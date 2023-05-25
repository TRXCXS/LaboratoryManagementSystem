<template>
    <div>
        <el-button style="margin-bottom: 10px" type="primary" @click="$router.push('/Management/classScheduling')">
            返回
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
        <el-card style="margin-bottom: 10px">
            <div style="display: flex;justify-content: space-around; margin: 10px 0">
                <el-button type="primary" @click="checkByTimeAndType">根据时间与类型查询</el-button>
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
                <template slot-scope="scope">
                    <el-button style="margin-bottom: 5px" type="primary" @click="confirmArrange(scope.row.labID,scope.row.name,scope.row.deviceCount)">排课 <i
                        class="el-icon-edit"></i></el-button>
                </template>
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
            BeingArrangedTableData:[],
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
                semesterID: this.$store.state.semester,
                instructorID: 1,
                instructorRequestID: 1,
                endWeek: 18,
                course: "",
                slot: "",
                // requestTime: "",
                // status: "",
                // adminProcessTime: "  ",
                // adminMessage: "",
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
        this.createLongArrangement = this.$store.state.LongArrangement
        this.switchToEasyWayToSee()
        this.BeingArrangedTableData[0] = this.$store.state.beingArrangedRequest
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
        switchToParams(){
            if (this.$store.state.beingArrangedRequest.slot === "1-2"){
                this.$store.state.beingArrangedRequest.slot ="ONE_TO_TWO"
            }else if (this.$store.state.beingArrangedRequest.slot ==="3-5"){
                this.$store.state.beingArrangedRequest.slot ="THREE_TO_FIVE"
            }else if (this.$store.state.beingArrangedRequest.slot ==="6-7"){
                this.$store.state.beingArrangedRequest.slot ="SIX_TO_SEVEN"
            }else if (this.$store.state.beingArrangedRequest.slot ==="8-9"){
                this.$store.state.beingArrangedRequest.slot ="EIGHT_TO_NINE"
            }else if (this.$store.state.beingArrangedRequest.slot ==="10-12"){
                this.$store.state.beingArrangedRequest.slot ="TEN_TO_TWELVE"
            }else if (this.$store.state.beingArrangedRequest.slot ==="13-15"){
                this.$store.state.beingArrangedRequest.slot ="THIRTEEN_TO_FIFTEEN"
            }
            if (this.$store.state.beingArrangedRequest.weekday ==='星期一') {
                this.$store.state.beingArrangedRequest.weekday = "MONDAY"
            }else if(this.$store.state.beingArrangedRequest.weekday ==='星期二') {
                this.$store.state.beingArrangedRequest.weekday = "TUESDAY"
            }else if(this.$store.state.beingArrangedRequest.weekday ==='星期三') {
                this.$store.state.beingArrangedRequest.weekday = "WEDNESDAY"
            }else if(this.$store.state.beingArrangedRequest.weekday ==='星期四') {
                this.$store.state.beingArrangedRequest.weekday = "THURSDAY"
            }else if(this.$store.state.beingArrangedRequest.weekday ==='星期五') {
                this.$store.state.beingArrangedRequest.weekday = "FRIDAY"
            }else if(this.$store.state.beingArrangedRequest.weekday ==='星期六') {
                this.$store.state.beingArrangedRequest.weekday = "SATURDAY"
            }else if(this.$store.state.beingArrangedRequest.weekday ==='星期日') {
                this.$store.state.beingArrangedRequest.weekday = "SUNDAY"
            }
            if(this.$store.state.beingArrangedRequest.labType === "软件实验室"){
                this.$store.state.beingArrangedRequest.labType = "SOFTWARE"
            }else if (this.$store.state.beingArrangedRequest.labType === "计算机硬件实验室"){
                this.$store.state.beingArrangedRequest.labType = "HARDWARE"
            }else if (this.$store.state.beingArrangedRequest.labType === "计算机网络实验室"){
                this.$store.state.beingArrangedRequest.labType = "NETWORK"
            }else if (this.$store.state.beingArrangedRequest.labType === "计算机系统实验室"){
                this.$store.state.beingArrangedRequest.labType = "SYSTEM"
            }else if (this.$store.state.beingArrangedRequest.labType === "物联网实验室"){
                this.$store.state.beingArrangedRequest.labType = "IOT"
            }
        },
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
            this.request.get("/laboratory/general/type",{
                params:{
                    labType: this.form.labType
                }
            }).then(res => {
                console.log(res)
                this.tableData = res.data
            })
        },
        handleStudentCountChange(){
            this.request.get("/laboratory/general/capacity",{
                params:{
                    studentCount: this.form.studentCount
                }
            }).then(res => {
                console.log(res)
                this.tableData = res.data
            })
        },
        confirmArrange(labID,name,deviceCount) {
            this.createLongArrangement.labID = labID
            // console.log(this.createLongArrangement)
            // console.log(this.BeingArrangedTableData[0])
            // console.log(name)
            this.$confirm('确认排课?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                if (name === this.BeingArrangedTableData[0].labType && this.BeingArrangedTableData[0].studentCount <= deviceCount){
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
                }else{
                    this.$message.error("排课失败,请检查排课")
                }
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消'
                });
            });

        },
        checkByTimeAndType(){
            this.switchToParams()
            this.request.get("/laboratory/for-instructor-requests/time-and-type",{
                params:{
                    slot: this.$store.state.beingArrangedRequest.slot,
                    labType: this.$store.state.beingArrangedRequest.labType,
                    startWeek:this.$store.state.beingArrangedRequest.startWeek,
                    weekday: this.$store.state.beingArrangedRequest.weekday,
                    endWeek: this.$store.state.beingArrangedRequest.endWeek
                }
            }).then(res => {
                for (let i = 0; i < res.data.length; i++) {
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
                }
                console.log(res)
                this.tableData = res.data
                this.switchToEasyWayToSee()
            })
        },
    }
}
</script>

<style scoped>

</style>