<template>
    <div>
        <el-card style="margin-bottom: 10px">
            <el-button type="primary" @click="checkAll">查看全部申请</el-button>
            <el-button type="primary" @click="checkUnhandled">查看未审核的申请</el-button>
        </el-card>
        <el-table :data="tableData" :header-cell-class-name="headerBg" border stripe style="margin-bottom: 20px">

            <el-table-column label="申请ID" prop="studentRequestID">
            </el-table-column>
            <el-table-column label="申请时间" prop="requestTime">
            </el-table-column>
            <el-table-column label="申请学期" prop="semesterID">
            </el-table-column>
            <el-table-column label="申请周次" prop="week" width="100px">
            </el-table-column>
            <el-table-column label="星期" prop="weekday" >
            </el-table-column>
            <el-table-column label="申请节次" prop="slot" width="100px">
            </el-table-column>
            <el-table-column label="申请实验室编号" prop="labID" >
            </el-table-column>
            <el-table-column label="申请原因" prop="reason" width="150px">
            </el-table-column>
            <el-table-column label="状态" prop="status" width="100px">
            </el-table-column>


            <el-table-column align="center" label="操作" width="190px">
                <template slot-scope="scope">
                    <el-button type="primary" @click="checkByTimeAndID(scope.row.weekday,scope.row.slot,scope.row.labID,scope.row.week)" style="margin-bottom: 5px">查询可用实验室</el-button>
                    <el-button type="success" @click="approve(scope.row.studentRequestID)" style="margin-bottom: 5px">通过 <i class="el-icon-edit"></i>
                    </el-button>
                    <el-button type="danger" @click="disapprove(scope.row.studentRequestID)">不通过 <i
                            class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-table-column>
        </el-table>
        <h3>可用实验室：</h3>
        <el-table :data="checkAvailableTableData" :header-cell-class-name="headerBg" border >
            <el-table-column label="实验室ID" prop="labID">
            </el-table-column>
            <el-table-column label="实验室房间号" prop="labNumber">
            </el-table-column>
            <el-table-column label="实验室名称" prop="name">
            </el-table-column>
            <el-table-column label="实验室类型" prop="labType" >
            </el-table-column>
            <el-table-column label="设备数量" prop="deviceCount" >
            </el-table-column>
        </el-table>
        <el-dialog :visible.sync="dialogFormVisible" title="确认驳回" width="32%">
            <el-form :label-width="formLabelWidth">
                <el-form-item label="驳回说明">
                    <el-input v-model="message" autocomplete="off" type="textarea"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancel">取 消</el-button>
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
            api: this.GLOBAL.BASE_URL,
            tableData:[],
            checkAvailableTableData:[],
            // tableData: Array(10).fill(item),
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


            studentRequestID: "",
            week: 0,
            weekday: "",
            slot: "",
            reason: "",
            requestTime: "",
            status: "",
            adminProcessTime: "",
            adminMessage: "",
            useCompleteTime: "",
            labID: 0,
            semesterID: 0,
            studentID: 0,

            shortArrangement:{
                slot: "",
                studentRequestID: 0,
                week: 0,
                labID: 0,
                weekday: "",
                shortArrangementID: 0
            },

            message:"",
            disapproveID:-1



        }
    },
    created() {
        this.load()
    },
    methods: {
        resetDialog() {
            this.dialogFormVisible = false
            this.disapproveID = -1
            this.message = ""
        },
        cancel(){
            this.dialogFormVisible = false
            this.message = ""
            this.disapproveID = -1
        },
        save() {
            this.request.put("/student-request/deny?studentRequestID=" + this.disapproveID + "&adminMessage=" + this.message).then(res => {
                if (res) {
                    this.$message.success("已驳回学生申请")
                    this.dialogFormVisible = false
                    this.resetDialog()
                    this.load()
                } else {
                    this.$message.error("操作失败")
                }
            })
        },
        handleAdd(id) {
            this.dialogFormVisible = true;
        },
        checkAll(){
            this.request.get("/student-request/all").then(res => {
                for (let i = 0; i < res.data.length; i++) {
                    // let origin_appeal_time = res[i].appeal_time
                    // let date1 = new Date(origin_appeal_time);
                    // let time1 = date1.getFullYear() + '-' + ((date1.getMonth() + 1) < 10 ? "0" + (date1.getMonth() + 1) : (date1.getMonth() + 1)) + '-' + (date1.getDate() < 10 ? "0" + date1.getDate() : date1.getDate()) + ' ' + (date1.getHours() < 10 ? "0" + date1.getHours() : date1.getHours()) + ':' + (date1.getMinutes() < 10 ? "0" + date1.getMinutes() : date1.getMinutes()) + ':' + (date1.getSeconds() < 10 ? "0" + date1.getSeconds() : date1.getSeconds());
                    // res[i].appeal_time = time1
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
                    if (res.data[i].status ==='NOT_VIEWED') {
                        res.data[i].status = "未审核"
                    }else if(res.data[i].status ==='APPROVED') {
                        res.data[i].status = "通过"
                    }else if(res.data[i].status ==='DENIED') {
                        res.data[i].status = "驳回"
                    }else if(res.data[i].status ==='USE_COMPLETE') {
                        res.data[i].status = "使用完毕"
                    }
                }

                this.tableData = res.data
            })
        },
        checkUnhandled(){
            this.load()
        },
        load() {
            this.request.get("/student-request/unhandled").then(res => {
                for (let i = 0; i < res.data.length; i++) {
                    // let origin_appeal_time = res[i].appeal_time
                    // let date1 = new Date(origin_appeal_time);
                    // let time1 = date1.getFullYear() + '-' + ((date1.getMonth() + 1) < 10 ? "0" + (date1.getMonth() + 1) : (date1.getMonth() + 1)) + '-' + (date1.getDate() < 10 ? "0" + date1.getDate() : date1.getDate()) + ' ' + (date1.getHours() < 10 ? "0" + date1.getHours() : date1.getHours()) + ':' + (date1.getMinutes() < 10 ? "0" + date1.getMinutes() : date1.getMinutes()) + ':' + (date1.getSeconds() < 10 ? "0" + date1.getSeconds() : date1.getSeconds());
                    // res[i].appeal_time = time1
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
                    if (res.data[i].status ==='NOT_VIEWED') {
                        res.data[i].status = "未审核"
                    }else if(res.data[i].status ==='APPROVED') {
                        res.data[i].status = "通过"
                    }else if(res.data[i].status ==='DENIED') {
                        res.data[i].status = "驳回"
                    }else if(res.data[i].status ==='USE_COMPLETE') {
                        res.data[i].status = "使用完毕"
                    }
                }

                this.tableData = res.data
            })
        },
        approve(id) {
            this.request.get("/student-request/student",{
                params:{
                    studentID:2
                }
            }).then(res => {
                for (let i = 0; i < res.data.length; i++) {
                    if (res.data[i].studentRequestID === id){
                        this.shortArrangement.slot = res.data[i].slot
                        this.shortArrangement.studentRequestID = res.data[i].studentRequestID
                        this.shortArrangement.week = res.data[i].week
                        this.shortArrangement.labID = res.data[i].labID
                        this.shortArrangement.weekday = res.data[i].weekday
                    }
                }
            })
            this.$confirm('确认通过?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                // console.log(this.shortArrangement)
                this.request.post("/short-arrangement", this.shortArrangement).then(res=>{
                    if (res) {
                        this.$message({
                            type: 'success',
                            message: '已通过学生申请!'
                        });
                        this.load()
                    } else {
                        this.$message.error("处理失败")
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消'
                });
            });
        },
        disapprove(id) {
            this.dialogFormVisible = true
            this.disapproveID = id
            console.log(id)
        },
        checkByTimeAndID(weekday,slot,labID,week){
            this.request.get("/laboratory/for-student-requests/time-and-id",{
                params:{
                    weekday:weekday,
                    slot:slot,
                    labID:labID,
                    week:week,
                }
            }).then(res => {
                this.checkAvailableTableData = res.data
            })
        },
    },
}
</script>

<style scoped>

</style>