<template>
    <div>
<!--        <el-card>-->
<!--            <div style="display: flex;justify-content: space-around; margin: 10px 0">-->
<!--                <h5 style="margin-top: 6px;">信用检索:</h5>-->
<!--                <el-input style="width: 200px" placeholder="请输入姓名" suffix-icon="el-icon-search"></el-input>-->
<!--                <el-button type="primary">搜索</el-button>-->
<!--            </div>-->
<!--        </el-card>-->

        <div style="margin: 10px 0">
            <el-button type="primary" @click="add25">加25分 <i class="el-icon-circle-plus-outline"></i></el-button>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
            <el-table-column prop="user_id" label="ID">
            </el-table-column>
            <el-table-column prop="username" label="姓名">
            </el-table-column>
            <el-table-column prop="role" label="角色">
            </el-table-column>
            <el-table-column prop="credit" label="信用分">
            </el-table-column>
            <el-table-column label="操作"  align="center">
                <template slot-scope="scope">
                    <el-button type="info" @click="check(scope.row.user_id)">查看 <i class="el-icon-view"></i></el-button>
                    <el-button type="warning" @click="handleModify(scope.row.user_id)">修改 <i class="el-icon-edit"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog title="修改评分" :visible.sync="dialogFormVisible"  width="30%">
            <el-form :label-width="formLabelWidth">
                <el-form-item label="评分">
                    <el-input-number v-model="increment" @change="handleChange" label="描述文字"></el-input-number>
                </el-form-item>
                <el-form-item label="修改意见">
                    <el-input type="textarea" v-model="memorandum"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="resetDialog">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

<!--        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">-->
<!--            <el-table-column prop="date" label="日期" >-->
<!--            </el-table-column>-->
<!--            <el-table-column prop="name" label="姓名">-->
<!--            </el-table-column>-->
<!--            <el-table-column prop="role" label="角色">-->
<!--            </el-table-column>-->
<!--            <el-table-column prop="point" label="信用分">-->
<!--            </el-table-column>-->
<!--            <el-table-column label="操作"  align="center">-->
<!--                <template slot-scope="scope">-->
<!--                    <el-button type="info" @click="$router.push('/Management/credit/check')">查看 <i class="el-icon-view"></i></el-button>-->
<!--                </template>-->
<!--            </el-table-column>-->
<!--        </el-table>-->

    </div>
</template>

<script>
export default {
    name: "Credit",
    data() {
        return {
            tableData:[],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',

            formLabelWidth: '80px',
            credit: "",
            dialogFormVisible: false,
            multipleSelection: [],

            user_id:"",
            increment:"",
            memorandum:""

        }
    },
    created() {
        this.load()
    },
    methods:{
        add25(){
            this.request.put("/credit_record/monthly_update").then(res =>{
                if (res) {
                    this.$message.success("加分成功")
                    this.dialogFormVisible = false
                    this.resetDialog()
                    this.load()
                } else {
                    this.$message.error("加分失败")
                }
            })
        },
        save(){
            // console.log(this.user_id)
            // console.log(this.increment)
            // console.log(this.memorandum)
            this.request.post("/credit_record/manual?user_id="+this.user_id+"&increment="+this.increment+"&message="+this.memorandum).then(res =>{
                if (res) {
                    this.$message.success("修改评分成功")
                    this.dialogFormVisible = false
                    this.resetDialog()
                    this.load()
                } else {
                    this.$message.error("修改评分失败")
                }
            })
        },
        handleModify(user_id){
            this.user_id = user_id
            this.dialogFormVisible = true;
         },
        resetDialog(){
            this.dialogFormVisible = false
            this.increment = 0
            this.memorandum = ""
        },
        load(){
            this.request.get("/user/users").then(res=>{
                console.log(res)
                this.tableData = res
            })
        },
        check(id){
            console.log(id)
            this.$router.push({
                path:"/Management/credit/check",
                query:{id:id}
            })
        },
        handleChange(value){
            console.log(value)
        }
    }
}
</script>

<style scoped>

</style>