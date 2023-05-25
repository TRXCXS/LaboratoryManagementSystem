<template>
    <div>
        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
        </div>

        <el-table :data="tableData" :header-cell-class-name="headerBg" border stripe>
            <el-table-column label="申请ID" prop="studentRequestID" width="60px">
            </el-table-column>
            <el-table-column label="申请时间" prop="requestTime">
            </el-table-column>
            <el-table-column label="申请学期" prop="semesterID">
            </el-table-column>
            <el-table-column label="申请周次" prop="week" width="80px">
            </el-table-column>
            <el-table-column label="星期" prop="weekday" width="60px">
            </el-table-column>
            <el-table-column label="申请节次" prop="slot" width="80px">
            </el-table-column>
            <el-table-column label="申请实验室编号" prop="labNumber" >
            </el-table-column>
            <el-table-column label="申请原因" prop="reason" width="150px">
            </el-table-column>
            <el-table-column label="状态" prop="status" width="80px">
<!--                <el-popover>-->
<!--                    <el-tag slot="reference" :type="status==='通过' ? 'success' : (status === '未审核' ? 'info' : (status === '驳回' ? 'danger' : 'success') )">-->
<!--                        {{ this.status }}-->
<!--                    </el-tag>-->
<!--                </el-popover>-->
            </el-table-column>
            <el-table-column label="审批时间" prop="adminProcessTime" width="80px">
            </el-table-column>
            <el-table-column label="管理员留言" prop="adminMessage" width="100px">
            </el-table-column>
            <el-table-column align="center" label="操作" width="200px">
                <template slot-scope="scope">
                    <el-button type="warning" @click="handleUpdate(scope.row.studentRequestID,scope.row.status)">修改 <i class="el-icon-edit"></i>
                    </el-button>
                    <el-button type="primary" @click="confirm_finishUsing(scope.row.studentRequestID,scope.row.status)">使用完毕 <i
                            class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :visible.sync="dialogFormVisible" title="填写借用信息" width="32%">
            <el-form :label-width="formLabelWidth">
<!--                <el-form-item label="申请学生">-->
<!--                    <el-input v-model="addRequest.studentID" autocomplete="off" ></el-input>-->
<!--                </el-form-item>-->
                <el-form-item label="申请周次">
                    <el-input-number v-model="addRequest.week" :max="20" :min="1" label="选择周次"
                                     @change="handleChange"></el-input-number>
                </el-form-item>
                <el-form-item label="星期">
                    <el-select v-model="addRequest.weekday" placeholder="请选择星期">
                        <el-option label="星期一" value="MONDAY"></el-option>
                        <el-option label="星期二" value="TUESDAY"></el-option>
                        <el-option label="星期三" value="WEDNESDAY"></el-option>
                        <el-option label="星期四" value="THURSDAY"></el-option>
                        <el-option label="星期五" value="FRIDAY"></el-option>
                        <el-option label="星期六" value="SATURDAY"></el-option>
                        <el-option label="星期日" value="SUNDAY"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="申请节次">
                    <!--                    <el-input v-model="form.app_week" autocomplete="off"></el-input>-->
                    <el-select v-model="addRequest.slot" placeholder="请选择节次">
                        <el-option label="1-2" value="ONE_TO_TWO"></el-option>
                        <el-option label="3-5" value="THREE_TO_FIVE"></el-option>
                        <el-option label="6-7" value="SIX_TO_SEVEN"></el-option>
                        <el-option label="8-9" value="EIGHT_TO_NINE"></el-option>
                        <el-option label="10-12" value="TEN_TO_TWELVE"></el-option>
                        <el-option label="13-15" value="THIRTEEN_TO_FIFTEEN"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="申请实验室编号">
                    <el-input v-model.number="addRequest.labID" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="申请原因">
                    <el-input v-model="addRequest.reason" autocomplete="off" type="textarea"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="closeDialog">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog :visible.sync="dialogFormVisible1" title="修改申请信息" width="32%">
            <el-form :label-width="formLabelWidth">
<!--                <el-form-item label="申请学生">-->
<!--                    <el-input v-model="modifyRequest.studentID" autocomplete="off" disabled></el-input>-->
<!--                </el-form-item>-->
                <el-form-item label="申请周次">
                    <el-input-number v-model="modifyRequest.week" :max="20" :min="1" label="选择周次"
                                     @change="handleChange"></el-input-number>
                </el-form-item>
                <el-form-item label="星期">
                    <el-select v-model="modifyRequest.weekday" placeholder="请选择星期">
                        <el-option label="星期一" value="MONDAY"></el-option>
                        <el-option label="星期二" value="TUESDAY"></el-option>
                        <el-option label="星期三" value="WEDNESDAY"></el-option>
                        <el-option label="星期四" value="THURSDAY"></el-option>
                        <el-option label="星期五" value="FRIDAY"></el-option>
                        <el-option label="星期六" value="SATURDAY"></el-option>
                        <el-option label="星期日" value="SUNDAY"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="申请节次">
                    <el-select v-model="modifyRequest.slot" placeholder="请选择节次">
                        <el-option label="1-2" value="ONE_TO_TWO"></el-option>
                        <el-option label="3-5" value="THREE_TO_FIVE"></el-option>
                        <el-option label="6-7" value="SIX_TO_SEVEN"></el-option>
                        <el-option label="8-9" value="EIGHT_TO_NINE"></el-option>
                        <el-option label="10-12" value="TEN_TO_TWELVE"></el-option>
                        <el-option label="13-15" value="THIRTEEN_TO_FIFTEEN"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="申请实验室编号">
                    <el-input v-model.number="modifyRequest.labID" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="申请原因">
                    <el-input v-model="modifyRequest.reason" autocomplete="off" type="textarea"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible1 = false">取 消</el-button>
                <el-button type="primary" @click="save1">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
export default {
    name: "MyAppeal",
    data() {
        return {
            tableData: [],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',
            handledState: "",
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},

            form: {
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
                labID: "",
                semesterID: this.$store.state.semester,
                studentID: 0,
            },

            addRequest:{
                studentRequestID: 2,
                week: 0,
                weekday: "",
                slot: "",
                reason: "",
                labID: "",
                semesterID: this.$store.state.semester,
                studentID: 0,
            },

            modifyRequest:{
                studentRequestID: 2,
                week: 0,
                weekday: "",
                slot: "",
                reason: "",
                labID: "",
                semesterID: this.$store.state.semester,
                studentID: 0,
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
            labID: "",
            semesterID: this.$store.state.semester,
            studentID: 0,

            tempLabNumber:"",

            formLabelWidth: '80px',

            password: "",
            dialogFormVisible: false,
            dialogFormVisible1: false,
            multipleSelection: [],
            checkStatus:"",

            temp:{
                labID:3,
            }


        }
    },
    created() {
        this.load()
        // this.form.app_name = this.user.username
        // console.log(this.modifyRequest.semesterID)
    },
    methods: {
        load() {
            this.request.get("/student-request/student",{
                params:{
                    studentID:this.user.userID
                }
            }).then(res => {
                console.log(res)
                for (let i = 0; i < res.data.length; i++) {
                    if (res.data[i].status ==='NOT_VIEWED') {
                        this.handledState = "未审核"
                        res.data[i].status = "未审核"
                    }else if(res.data[i].status ==='APPROVED') {
                        this.handledState = "通过"
                        res.data[i].status = "通过"
                    }else if(res.data[i].status ==='DENIED') {
                        this.handledState = "驳回"
                        res.data[i].status = "驳回"
                    }else if(res.data[i].status ==='USE_COMPLETE') {
                        this.handledState = "使用完毕"
                        res.data[i].status = "使用完毕"
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
                        res.data[i].weekday = "星期日"
                    }
                }
                this.tableData = res.data
            })
        },
        confirm_finishUsing(RequestID,status) {
            this.$confirm('确定使用完毕?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                if (status === "通过"){
                    this.request.put("/student-request/use-complete?studentRequestID="+RequestID).then(res => {
                        if (res) {
                            this.load()
                            this.$message({
                                type: 'success',
                                message: '已更改状态!'
                            });
                            this.load()
                        } else {
                            this.$message.error("取消失败")
                        }
                    })
                }else {
                    this.$message({
                        type: 'error',
                        message: '管理员未通过审核、申请被驳回或实验室已使用完毕'
                    });
                }
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消'
                });
            });
        },
        handleAdd() {
            // this.form.app_name = this.user.username
            this.dialogFormVisible = true;
            // this.addRequest = {}
        },
        save() {
            this.addRequest.studentID = this.user.userID
            this.addRequest.semesterID = this.$store.state.semester
            this.request.get("/laboratory/labnum-to-labid", {
                params:{
                    labNumber:this.addRequest.labID
                }
            }).then(res =>{
                console.log(res.data)
                this.tempLabNumber = this.addRequest.labID
                this.addRequest.labID = res.data
            }).catch(error => {
                this.$message.error("申请失败!实验室不存在")
            }).then(() =>{
                this.request.post("/student-request",this.addRequest).then(res =>{
                    console.log(res)
                    if (res) {
                        this.$message.success("申请成功")
                        this.dialogFormVisible = false
                        this.addRequest = {}
                        this.load()
                    } else {
                        this.$message.error("申请失败")
                    }
                }).catch(error => {
                    this.$message.error("申请失败!实验室不存在")
                })
            })

        },
        save1() {
            if (this.status === "通过"){
                this.$message.warning("申请已通过，无法修改！")
            }else if (this.status === "使用完毕"){
                this.$message.warning("使用完毕，无法修改！")
            }else if (this.status === "驳回"){
                this.$message.warning("申请被驳回，无法修改！请重新申请！")
            }else {
                this.modifyRequest.studentID = this.user.userID
                this.modifyRequest.semesterID = this.$store.state.semester
                console.log(this.modifyRequest)

                this.request.get("/laboratory/labnum-to-labid", {
                    params:{
                        labNumber:this.modifyRequest.labID
                    }
                }).then(res =>{
                    console.log(res.data)
                    this.tempLabNumber = this.addRequest.labID
                    this.modifyRequest.labID = res.data
                }).catch(error => {
                    this.$message.error("修改失败!实验室不存在")
                }).then(()=>{
                    this.request.put("/student-request", this.modifyRequest).then(res =>{
                        console.log(res)
                        if (res) {
                            this.$message.success("修改成功")
                            this.dialogFormVisible1 = false
                            this.load()
                        } else {
                            this.$message.error("修改失败")
                        }
                    }).catch(error => {
                        this.$message.error("修改失败!实验室不存在")
                    })
                })
            }

        },
        handleChange() {

        },
        closeDialog() {
            this.dialogFormVisible = false;
            this.addRequest = {}
        },
        handleUpdate(id,status) {
            this.status = status
            this.request.get("/student-request/student",{
                params:{
                    studentID: this.user.userID,
                }
            } ).then(res => {
                console.log(res)
                for (let i = 0; i < res.data.length; i++) {
                    if (res.data[i].status ==='NOT_VIEWED') {
                        this.handledState = "未审核"
                    }else if(res.data[i].status ==='APPROVED') {
                        this.handledState = "通过"
                    }else if(res.data[i].status ==='DENIED') {
                        this.handledState = "驳回"
                    }else if(res.data[i].status ==='USE_COMPLETE') {
                        this.handledState = "使用完毕"
                    }
                    if (res.data[i].studentRequestID === id){
                        this.modifyRequest.studentID = res.data[i].studentID
                        this.modifyRequest.weekday = res.data[i].weekday
                        this.modifyRequest.week = res.data[i].week
                        this.modifyRequest.slot = res.data[i].slot
                        this.modifyRequest.reason = res.data[i].reason
                        this.modifyRequest.labID = res.data[i].labID
                        this.modifyRequest.studentRequestID = res.data[i].studentRequestID
                        this.modifyRequest.semesterID = res.data[i].semesterID
                        break
                    }
                }
            })
            this.dialogFormVisible1 = true;
        },
    }
}
</script>

<style scoped>

</style>