<template>
    <div>
        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
        </div>

        <el-table :data="tableData" :header-cell-class-name="headerBg" border stripe>
            <el-table-column label="维修工单ID" prop="repairRequestID">
            </el-table-column>
            <el-table-column label="报修日期" prop="requestTime">
            </el-table-column>
            <el-table-column label="实验室ID" prop="labID">
            </el-table-column>
            <el-table-column label="故障描述" prop="requestDescription">
            </el-table-column>
            <el-table-column label="维修开始时间" prop="repairStartTime">
            </el-table-column>
            <el-table-column label="维修结束时间" prop="repairEndTime">
            </el-table-column>
            <el-table-column label="实验员留言" prop="technicianMessage">
            </el-table-column>
            <el-table-column label="状态" prop="status" width="100px">
<!--                <el-popover>-->
<!--                    <el-tag slot="reference" :type="this.handledState==='已维修' ? 'success' : (this.handledState === '未维修' ? 'info' : 'warning')">-->
<!--                        {{ handledState }}-->
<!--                    </el-tag>-->
<!--                </el-popover>-->
            </el-table-column>
        </el-table>

        <el-dialog :visible.sync="dialogFormVisible" title="报修信息" width="32%">
            <el-form :label-width="formLabelWidth">
<!--                <el-form-item label="报修教师ID">-->
<!--                    <el-input v-model.number="addRepairRequest.instructorID" autocomplete="off" disabled></el-input>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="状态">-->
<!--                    <el-input v-model="repair.status" autocomplete="off" disabled></el-input>-->
<!--                </el-form-item>-->
                <el-form-item label="实验室编号">
                    <el-input v-model.number="addRepairRequest.labID" autocomplete="off"></el-input>
                </el-form-item>
<!--                <el-form-item label="申请报修时间">-->
<!--                    <el-date-picker-->
<!--                        v-model="repair.requestTime"-->
<!--                        type="datetime"-->
<!--                        value-format="yyyy-MM-dd HH:mm:ss"-->
<!--                        placeholder="选择日期时间">-->
<!--                    </el-date-picker>-->
<!--                </el-form-item>-->
                <el-form-item label="故障描述">
                    <el-input v-model="addRepairRequest.requestDescription" autocomplete="off" type="textarea"></el-input>
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

            formLabelWidth: '80px',
            username: "",
            role: "",
            password: "",
            dialogFormVisible: false,
            multipleSelection: [],
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            tempLabNumber:"",
            repair:{
                status: "",
                requestTime: "",
                requestDescription: "",
                repairRequestID: 0,
                repairEndTime: "",
                labID: 0,
                repairStartTime: "",
                technicianMessage: "",
                instructorID: 0
            },

            addRepairRequest:{
                repairRequestID: 0,
                requestDescription: "",
                instructorID: 0,
                labID: "",
            },

            tempDataAddRepairRequest:{
                repairRequestID: 0,
                requestDescription: "",
                instructorID: 0,
                labID: "",
            }

        }
    },
    created() {
        this.load()
    },
    methods: {
        setData() {
            this.tempDataAddRepairRequest.repairRequestID = this.addRepairRequest.repairRequestID
            this.tempDataAddRepairRequest.requestDescription = this.addRepairRequest.requestDescription
            this.tempDataAddRepairRequest.instructorID = this.addRepairRequest.instructorID
            this.tempDataAddRepairRequest.labID = this.addRepairRequest.labID
        },
        load() {
            this.request.get("/repair-request/instructor",{
                params:{
                    instructorID: this.user.userID,
                }
            }).then(res => {
                for (let i = 0; i < res.data.length; i++) {
                    // let origin_appeal_time = res[i].appeal_time
                    // let date1 = new Date(origin_appeal_time);
                    // let time1 = date1.getFullYear() + '-' + ((date1.getMonth() + 1) < 10 ? "0" + (date1.getMonth() + 1) : (date1.getMonth() + 1)) + '-' + (date1.getDate() < 10 ? "0" + date1.getDate() : date1.getDate()) + ' ' + (date1.getHours() < 10 ? "0" + date1.getHours() : date1.getHours()) + ':' + (date1.getMinutes() < 10 ? "0" + date1.getMinutes() : date1.getMinutes()) + ':' + (date1.getSeconds() < 10 ? "0" + date1.getSeconds() : date1.getSeconds());
                    // res[i].appeal_time = time1
                    if (res.data[i].status ==='NOT_REPAIRED') {
                        this.handledState = "未维修"
                        res.data[i].status ='未维修'
                    }else if(res.data[i].status ==='REPAIRED') {
                        this.handledState = "已维修"
                        res.data[i].status ='已维修'
                    }else {
                        this.handledState = "维修中"
                        res.data[i].status ='维修中'
                    }
                }
                console.log(res)
                this.tableData = res.data
            })
        },
        resetDialog() {
             this.addRepairRequest.repairRequestID= 0
             this.addRepairRequest.requestDescription= ""
             this.addRepairRequest.instructorID=this.user.userID
             this.addRepairRequest.labID= ""
        },
        save() {
            this.addRepairRequest.instructorID = this.user.userID
            console.log(this.addRepairRequest)
            this.request.get("/laboratory/labnum-to-labid", {
                params:{
                    labNumber:this.addRepairRequest.labID
                }
            }).then(res =>{
                this.setData()
                this.tempLabNumber = this.addRepairRequest.labID
                this.tempDataAddRepairRequest.labID = res.data
            }).catch(error => {
                this.$message.error("申请失败!实验室不存在或已处于维修状态")
            }).then(() =>{
                this.request.post("/repair-request",this.tempDataAddRepairRequest).then(res => {
                    if (res) {
                        this.$message.success("申请成功")
                        this.dialogFormVisible = false
                        this.resetDialog()
                        this.load()
                    } else {
                        this.$message.error("申请失败")
                    }
                }).catch(error => {
                    // console.log(error)
                    this.$message.error("实验室不存在或已处于维修状态！")
                })
            })


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
        handleAdd() {
            this.dialogFormVisible = true;
            // this.form = {}
        },
        reset() {
            this.$message.success("已重置")
        }
    }
}
</script>

<style scoped>

</style>