<template>
    <div>

        <el-button style="margin-bottom: 10px" type="primary" @click="$router.push('/Management/classScheduling')">
            返回筛选
        </el-button>

        <el-table :data="tableData" :header-cell-class-name="headerBg" border stripe>
            <el-table-column label="实验室ID" prop="labID">
            </el-table-column>
            <el-table-column label="实验室编号" prop="labNumber">
            </el-table-column>
            <el-table-column label="实验室名字" prop="name">
            </el-table-column>
            <el-table-column label="实验室类型" prop="labType">
            </el-table-column>
            <el-table-column label="设备数量" prop="deviceCount">
            </el-table-column>
            <el-table-column align="center" label="操作">
                <el-button style="margin-bottom: 5px" type="primary" @click="confirmArrange">排课 <i
                        class="el-icon-edit"></i></el-button>
            </el-table-column>
        </el-table>

        <el-dialog :visible.sync="dialogFormVisible" title="确认维修" width="30%">
            <el-form :label-width="formLabelWidth">
                <el-form-item label="维修情况说明">
                    <el-input v-model="description" autocomplete="off" type="textarea"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "Tester",
    data() {
        return {
            tableData: [],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',
            handledState: "",

            teacher_name: "",
            lab_num: "",
            description: "",


            formLabelWidth: '80px',
            username: "",
            role: "",
            password: "",
            dialogFormVisible: false,
            multipleSelection: [],

            satisfyingEverythingInstructorRequest:{
                endWeek: 0,
                slot: "",
                labType: "",
                startWeek: 0,
                weekday: "",
                studentCount: 0
            },
            createLongArrangement:{
                longArrangementID: 0,
                labID: 0,
                studentClass: "",
                weekday: "",
                studentCount: 0,
                instructorRequestID: 0,
                endWeek: 0,
                slot: "",
                startWeek: 0
            },

            temp:{
                endWeek: 18,
                slot: "TEN_TO_TWELVE",
                weekday: "SUNDAY",
                labType: "SYSTEM",
                startWeek: 1
            },

        }
    },
    created() {
        this.createLongArrangement = this.$store.state.LongArrangement
        this.satisfyingEverythingInstructorRequest = this.$store.state.satisfyingEverythingInstructorRequest
        this.load()
    },
    methods: {
        load() {
            // if (this.satisfyingEverythingInstructorRequest.slot === "1-2"){
            //     this.satisfyingEverythingInstructorRequest.slot = "ONE_TO_TWO"
            // }else if (this.satisfyingEverythingInstructorRequest.slot === "3-5"){
            //     this.satisfyingEverythingInstructorRequest.slot = "THREE_TO_FIVE"
            // }else if (this.satisfyingEverythingInstructorRequest.slot === "6-7"){
            //     this.satisfyingEverythingInstructorRequest.slot = "SIX_TO_SEVEN"
            // }else if (this.satisfyingEverythingInstructorRequest.slot === "8-9"){
            //     this.satisfyingEverythingInstructorRequest.slot = "EIGHT_TO_NINE"
            // }else if (this.satisfyingEverythingInstructorRequest.slot === "10-12"){
            //     this.satisfyingEverythingInstructorRequest.slot = "TEN_TO_TWELVE"
            // }else if (this.satisfyingEverythingInstructorRequest.slot === "13-15"){
            //     this.satisfyingEverythingInstructorRequest.slot = "THIRTEEN_TO_FIFTEEN"
            // }
            console.log(this.satisfyingEverythingInstructorRequest)
            this.request.get("/laboratory/for-instructor-requests/satisfying-everything",this.satisfyingEverythingInstructorRequest).then(res => {
                console.log(res)
                this.tableData = res.data
            })

        },
        resetDialog() {
            this.load()
        },
        save() {
            // this.request.post("/user/admins?username="+this.username+"&password="+this.password+"&role="+this.role).then(res =>{
            //     if (res) {
            //         this.$message.success("添加成功")
            //         this.dialogFormVisible = false
            //         this.resetDialog()
            //         this.load()
            //     } else {
            //         this.$message.error("添加失败")
            //     }
            // })
        },
        del(id) {
            console.log(id)
            this.$confirm('此操作将永久删除该条记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.delete("/user/admins/" + id).then(res => {
                    if (res) {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        this.load()
                    } else {
                        this.$message.error("删除失败")
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        handleMaintenance() {
            this.dialogFormVisible = true;
            this.description = ""
        },
        reset() {
            this.$message.success("已设置")
        },
        confirmArrange() {
            // this.request.post("/long-arrangement" + this.username + "&password=" + this.password + "&role=" + this.role).then(res => {
            //     if (res) {
            //         this.$message.success("排课成功")
            //         this.dialogFormVisible = false
            //         this.resetDialog()
            //         this.load()6
            //     } else {
            //         this.$message.error("排课失败")
            //     }
            // })
        },
    }
}
</script>

<style scoped>

</style>