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
            <el-table-column label="申请实验室门牌号" prop="labNumber" >
            </el-table-column>
            <el-table-column label="申请原因" prop="reason" width="150px">
            </el-table-column>
            <el-table-column label="状态" prop="status" width="100px">
            </el-table-column>


            <el-table-column align="center" label="操作" width="190px">
                <template slot-scope="scope">
                    <el-button type="primary" @click="checkByTimeAndID(scope.row.weekday,scope.row.slot,scope.row.labNumber,scope.row.week,scope.row.status)" style="margin-bottom: 5px">查询可用实验室</el-button>
                    <el-button type="success" @click="approve(scope.row.studentRequestID,scope.row.status,scope.row.labNumber,scope.row.weekday,scope.row.week,scope.row.slot)" style="margin-bottom: 5px" :disabled="passDisable">通过 <i class="el-icon-edit"></i>
                    </el-button>
                    <el-button type="danger" @click="disapprove(scope.row.studentRequestID,scope.row.status)">不通过 <i
                            class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-table-column>
        </el-table>
        <h3>可用实验室：</h3>
        <el-table :data="checkAvailableTableData" :header-cell-class-name="headerBg" border stripe>
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

            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
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
            semesterID: this.$store.state.semester,
            studentID: 0,

            shortArrangement:{
                slot: "",
                studentRequestID: 0,
                week: 0,
                labID: 0,
                weekday: "",
                shortArrangementID: 0
            },

            tempShortArrangement:{
                slot: "",
                studentRequestID: 0,
                week: 0,
                labID: 0,
                weekday: "",
                shortArrangementID: 0
            },

            message:"",
            disapproveID:-1,
            tempLabID:"",

            passDisable:false,
        }
    },
    created() {
        this.load()
    },
    methods: {
        setData(){
            this.tempShortArrangement.slot = this.shortArrangement.slot
            this.tempShortArrangement.studentRequestID = this.shortArrangement.studentRequestID
            this.tempShortArrangement.week = this.shortArrangement.week
            this.tempShortArrangement.labID = this.shortArrangement.labID
            this.tempShortArrangement.weekday = this.shortArrangement.weekday
            this.tempShortArrangement.shortArrangementID = this.shortArrangement.shortArrangementID
        },
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
                console.log(res)
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
                        res.data[i].weekday = "星期日"
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
                        res.data[i].weekday = "星期日"
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
                console.log(res)
                this.tableData = res.data
            })
        },
        approve(id,status,labID,weekday,week,slot) {
            if (status === "未审核"){
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
                }else if(weekday ==='星期日') {
                    weekday = "SUNDAY"
                }
                this.shortArrangement.slot = slot
                this.shortArrangement.studentRequestID = id
                this.shortArrangement.week = week
                this.shortArrangement.labID = labID
                this.shortArrangement.weekday = weekday
                this.$confirm('确认通过?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    // console.log(this.shortArrangement)


                    this.request.get("/laboratory/labnum-to-labid", {
                        params:{
                            labNumber:this.shortArrangement.labID
                        }
                    }).then(res =>{
                        console.log(res.data)
                        this.setData()
                        this.tempShortArrangement.labID = res.data
                    }).catch(error => {
                        // this.$message.error("修改失败!实验室不存在")
                    }).then(()=>{
                        this.request.post("/short-arrangement", this.tempShortArrangement).then(res=>{
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
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消'
                    });
                });
            }else {
                this.$message.error("已经处理过啦！")
            }
        },
        disapprove(id,status) {
            if (status === "未审核"){
                this.dialogFormVisible = true
                this.disapproveID = id
                console.log(id)
            }else {
                this.$message.error("已经处理过啦！")
            }
        },
        checkByTimeAndID(weekday,slot,labID,week,status){
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
            }else if(weekday ==='星期日') {
                weekday = "SUNDAY"
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
            // console.log(weekday,slot,labID,week)
            if (status === "未审核"){

                this.request.get("/laboratory/labnum-to-labid", {
                    params:{
                        labNumber:labID
                    }
                }).then(res =>{
                    console.log(res.data)
                    this.tempLabID = res.data
                }).catch(error => {
                    // this.$message.error("修改失败!实验室不存在")
                }).then(()=>{
                    this.request.get("/laboratory/for-student-requests/time-and-id",{
                        params:{
                            weekday:weekday,
                            slot:slot,
                            labID:this.tempLabID,
                            week:week,
                        }
                    }).then(res => {
                        let temp = []
                        temp.push(res.data)
                        this.checkAvailableTableData = temp
                        console.log(res)
                        if (res.data === null){
                            this.passDisable = true
                        }
                    })
                })
            }else {
                this.$message.error("已经处理过啦！")
            }

        },
    },
}
</script>

<style scoped>

</style>