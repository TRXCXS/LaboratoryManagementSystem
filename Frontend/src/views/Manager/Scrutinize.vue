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

        <el-table :data="tableData" :header-cell-class-name="headerBg" border stripe>

            <el-table-column label="申请学生" prop="app_student">
            </el-table-column>
            <el-table-column label="申请时间" prop="requestTime">
            </el-table-column>
            <el-table-column label="申请学期" prop="semesterID">
            </el-table-column>
            <el-table-column label="申请周次" prop="week" width="100px">
            </el-table-column>
            <el-table-column label="申请节次" prop="slot" width="100px">
            </el-table-column>
            <el-table-column label="申请实验室编号" prop="labID" width="150px">
            </el-table-column>
            <el-table-column label="申请原因" prop="reason" width="150px">
            </el-table-column>
            <el-table-column label="状态" prop="status" width="100px">
            </el-table-column>



            <el-table-column align="center" label="操作" width="190px">
                <template slot-scope="scope">
                    <el-button type="success" @click="approve(scope.row.user_id)">通过 <i class="el-icon-edit"></i>
                    </el-button>
                    <el-button type="danger" @click="disapprove(scope.row.user_id)">不通过 <i
                            class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <!--        <el-dialog title="审核申诉" :visible.sync="dialogFormVisible"  width="30%">-->
        <!--            <el-form :label-width="formLabelWidth">-->

        <!--                <el-form-item label="审核确认">-->
        <!--                    <el-select v-model="form.confirmation" placeholder="请审核申诉是否通过">-->
        <!--                        <el-option label="YES" value="YES"></el-option>-->
        <!--                        <el-option label="NO" value="NO"></el-option>-->
        <!--                        <el-option label="OTHER" value="OTHER"></el-option>-->
        <!--                    </el-select>-->
        <!--                </el-form-item>-->
        <!--                <el-form-item label="审核意见">-->
        <!--                    <el-input type="textarea" v-model="form.memorandum"></el-input>-->
        <!--                </el-form-item>-->
        <!--            </el-form>-->
        <!--            <div slot="footer" class="dialog-footer">-->
        <!--                <el-button @click="resetDialog">取 消</el-button>-->
        <!--                <el-button type="primary" @click="save">确 定</el-button>-->
        <!--            </div>-->
        <!--        </el-dialog>-->
    </div>
</template>

<script>
export default {
    name: "Appeal",

    data() {
        const item = {
            app_time: "2023-04-25 02:44:30",
            app_student: "小鑫",
            app_semester: "2022-2023-2",
            app_week: "5",
            app_section: "8-9",
            app_labNum: "532",
            app_reason: "开会"
        };
        return {
            api: this.GLOBAL.BASE_URL,
            // tableData:[],
            tableData: Array(10).fill(item),
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',

            formLabelWidth: '80px',

            dialogFormVisible: false,
            multipleSelection: [],

            appeal_id: "",
            form: {
                confirmation: "",
                memorandum: "",
            },


            studentRequestID:"",
            week:0,
            weekday:"",
            slot:"",
            reason:"",
            requestTime:"",
            status:"",
            adminProcessTime:"",
            adminMessage:"",
            useCompleteTime:"",
            labID:0,
            semesterID:0,
            studentID:0,



        }
    },
    created() {
        this.load()
    },
    methods: {
        download(photo_id) {
            console.log(photo_id)
            this.request.get(this.api + "/photo/" + photo_id, {responseType: 'blob'})
                .then(response => {
                    const url = window.URL.createObjectURL(new Blob([response]));
                    const link = document.createElement('a');
                    link.href = url;
                    link.setAttribute('download', 'filename.jpg');
                    document.body.appendChild(link);
                    link.click();
                })
        },
        resetDialog() {
            this.dialogFormVisible = false
            this.form.confirmation = ""
            this.form.memorandum = ""
        },
        save() {
            console.log(this.form.confirmation)
            console.log(this.form.memorandum)
            console.log(this.appeal_id)
            this.request.post("/scrutiny/?appeal_id=" + this.appeal_id + "&confirmation=" + this.form.confirmation + "&memorandum=" + this.form.memorandum).then(res => {
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
        handleAdd(id) {
            this.appeal_id = id
            this.dialogFormVisible = true;
        },
        load() {
            this.request.get("/appeal/unhandled").then(res => {
                for (let i = 0; i < res.length; i++) {
                    let origin_appeal_time = res[i].appeal_time
                    let date1 = new Date(origin_appeal_time);
                    let time1 = date1.getFullYear() + '-' + ((date1.getMonth() + 1) < 10 ? "0" + (date1.getMonth() + 1) : (date1.getMonth() + 1)) + '-' + (date1.getDate() < 10 ? "0" + date1.getDate() : date1.getDate()) + ' ' + (date1.getHours() < 10 ? "0" + date1.getHours() : date1.getHours()) + ':' + (date1.getMinutes() < 10 ? "0" + date1.getMinutes() : date1.getMinutes()) + ':' + (date1.getSeconds() < 10 ? "0" + date1.getSeconds() : date1.getSeconds());
                    res[i].appeal_time = time1
                }
                this.tableData = res
            })
        },
        approve(id) {
            console.log(id)
            this.$confirm('确认通过?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                // this.request.post("/short-arrangement"+id).then(res=>{
                //     if (res) {
                //         this.$message({
                //             type: 'success',
                //             message: '已通过学生申请!'
                //         });
                //         this.load()
                //     } else {
                //         this.$message.error("处理失败")
                //     }
                // })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消'
                });
            });
        },
        disapprove(id) {
            console.log(id)
            this.$confirm('确认不通过?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                // this.request.delete("/user/users/"+id).then(res=>{
                //     if (res) {
                //         this.$message({
                //             type: 'success',
                //             message: '已驳回学生申请!'
                //         });
                //         this.load()
                //     } else {
                //         this.$message.error("处理失败")
                //     }
                // })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消'
                });
            });
        },
    },
}
</script>

<style scoped>

</style>