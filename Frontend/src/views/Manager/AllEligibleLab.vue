<template>
    <div>

        <el-button style="margin-bottom: 10px" type="primary" @click="$router.push('/Management/classScheduling')">
            返回筛选
        </el-button>

        <el-table :data="BeingArrangedTableData" :header-cell-class-name="headerBg" border stripe style="margin-bottom: 10px">
            <el-table-column label="教师申请ID" prop="instructorRequestID" >
            </el-table-column>
            <el-table-column label="申请时间" prop="requestTime" >
            </el-table-column>
            <el-table-column label="课程名称" prop="course" >
            </el-table-column>
            <el-table-column label="实验室类型" prop="labType" >
            </el-table-column>
            <el-table-column label="星期" prop="weekday"  >
            </el-table-column>
            <el-table-column label="学生班级" prop="studentClass" >
            </el-table-column>
            <el-table-column label="学生人数" prop="studentCount" >
            </el-table-column>
            <el-table-column label="起始周" prop="startWeek" >
            </el-table-column>
            <el-table-column label="结束周" prop="endWeek" >
            </el-table-column>
            <el-table-column label="节次" prop="slot" >
            </el-table-column>
        </el-table>

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
                <template slot-scope="scope">
                    <el-button style="margin-bottom: 5px" type="primary" @click="confirmArrange(scope.row.labID)">排课 <i
                        class="el-icon-edit"></i></el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
export default {
    name: "Tester",
    data() {
        return {
            tableData: [],
            BeingArrangedTableData:[],
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

            temp2:{
                endWeek: 18,
                weekday: "WEDNESDAY",
                labType: "SYSTEM",
                studentCount: 60,
                startWeek: 1,
                slot: "TEN_TO_TWELVE"
            }

        }
    },
    created() {
        this.createLongArrangement = this.$store.state.LongArrangement
        this.satisfyingEverythingInstructorRequest = this.$store.state.satisfyingEverythingInstructorRequest
        if (this.$store.state.beingArrangedRequest.slot === "ONE_TO_TWO"){
            this.$store.state.beingArrangedRequest.slot ="1-2"
        }else if (this.$store.state.beingArrangedRequest.slot ==="THREE_TO_FIVE"){
            this.$store.state.beingArrangedRequest.slot ="3-5"
        }else if (this.$store.state.beingArrangedRequest.slot ==="SIX_TO_SEVEN"){
            this.$store.state.beingArrangedRequest.slot ="6-7"
        }else if (this.$store.state.beingArrangedRequest.slot ==="EIGHT_TO_NINE"){
            this.$store.state.beingArrangedRequest.slot ="8-9"
        }else if (this.$store.state.beingArrangedRequest.slot ==="TEN_TO_TWELVE"){
            this.$store.state.beingArrangedRequest.slot ="10-12"
        }else if (this.$store.state.beingArrangedRequest.slot ==="THIRTEEN_TO_FIFTEEN"){
            this.$store.state.beingArrangedRequest.slot ="13-15"
        }
        if (this.$store.state.beingArrangedRequest.weekday ==='MONDAY') {
            this.$store.state.beingArrangedRequest.weekday = "星期一"
        }else if(this.$store.state.beingArrangedRequest.weekday ==='TUESDAY') {
            this.$store.state.beingArrangedRequest.weekday = "星期二"
        }else if(this.$store.state.beingArrangedRequest.weekday ==='WEDNESDAY') {
            this.$store.state.beingArrangedRequest.weekday = "星期三"
        }else if(this.$store.state.beingArrangedRequest.weekday ==='THURSDAY') {
            this.$store.state.beingArrangedRequest.weekday = "星期四"
        }else if(this.$store.state.beingArrangedRequest.weekday ==='FRIDAY') {
            this.$store.state.beingArrangedRequest.weekday = "星期五"
        }else if(this.$store.state.beingArrangedRequest.weekday ==='SATURDAY') {
            this.$store.state.beingArrangedRequest.weekday = "星期六"
        }else if(this.$store.state.beingArrangedRequest.weekday ==='SUNDAY') {
            this.$store.state.beingArrangedRequest.weekday = "星期日"
        }
        if(this.$store.state.beingArrangedRequest.labType === "SOFTWARE"){
            this.$store.state.beingArrangedRequest.labType = "软件实验室"
        }else if (this.$store.state.beingArrangedRequest.labType === "HARDWARE"){
            this.$store.state.beingArrangedRequest.labType = "计算机硬件实验室"
        }else if (this.$store.state.beingArrangedRequest.labType === "NETWORK"){
            this.$store.state.beingArrangedRequest.labType = "计算机网络实验室"
        }else if (this.$store.state.beingArrangedRequest.labType === "SYSTEM"){
            this.$store.state.beingArrangedRequest.labType = "计算机系统实验室"
        }else if (this.$store.state.beingArrangedRequest.labType === "IOT"){
            this.$store.state.beingArrangedRequest.labType = "物联网实验室"
        }
        this.BeingArrangedTableData[0] = this.$store.state.beingArrangedRequest
        this.load()
    },
    methods: {
        switchToEasyWayToSee(){
            if (this.$store.state.beingArrangedRequest.slot === "ONE_TO_TWO"){
                this.$store.state.beingArrangedRequest.slot ="1-2"
            }else if (this.$store.state.beingArrangedRequest.slot ==="THREE_TO_FIVE"){
                this.$store.state.beingArrangedRequest.slot ="3-5"
            }else if (this.$store.state.beingArrangedRequest.slot ==="SIX_TO_SEVEN"){
                this.$store.state.beingArrangedRequest.slot ="6-7"
            }else if (this.$store.state.beingArrangedRequest.slot ==="EIGHT_TO_NINE"){
                this.$store.state.beingArrangedRequest.slot ="8-9"
            }else if (this.$store.state.beingArrangedRequest.slot ==="TEN_TO_TWELVE"){
                this.$store.state.beingArrangedRequest.slot ="10-12"
            }else if (this.$store.state.beingArrangedRequest.slot ==="THIRTEEN_TO_FIFTEEN"){
                this.$store.state.beingArrangedRequest.slot ="13-15"
            }
            if (this.$store.state.beingArrangedRequest.weekday ==='MONDAY') {
                this.$store.state.beingArrangedRequest.weekday = "星期一"
            }else if(this.$store.state.beingArrangedRequest.weekday ==='TUESDAY') {
                this.$store.state.beingArrangedRequest.weekday = "星期二"
            }else if(this.$store.state.beingArrangedRequest.weekday ==='WEDNESDAY') {
                this.$store.state.beingArrangedRequest.weekday = "星期三"
            }else if(this.$store.state.beingArrangedRequest.weekday ==='THURSDAY') {
                this.$store.state.beingArrangedRequest.weekday = "星期四"
            }else if(this.$store.state.beingArrangedRequest.weekday ==='FRIDAY') {
                this.$store.state.beingArrangedRequest.weekday = "星期五"
            }else if(this.$store.state.beingArrangedRequest.weekday ==='SATURDAY') {
                this.$store.state.beingArrangedRequest.weekday = "星期六"
            }else if(this.$store.state.beingArrangedRequest.weekday ==='SUNDAY') {
                this.$store.state.beingArrangedRequest.weekday = "星期日"
            }
            if(this.$store.state.beingArrangedRequest.labType === "SOFTWARE"){
                this.$store.state.beingArrangedRequest.labType = "软件实验室"
            }else if (this.$store.state.beingArrangedRequest.labType === "HARDWARE"){
                this.$store.state.beingArrangedRequest.labType = "计算机硬件实验室"
            }else if (this.$store.state.beingArrangedRequest.labType === "NETWORK"){
                this.$store.state.beingArrangedRequest.labType = "计算机网络实验室"
            }else if (this.$store.state.beingArrangedRequest.labType === "SYSTEM"){
                this.$store.state.beingArrangedRequest.labType = "计算机系统实验室"
            }else if (this.$store.state.beingArrangedRequest.labType === "IOT"){
                this.$store.state.beingArrangedRequest.labType = "物联网实验室"
            }
        },
        load() {
            console.log(this.satisfyingEverythingInstructorRequest)
            this.request.get("/laboratory/for-instructor-requests/satisfying-everything",{
                params:{
                    endWeek: this.satisfyingEverythingInstructorRequest.endWeek,
                    weekday: this.satisfyingEverythingInstructorRequest.weekday,
                    labType: this.satisfyingEverythingInstructorRequest.labType,
                    studentCount: this.satisfyingEverythingInstructorRequest.studentCount,
                    startWeek: this.satisfyingEverythingInstructorRequest.startWeek,
                    slot: this.satisfyingEverythingInstructorRequest.slot
                }
            }).then(res => {
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
        confirmArrange(labID) {
            this.createLongArrangement.labID = labID
            console.log(this.createLongArrangement)
            console.log(this.BeingArrangedTableData)

            this.$confirm('确认排课?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.post("/long-arrangement",this.createLongArrangement).then(res => {
                    if (res) {
                        this.$message.success("排课成功")
                        this.dialogFormVisible = false
                        this.resetDialog()
                        this.load()
                        this.$router.push("/Management/classScheduling")
                    } else {
                        this.$message.error("排课失败")
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消'
                });
            });
        },
    }
}
</script>

<style scoped>

</style>