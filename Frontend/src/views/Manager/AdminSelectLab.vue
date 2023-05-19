<template>
    <div>
        <el-button style="margin-bottom: 10px" type="primary" @click="$router.push('/Management/classScheduling')">
            返回
        </el-button>
        <el-card style="margin-bottom: 10px">
            <div style="display: flex;justify-content: space-around; margin: 10px 0">
                <div style="display: flex">
                    <h5 style="margin-top: 6px;">实验室检索:</h5>
                    <el-select v-model="form.labType" placeholder="请选择实验室类型" style="padding-left: 10px"
                               @change="change">
                        <el-option label="软件实验室" value="SOFTWARE"></el-option>
                        <el-option label="计算机系统实验室" value="SYSTEM"></el-option>
                        <el-option label="计算机硬件实验室" value="HARDWARE"></el-option>
                        <el-option label="物联网实验室" value="IOT"></el-option>
                        <el-option label="计算机网络实验室" value="NETWORK"></el-option>
                    </el-select>
                </div>
                <div style="display: flex">
                    <h5 style="margin-top: 6px; margin-right: 10px">学生人数:</h5>
                    <el-input-number v-model="form.studentCount" @change="handleStudentCountChange" :min="1" label="请输入学生人数" ></el-input-number>
                </div>
            </div>
        </el-card>
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
    </div>
</template>

<script>
import Login from "@/views/Login.vue";

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

            form:{
                labType: "",
                weekday: "",
                startWeek: 1,
                studentClass: "",
                studentCount: -1,
                semesterID: 1,
                instructorID: 1,
                instructorRequestID: 1,
                endWeek: 18,
                course: "",
                slot: "",
                // requestTime: "",
                // status: "",
                // adminProcessTime: "",
                // adminMessage: "",
            },

            formLabelWidth: '80px',
            username: "",
            role: "",
            password: "",
            dialogFormVisible: false,
            multipleSelection: [],
        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            // this.request.get("/user/admins").then(res => {
            //     console.log(res)
            //     this.tableData = res
            // })
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
        change(){
            this.request.get("/laboratory/type",{
                params:{
                    labType: this.form.labType
                }
            }).then(res => {
                console.log(res)
                this.tableData = res.data
            })
        },
        handleStudentCountChange(){
            this.request.get("/laboratory/capacity",{
                params:{
                    studentCount: this.form.studentCount
                }
            }).then(res => {
                console.log(res)
                this.tableData = res.data
            })
        },
        confirmArrange() {
            // this.request.post("/long-arrangement" + this.username + "&password=" + this.password + "&role=" + this.role).then(res => {
            //     if (res) {
            //         this.$message.success("排课成功")
            //         this.dialogFormVisible = false
            //         this.resetDialog()
            //         this.load()
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