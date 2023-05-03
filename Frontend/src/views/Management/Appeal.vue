<template>
    <div>
<!--        <el-card>-->
<!--            <div style="display: flex;justify-content: space-around; margin: 10px 0">-->
<!--                <h5 style="margin-top: 6px;">申诉搜索:</h5>-->
<!--                <el-input style="width: 200px" placeholder="请输入姓名" suffix-icon="el-icon-search"></el-input>-->
<!--                &lt;!&ndash;                <el-input style="width: 200px" placeholder="请输入邮箱" suffix-icon="el-icon-message" class="ml-5"></el-input>&ndash;&gt;-->
<!--                &lt;!&ndash;                <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-position" class="ml-5"></el-input>&ndash;&gt;-->
<!--                <el-button type="primary">搜索</el-button>-->
<!--            </div>-->
<!--        </el-card>-->

<!--        <div style="margin: 10px 0">-->
<!--            <el-button type="primary">导入 <i class="el-icon-bottom"></i></el-button>-->
<!--            <el-button type="primary">导出 <i class="el-icon-top"></i></el-button>-->
<!--        </div>-->

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
            <el-table-column prop="appeal_id" label="申诉ID" >
            </el-table-column>
            <el-table-column prop="appeal_time" label="申诉时间" >
            </el-table-column>
            <el-table-column prop="user_id" label="申诉人ID">
            </el-table-column>
            <el-table-column prop="credrec_id" label="信用记录ID">
            </el-table-column>
            <el-table-column prop="description" label="描述">
            </el-table-column>
<!--            <el-table-column prop="photo_id" label="图片ID">-->
<!--            </el-table-column>-->
            <el-table-column prop="photo_id" label="图片">
                <template slot-scope="scope">
                    <el-button type="primary" @click="download(scope.row.photo_id)">下载 <i class="el-icon-download"></i></el-button>
                </template>
            </el-table-column>
            <el-table-column label="操作"  align="center">
                <template slot-scope="scope">
                    <el-button type="info" @click="handleAdd(scope.row.appeal_id)">审核 <i class="el-icon-view"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog title="审核申诉" :visible.sync="dialogFormVisible"  width="30%">
            <el-form :label-width="formLabelWidth">

                <el-form-item label="审核确认">
                    <el-select v-model="form.confirmation" placeholder="请审核申诉是否通过">
                        <el-option label="YES" value="YES"></el-option>
                        <el-option label="NO" value="NO"></el-option>
                        <el-option label="OTHER" value="OTHER"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="审核意见">
                    <el-input type="textarea" v-model="form.memorandum"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="resetDialog">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "Appeal",
    data() {
        return {
            api:this.GLOBAL.BASE_URL,
            tableData:[],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',

            formLabelWidth: '80px',

            dialogFormVisible: false,
            multipleSelection: [],

            appeal_id:"",
            form:{
                confirmation:"",
                memorandum:"",
            }

        }
    },
    created() {
        this.load()
    },
    methods:{
        download(photo_id){
            console.log(photo_id)
            this.request.get(this.api+"/photo/" + photo_id, { responseType: 'blob' })
                .then(response => {
                    const url = window.URL.createObjectURL(new Blob([response]));
                    const link = document.createElement('a');
                    link.href = url;
                    link.setAttribute('download', 'filename.jpg');
                    document.body.appendChild(link);
                    link.click();
                })
        },
        resetDialog(){
            this.dialogFormVisible = false
            this.form.confirmation=""
            this.form.memorandum=""
        },
        save(){
            console.log(this.form.confirmation)
            console.log(this.form.memorandum)
            console.log(this.appeal_id)
            this.request.post("/scrutiny/?appeal_id="+this.appeal_id+"&confirmation="+this.form.confirmation+"&memorandum="+this.form.memorandum).then(res =>{
                if (res) {
                    this.$message.success("审核成功")
                    this.dialogFormVisible = false
                    this.resetDialog()
                    this.load()
                } else {
                    this.$message.error("审核失败")
                }
            })
        },
        handleAdd(id){
            this.appeal_id = id
            this.dialogFormVisible = true;
        },
        load(){
            this.request.get("/appeal/unhandled").then(res=>{
                for(let i = 0;i<res.length;i++){
                    let origin_appeal_time = res[i].appeal_time
                    let date1 = new Date(origin_appeal_time);
                    let time1=date1.getFullYear() + '-' + ((date1.getMonth() + 1)<10?"0"+(date1.getMonth() + 1):(date1.getMonth() + 1) )+ '-' + (date1.getDate()<10?"0"+date1.getDate():date1.getDate() )+ ' ' + (date1.getHours()<10?"0"+date1.getHours():date1.getHours() )+ ':' + (date1.getMinutes()<10?"0"+date1.getMinutes():date1.getMinutes() )+ ':' + (date1.getSeconds()<10?"0"+date1.getSeconds():date1.getSeconds());
                    res[i].appeal_time = time1
                }
                this.tableData = res
            })
        },
    },
}
</script>

<style scoped>

</style>