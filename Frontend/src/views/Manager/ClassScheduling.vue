<template>
    <div>
        <el-card style="margin-bottom: 10px">
            <el-button type="primary" @click="checkAll">查看全部申请</el-button>
            <el-button type="primary" @click="checkUnhandled">查看未排课的申请</el-button>
        </el-card>
        <el-table :data="tableData" :header-cell-class-name="headerBg" border stripe>
            <el-table-column label="教师申请ID" prop="instructorRequestID" width="85px">
            </el-table-column>
            <el-table-column label="申请时间" prop="requestTime" width="140px">
            </el-table-column>
            <el-table-column label="课程名称" prop="course" width="100px">
            </el-table-column>
            <el-table-column label="实验室类型" prop="labType" width="100px">
            </el-table-column>
            <el-table-column label="星期" prop="weekday" width="80px" >
            </el-table-column>
            <el-table-column label="学生班级" prop="studentClass" width="90px">
            </el-table-column>
            <el-table-column label="学生人数" prop="studentCount" width="80px">
            </el-table-column>
            <el-table-column label="起始周" prop="startWeek" width="60px">
            </el-table-column>
            <el-table-column label="结束周" prop="endWeek" width="60px">
            </el-table-column>
            <el-table-column label="节次" prop="slot" width="55px">
            </el-table-column>
            <el-table-column label="状态" prop="status" width="70px">

<!--                <el-popover>-->
<!--                    <el-tag slot="reference" :type="this.handledState==='已排课' ? 'success' : 'info'">-->
<!--                        {{ handledState }}-->
<!--                    </el-tag>-->
<!--                </el-popover>-->

            </el-table-column>
            <el-table-column align="center" label="操作">
                <template slot-scope="scope">
                    <el-button style="margin-bottom: 5px" type="success" @click="checkAllEligibleLab(scope.row.requestTime,scope.row.course,scope.row.studentClass,scope.row.weekday,scope.row.studentCount,scope.row.instructorRequestID,scope.row.endWeek,scope.row.slot,scope.row.startWeek,scope.row.labType)">查看符合所有条件的教室
                        <i class="el-icon-edit"></i></el-button>
                    <el-button type="primary" @click="selectLab(scope.row.requestTime,scope.row.course,scope.row.studentClass,scope.row.weekday,scope.row.studentCount,scope.row.instructorRequestID,scope.row.endWeek,scope.row.slot,scope.row.startWeek,scope.row.labType)">筛选教室<i class="el-icon-edit"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :visible.sync="dialogFormVisible" title="用户信息" width="30%">
            <el-form :label-width="formLabelWidth">
                <el-form-item label="用户名">
                    <el-input v-model="username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="password" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="角色">
                    <el-input v-model="role" autocomplete="off"></el-input>
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
    name: "ClassScheduling",
    data() {
        return {
            tableData: [],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,

            formLabelWidth: '80px',
            username: "",
            role: "",
            password: "",
            dialogFormVisible: false,
            multipleSelection: [],

            headerBg: 'headerBg',

            instructorRequestID: 0,
            requestTime: "",
            labType: "",
            startWeek: 0,
            endWeek: 0,
            weekday: "",
            slot: "",
            studentClass: "",
            studentCount: 0,
            course: "",
            status: "",
            adminProcessTime: "",
            adminMessage: "",
            instructorID: 0,
            semesterID: 0,

            handledState:""

        }
    },
    created() {
        this.load()
    },
    methods: {
        checkAll(){
            this.request.get("/instructor-request/all").then(res => {
                console.log(res)
                for (let i = 0; i < res.data.length; i++) {
                    if (res.data[i].status ==='NOT_ARRANGED') {
                        this.handledState = "未排课"
                        res.data[i].status = "未排课"
                    } else {
                        this.handledState = "已排课"
                        res.data[i].status = "已排课"
                    }
                    if (res.data[i].slot === "ONE_TO_TWO"){
                        res.data[i].slot ="1-2"
                    }else if (res.data[i].slot ==="THREE_TO_FIVE"){
                        res.data[i].slot ="3-5"
                    }else if (res.data[i].slot ==="SIX_TO_SEVEN"){
                        res.data[i].slot ="6-7"
                    }else if (res.data[i].slot ==="EIGHT_TO_NINE"){
                        res.data[i].slot ="8-9"
                    }else if (res.data[i].slot ==="TEN_TO_TWELVE"){
                        res.data[i].slot ="10-12"
                    }else if (res.data[i].slot ==="THIRTEEN_TO_FIFTEEN"){
                        res.data[i].slot ="13-15"
                    }
                    if (res.data[i].weekday ==='MONDAY') {
                        res.data[i].weekday = "星期一"
                    }else if(res.data[i].weekday ==='TUESDAY') {
                        res.data[i].weekday = "星期二"
                    }else if(res.data[i].weekday ==='WEDNESDAY') {
                        res.data[i].weekday = "星期三"
                    }else if(res.data[i].weekday ==='THURSDAY') {
                        res.data[i].weekday = "星期四"
                    }else if(res.data[i].weekday ==='FRIDAY') {
                        res.data[i].weekday = "星期五"
                    }else if(res.data[i].weekday ==='SATURDAY') {
                        res.data[i].weekday = "星期六"
                    }else if(res.data[i].weekday ==='SUNDAY') {
                        res.data[i].weekday = "星期七"
                    }
                    if(res.data[i].labType === "SOFTWARE"){
                        res.data[i].labType = "软件实验室"
                    }else if (res.data[i].labType === "HARDWARE"){
                        res.data[i].labType = "计算机硬件实验室"
                    }else if (res.data[i].labType === "NETWORK"){
                        res.data[i].labType = "计算机网络实验室"
                    }else if (res.data[i].labType === "SYSTEM"){
                        res.data[i].labType = "计算机系统实验室"
                    }else if (res.data[i].labType === "IOT"){
                        res.data[i].labType = "物联网实验室"
                    }

                }
                this.tableData = res.data
            })
        },
        checkUnhandled(){
            this.load()
        },
        load() {
            this.request.get("/instructor-request/unhandled").then(res => {
                console.log(res)
                for (let i = 0; i < res.data.length; i++) {
                    if (res.data[i].status ==='NOT_ARRANGED') {
                        this.handledState = "未排课"
                        res.data[i].status = "未排课"
                    } else {
                        this.handledState = "已排课"
                        res.data[i].status = "已排课"
                    }
                    if (res.data[i].slot === "ONE_TO_TWO"){
                        res.data[i].slot ="1-2"
                    }else if (res.data[i].slot ==="THREE_TO_FIVE"){
                        res.data[i].slot ="3-5"
                    }else if (res.data[i].slot ==="SIX_TO_SEVEN"){
                        res.data[i].slot ="6-7"
                    }else if (res.data[i].slot ==="EIGHT_TO_NINE"){
                        res.data[i].slot ="8-9"
                    }else if (res.data[i].slot ==="TEN_TO_TWELVE"){
                        res.data[i].slot ="10-12"
                    }else if (res.data[i].slot ==="THIRTEEN_TO_FIFTEEN"){
                        res.data[i].slot ="13-15"
                    }
                    if (res.data[i].weekday ==='MONDAY') {
                        res.data[i].weekday = "星期一"
                    }else if(res.data[i].weekday ==='TUESDAY') {
                        res.data[i].weekday = "星期二"
                    }else if(res.data[i].weekday ==='WEDNESDAY') {
                        res.data[i].weekday = "星期三"
                    }else if(res.data[i].weekday ==='THURSDAY') {
                        res.data[i].weekday = "星期四"
                    }else if(res.data[i].weekday ==='FRIDAY') {
                        res.data[i].weekday = "星期五"
                    }else if(res.data[i].weekday ==='SATURDAY') {
                        res.data[i].weekday = "星期六"
                    }else if(res.data[i].weekday ==='SUNDAY') {
                        res.data[i].weekday = "星期七"
                    }
                    if(res.data[i].labType === "SOFTWARE"){
                        res.data[i].labType = "软件实验室"
                    }else if (res.data[i].labType === "HARDWARE"){
                        res.data[i].labType = "计算机硬件实验室"
                    }else if (res.data[i].labType === "NETWORK"){
                        res.data[i].labType = "计算机网络实验室"
                    }else if (res.data[i].labType === "SYSTEM"){
                        res.data[i].labType = "计算机系统实验室"
                    }else if (res.data[i].labType === "IOT"){
                        res.data[i].labType = "物联网实验室"
                    }

                }
                this.tableData = res.data
            })
        },
        resetDialog() {
            this.load()
        },
        save() {
            this.request.post("/user/users?username=" + this.username + "&password=" + this.password + "&role=" + this.role).then(res => {
                if (res) {
                    this.$message.success("添加成功")
                    this.dialogFormVisible = false
                    this.resetDialog()
                    this.load()
                } else {
                    this.$message.error("添加失败")
                }
            })
        },
        handleAdd() {
            this.dialogFormVisible = true;
            this.form = {}
        },
        del(id) {
            console.log(id)
            this.$confirm('此操作将永久删除该条记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.delete("/user/users/" + id).then(res => {
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

        reset() {
            this.$message.success("已重置")
        },
        checkAllEligibleLab(requestTime,course,studentClass,weekday,studentCount,instructorRequestID,endWeek,slot,startWeek,labType) {
            this.$router.push("/Management/AllEligibleLab");
            if(labType === "软件实验室"){
                labType = "SOFTWARE"
            }else if (labType === "计算机硬件实验室"){
                labType = "HARDWARE"
            }else if (labType === "计算机网络实验室"){
                labType = "NETWORK"
            }else if (labType === "计算机系统实验室"){
                labType = "SYSTEM"
            }else if (labType === "物联网实验室"){
                labType = "IOT"
            }
            if (slot === "1-2"){
                slot ="ONE_TO_TWO"
            }else if (slot ==="3-5"){
                slot ="THREE_TO_FIVE"
            }else if (slot ==="6-7"){
                slot ="SIX_TO_SEVEN"
            }else if (slot ==="8-9"){
                slot ="EIGHT_TO_NINE"
            }else if (slot ==="10-12"){
                slot ="TEN_TO_TWELVE"
            }else if (slot ==="13-15"){
                slot ="THIRTEEN_TO_FIFTEEN"
            }
            if (weekday ==='星期一') {
                weekday = "MONDAY"
            }else if(weekday ==='星期二') {
                weekday = "TUESDAY"
            }else if(weekday ==='星期三') {
                weekday = "WEDNESDAY"
            }else if(weekday ==='星期四') {
                weekday = "THURSDAY"
            }else if(weekday ==='星期五') {
                weekday = "FRIDAY"
            }else if(weekday ==='星期六') {
                weekday = "SATURDAY"
            }else if(weekday ==='星期七') {
                weekday = "SUNDAY"
            }


            this.$store.state.LongArrangement.studentClass = studentClass
            this.$store.state.LongArrangement.weekday = weekday
            this.$store.state.LongArrangement.studentCount = studentCount
            this.$store.state.LongArrangement.instructorRequestID = instructorRequestID
            this.$store.state.LongArrangement.endWeek = endWeek
            this.$store.state.LongArrangement.slot = slot
            this.$store.state.LongArrangement.startWeek = startWeek

            this.$store.state.satisfyingEverythingInstructorRequest.endWeek = endWeek
            this.$store.state.satisfyingEverythingInstructorRequest.slot = slot
            this.$store.state.satisfyingEverythingInstructorRequest.labType = labType
            this.$store.state.satisfyingEverythingInstructorRequest.startWeek = startWeek
            this.$store.state.satisfyingEverythingInstructorRequest.weekday = weekday
            this.$store.state.satisfyingEverythingInstructorRequest.studentCount = studentCount

            this.$store.state.beingArrangedRequest.instructorRequestID= instructorRequestID
            this.$store.state.beingArrangedRequest.requestTime=requestTime
            this.$store.state.beingArrangedRequest.course=course
            this.$store.state.beingArrangedRequest.labType=labType
            this.$store.state.beingArrangedRequest.weekday= weekday
            this.$store.state.beingArrangedRequest.studentClass= studentClass
            this.$store.state.beingArrangedRequest.studentCount= studentCount
            this.$store.state.beingArrangedRequest.endWeek= endWeek
            this.$store.state.beingArrangedRequest.slot= slot
            this.$store.state.beingArrangedRequest.startWeek= startWeek


        },
        selectLab(requestTime,course,studentClass,weekday,studentCount,instructorRequestID,endWeek,slot,startWeek,labType) {
            this.$router.push({
                path:"/Management/AdminSelectLab",
            })
            if (slot === "1-2"){
                slot ="ONE_TO_TWO"
            }else if (slot ==="3-5"){
                slot ="THREE_TO_FIVE"
            }else if (slot ==="6-7"){
                slot ="SIX_TO_SEVEN"
            }else if (slot ==="8-9"){
                slot ="EIGHT_TO_NINE"
            }else if (slot ==="10-12"){
                slot ="TEN_TO_TWELVE"
            }else if (slot ==="13-15"){
                slot ="THIRTEEN_TO_FIFTEEN"
            }
            if (weekday ==='星期一') {
                weekday = "MONDAY"
            }else if(weekday ==='星期二') {
                weekday = "TUESDAY"
            }else if(weekday ==='星期三') {
                weekday = "WEDNESDAY"
            }else if(weekday ==='星期四') {
                weekday = "THURSDAY"
            }else if(weekday ==='星期五') {
                weekday = "FRIDAY"
            }else if(weekday ==='星期六') {
                weekday = "SATURDAY"
            }else if(weekday ==='星期七') {
                weekday = "SUNDAY"
            }

            this.$store.state.LongArrangement.studentClass = studentClass
            this.$store.state.LongArrangement.weekday = weekday
            this.$store.state.LongArrangement.studentCount = studentCount
            this.$store.state.LongArrangement.instructorRequestID = instructorRequestID
            this.$store.state.LongArrangement.endWeek = endWeek
            this.$store.state.LongArrangement.slot = slot
            this.$store.state.LongArrangement.startWeek = startWeek

            this.$store.state.beingArrangedRequest.instructorRequestID= instructorRequestID
            this.$store.state.beingArrangedRequest.requestTime=requestTime
            this.$store.state.beingArrangedRequest.course=course
            this.$store.state.beingArrangedRequest.labType=labType
            this.$store.state.beingArrangedRequest.weekday= weekday
            this.$store.state.beingArrangedRequest.studentClass= studentClass
            this.$store.state.beingArrangedRequest.studentCount= studentCount
            this.$store.state.beingArrangedRequest.endWeek= endWeek
            this.$store.state.beingArrangedRequest.slot= slot
            this.$store.state.beingArrangedRequest.startWeek= startWeek


        },
    }
}
</script>

<style scoped>

</style>