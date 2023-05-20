<template>
    <div>
        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
        </div>

        <el-table :data="tableData" :header-cell-class-name="headerBg" border stripe>
            <!--            <el-table-column prop="app_time" label="申请时间" >-->
            <!--            </el-table-column>-->
            <el-table-column label="教师申请ID" prop="instructorRequestID" width="90px">
            </el-table-column>
            <el-table-column label="申请时间" prop="requestTime" width="140px">
            </el-table-column>
            <el-table-column label="课程名称" prop="course" width="100px">
            </el-table-column>
            <el-table-column label="实验室类型" prop="labType" width="120px">
            </el-table-column>
            <el-table-column label="星期" prop="weekday" width="100px">
            </el-table-column>
            <el-table-column label="学生班级" prop="studentClass" width="120px">
            </el-table-column>
            <el-table-column label="学生人数" prop="studentCount" width="70px">
            </el-table-column>
            <el-table-column label="起始周" prop="startWeek" width="60px">
            </el-table-column>
            <el-table-column label="结束周" prop="endWeek" width="60px">
            </el-table-column>
            <el-table-column label="节次" prop="slot" width="60px">
            </el-table-column>
            <el-table-column label="状态" prop="status" width="70px">
            </el-table-column>

            <el-table-column align="center" label="操作" >
                <template slot-scope="scope">
                    <el-button type="warning" @click="handleUpdate(scope.row.instructorRequestID)">修改 <i class="el-icon-edit"></i>
                    </el-button>
<!--                    <el-button type="danger" @click="del(scope.row.appeal_id)">撤销 <i-->
<!--                            class="el-icon-remove-outline"></i></el-button>-->
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :visible.sync="applicationDialogFormVisible" title="填写申请信息" width="32%">
            <el-form :label-width="formLabelWidth">
                <el-form-item label="课程名称">
                    <el-input v-model="addApplication.course" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="学生人数">
                    <el-input v-model="addApplication.studentCount" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="学生班级">
                    <el-input v-model="addApplication.studentClass" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="星期">
                    <el-select v-model="addApplication.weekday" placeholder="请选择星期">
                        <el-option label="星期一" value="MONDAY"></el-option>
                        <el-option label="星期二" value="TUESDAY"></el-option>
                        <el-option label="星期三" value="WEDNESDAY"></el-option>
                        <el-option label="星期四" value="THURSDAY"></el-option>
                        <el-option label="星期五" value="FRIDAY"></el-option>
                        <el-option label="星期六" value="SATURDAY"></el-option>
                        <el-option label="星期日" value="SUNDAY"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="起始周">
                    <el-input-number v-model="addApplication.startWeek" :max="20" :min="1" label="选择周次"
                                     @change="handleChange"></el-input-number>
                </el-form-item>
                <el-form-item label="结束周">
                    <el-input-number v-model="addApplication.endWeek" :max="20" :min="1" label="选择周次"
                                     @change="handleChange"></el-input-number>
                </el-form-item>
                <el-form-item label="申请节次">
                    <el-select v-model="addApplication.slot" placeholder="请选择节次">
                        <el-option label="1-2" value="ONE_TO_TWO"></el-option>
                        <el-option label="3-5" value="THREE_TO_FIVE"></el-option>
                        <el-option label="6-7" value="SIX_TO_SEVEN"></el-option>
                        <el-option label="8-9" value="EIGHT_TO_NINE"></el-option>
                        <el-option label="10-12" value="TEN_TO_TWELVE"></el-option>
                        <el-option label="13-15" value="THIRTEEN_TO_FIFTEEN"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="申请实验室类型">
                    <el-select v-model="addApplication.labType" placeholder="请选择实验室类型">
                        <el-option label="软件实验室" value="SOFTWARE"></el-option>
                        <el-option label="计算机系统实验室" value="SYSTEM"></el-option>
                        <el-option label="计算机硬件实验室" value="HARDWARE"></el-option>
                        <el-option label="物联网实验室" value="IOT"></el-option>
                        <el-option label="计算机网络实验室" value="NETWORK"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="closeApplicationDialog">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog :visible.sync="modifyDialogFormVisible" title="修改申请信息" width="32%">
            <el-form :label-width="formLabelWidth">
                <el-form-item label="课程名称">
                    <el-input v-model="form.course" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="学生人数">
                    <el-input v-model="form.studentCount" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="学生班级">
                    <el-input v-model="form.studentClass" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="星期">
                    <el-select v-model="form.weekday" placeholder="请选择星期">
                        <el-option label="星期一" value="MONDAY"></el-option>
                        <el-option label="星期二" value="TUESDAY"></el-option>
                        <el-option label="星期三" value="WEDNESDAY"></el-option>
                        <el-option label="星期四" value="THURSDAY"></el-option>
                        <el-option label="星期五" value="FRIDAY"></el-option>
                        <el-option label="星期六" value="SATURDAY"></el-option>
                        <el-option label="星期日" value="SUNDAY"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="起始周">
                    <el-input-number v-model="form.startWeek" :max="20" :min="1" label="选择起始周次"
                                     @change="handleChange"></el-input-number>
                </el-form-item>
                <el-form-item label="结束周">
                    <el-input-number v-model="form.endWeek" :max="20" :min="1" label="选择结束周次"
                                     @change="handleChange"></el-input-number>
                </el-form-item>
                <el-form-item label="申请节次">
                    <!--                    <el-input v-model="form.app_week" autocomplete="off"></el-input>-->
                    <el-select v-model="form.slot" placeholder="请选择节次">
                        <el-option label="1-2" value="ONE_TO_TWO"></el-option>
                        <el-option label="3-5" value="THREE_TO_FIVE"></el-option>
                        <el-option label="6-7" value="SIX_TO_SEVEN"></el-option>
                        <el-option label="8-9" value="EIGHT_TO_NINE"></el-option>
                        <el-option label="10-12" value="TEN_TO_TWELVE"></el-option>
                        <el-option label="13-15" value="THIRTEEN_TO_FIFTEEN"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="申请实验室类型">
                    <el-select v-model="form.labType" placeholder="请选择实验室类型">
                        <el-option label="软件实验室" value="SOFTWARE"></el-option>
                        <el-option label="计算机系统实验室" value="SYSTEM"></el-option>
                        <el-option label="计算机硬件实验室" value="HARDWARE"></el-option>
                        <el-option label="物联网实验室" value="IOT"></el-option>
                        <el-option label="计算机网络实验室" value="NETWORK"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="closeModifyDialog">取 消</el-button>
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

            addApplication:{
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
            },

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


            formLabelWidth: '80px',

            password: "",
            applicationDialogFormVisible: false,
            modifyDialogFormVisible: false,
            multipleSelection: [],
        }
    },
    created() {
        this.load()
        // this.form.app_name = this.user.username
    },
    methods: {
        load() {
            this.request.get("/instructor-request/instructor",{
                params:{
                    instructorID: 1,
                }
            } ).then(res => {
                console.log(res)
                console.log(res.data.length)
                for (let i = 0; i < res.data.length; i++) {
                    // let origin_appeal_time = res[i].appeal_time
                    // let date1 = new Date(origin_appeal_time);
                    // let time1 = date1.getFullYear() + '-' + ((date1.getMonth() + 1) < 10 ? "0" + (date1.getMonth() + 1) : (date1.getMonth() + 1)) + '-' + (date1.getDate() < 10 ? "0" + date1.getDate() : date1.getDate()) + ' ' + (date1.getHours() < 10 ? "0" + date1.getHours() : date1.getHours()) + ':' + (date1.getMinutes() < 10 ? "0" + date1.getMinutes() : date1.getMinutes()) + ':' + (date1.getSeconds() < 10 ? "0" + date1.getSeconds() : date1.getSeconds());
                    // res[i].appeal_time = time1
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
        del(app_id) {
            this.$confirm('该操作将会撤销实验室申请，是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.delete("/appeal/cancel/" + app_id).then(res => {
                    if (res) {
                        this.$message({
                            type: 'success',
                            message: '已取消!'
                        });
                        this.load()
                    } else {
                        this.$message.error("取消失败")
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消'
                });
            });
        },
        handleAdd() {
            this.applicationDialogFormVisible = true;
            // this.addApplication = {}
        },
        resetAddDialog(){
            this.addApplication.labType = ""
            this.addApplication.weekday =  ""
            this.addApplication.startWeek= 1
            this.addApplication.studentClass= ""
            this.addApplication.studentCount= -1
            this.addApplication.semesterID= 1
            this.addApplication.instructorID= 1
            this.addApplication.instructorRequestID= 1
            this.addApplication.endWeek=18
            this.addApplication.course= ""
            this.addApplication.slot=""
        },
        resetModifyDialog(){
            this.form.labType = ""
            this.form.weekday =  ""
            this.form.startWeek= 1
            this.form.studentClass= ""
            this.form.studentCount= -1
            this.form.semesterID= 1
            this.form.instructorID= 1
            this.form.instructorRequestID= 1
            this.form.endWeek=18
            this.form.course= ""
            this.form.slot=""
        },
        save() {
            console.log(this.form)
            this.request.post("/instructor-request",this.addApplication).then(res =>{
                console.log(res)
                if(res){
                    this.$message.success("申请成功")
                    this.applicationDialogFormVisible = false
                    this.resetAddDialog()
                    this.load()
                }else {
                    this.$message.error("申请失败")
                }
            })
        },
        save1() {
            console.log(this.form)
            this.request.put("/instructor-request",this.form).then(res =>{
                console.log(res)
                if(res){
                    this.$message.success("修改成功")
                    this.modifyDialogFormVisible = false
                    this.resetModifyDialog()
                    this.load()
                }else {
                    this.$message.error("修改失败")
                }
            })
        },
        handleChange(value) {
            // console.log(value)
        },
        closeApplicationDialog() {
            this.applicationDialogFormVisible = false;
            this.resetAddDialog()
        },
        closeModifyDialog() {
            this.modifyDialogFormVisible = false;
            this.resetModifyDialog()
        },
        handleUpdate(id) {
            this.request.get("/instructor-request/instructor",{
                params:{
                    instructorID: 1,
                }
            } ).then(res => {
                for (let i = 0; i < res.data.length; i++) {
                    if (res.data[i].status ==='NOT_ARRANGED') {
                        this.handledState = "未排课"
                    } else {
                        this.handledState = "已排课"
                    }
                    if (res.data[i].instructorRequestID === id){
                        this.form.labType = res.data[i].labType
                        this.form.weekday = res.data[i].weekday
                        this.form.startWeek = res.data[i].startWeek
                        this.form.studentClass = res.data[i].studentClass
                        this.form.studentCount = res.data[i].studentCount
                        this.form.semesterID = res.data[i].semesterID
                        this.form.instructorID = res.data[i].instructorID
                        this.form.instructorRequestID = res.data[i].instructorRequestID
                        this.form.endWeek = res.data[i].endWeek
                        this.form.course = res.data[i].course
                        this.form.slot = res.data[i].slot
                        break
                    }
                }
            })
            this.modifyDialogFormVisible = true;
        },
    }
}
</script>

<style scoped>

</style>

