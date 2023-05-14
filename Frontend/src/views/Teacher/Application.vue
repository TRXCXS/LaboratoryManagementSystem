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
            <el-table-column label="上课学期" prop="requestTime" width="140px">
            </el-table-column>
            <el-table-column label="课程名称" prop="course" width="100px">
            </el-table-column>
            <el-table-column label="实验室类型" prop="labType" width="100px">
            </el-table-column>
            <el-table-column label="学生班级" prop="studentClass" width="150px">
            </el-table-column>
            <el-table-column label="学生人数" prop="studentCount" width="80px">
            </el-table-column>
            <el-table-column label="起始周" prop="startWeek" width="60px">
            </el-table-column>
            <el-table-column label="结束周" prop="endWeek" width="60px">
            </el-table-column>
            <el-table-column label="节次" prop="slot" width="50px">
            </el-table-column>
            <el-table-column label="状态" prop="status" width="100px">


                <el-popover>
                    <el-tag slot="reference" :type="this.handledState==='已排课' ? 'success' : 'info'">
                        {{ handledState }}
                    </el-tag>
                </el-popover>

            </el-table-column>
            <el-table-column align="center" label="操作" width="230px">
                <template slot-scope="scope">
                    <el-button type="warning" @click="handleUpdate(scope.row.desk_id)">修改 <i class="el-icon-edit"></i>
                    </el-button>
                    <el-button type="danger" @click="del(scope.row.appeal_id)">撤销 <i
                            class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :visible.sync="dialogFormVisible" title="填写申请信息" width="32%">
            <el-form :label-width="formLabelWidth">
                <el-form-item label="上课学期">
                    <!--                    <el-input v-model="form.app_semester" autocomplete="off"></el-input>-->
                    <el-select v-model="form.app_semester" placeholder="请选择学期">
                        <el-option label="2022-2023-1" value="2022-2023-1"></el-option>
                        <el-option label="2022-2023-1" value="2022-2023-1"></el-option>
                        <el-option label="2022-2023-1" value="2022-2023-1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="申请教师">
                    <el-input v-model="form.app_name" autocomplete="off" disabled></el-input>
                </el-form-item>
                <el-form-item label="课程名称">
                    <el-input v-model="form.course_name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="起始周">
                    <el-input-number v-model="form.start_week" :max="20" :min="1" label="选择周次"
                                     @change="handleChange"></el-input-number>
                </el-form-item>
                <el-form-item label="结束周">
                    <el-input-number v-model="form.end_week" :max="20" :min="1" label="选择周次"
                                     @change="handleChange"></el-input-number>
                </el-form-item>
                <el-form-item label="申请节次">
                    <!--                    <el-input v-model="form.app_week" autocomplete="off"></el-input>-->
                    <el-select v-model="form.app_section" placeholder="请选择节次">
                        <el-option label="1-2" value="2022-2023-1"></el-option>
                        <el-option label="3-5" value="2022-2023-1"></el-option>
                        <el-option label="6-7" value="2022-2023-1"></el-option>
                        <el-option label="8-9" value="2022-2023-1"></el-option>
                        <el-option label="10-12" value="2022-2023-1"></el-option>
                        <el-option label="13-15" value="2022-2023-1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="申请实验室类型">
                    <el-input v-model="form.lab_type" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="closeDialog">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog :visible.sync="dialogFormVisible1" title="修改申请信息" width="32%">
            <el-form :label-width="formLabelWidth">
                <el-form-item label="申请学期">
                    <!--                    <el-input v-model="form.app_semester" autocomplete="off"></el-input>-->
                    <el-select v-model="form.app_semester" placeholder="请选择学期">
                        <el-option label="2022-2023-1" value="2022-2023-1"></el-option>
                        <el-option label="2022-2023-1" value="2022-2023-1"></el-option>
                        <el-option label="2022-2023-1" value="2022-2023-1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="申请学生">
                    <el-input v-model="form.app_name" autocomplete="off" disabled></el-input>
                </el-form-item>
                <el-form-item label="申请周次">
                    <!--                    <el-input v-model="form.app_week" autocomplete="off"></el-input>-->
                    <el-input-number v-model="form.app_week" :max="20" :min="1" label="选择周次"
                                     @change="handleChange"></el-input-number>
                </el-form-item>
                <el-form-item label="申请节次">
                    <!--                    <el-input v-model="form.app_week" autocomplete="off"></el-input>-->
                    <el-select v-model="form.app_section" placeholder="请选择节次">
                        <el-option label="1-2" value="2022-2023-1"></el-option>
                        <el-option label="3-5" value="2022-2023-1"></el-option>
                        <el-option label="6-7" value="2022-2023-1"></el-option>
                        <el-option label="8-9" value="2022-2023-1"></el-option>
                        <el-option label="10-12" value="2022-2023-1"></el-option>
                        <el-option label="13-15" value="2022-2023-1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="申请实验室编号">
                    <el-input v-model="form.app_labNum" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="申请原因">
                    <el-input v-model="form.app_reason" autocomplete="off" type="textarea"></el-input>
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
        const item = {
            app_time: "2023-4-15-20:30:15",
            app_semester: "2023-2024-1",
            course_name: "现代软件开发",
            lab_type: "机房",
            class: "计算机科学与技术三班",
            stu_num: "55",
            start_week: "1",
            end_week: "12",
            app_section: "5-6",
        };
        return {
            // tableData: [],
            tableData: Array(10).fill(item),
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',
            handledState: "",
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},

            form: {
                app_time: "",
                app_semester: "",
                course_name: "",
                lab_type: "",
                class: "",
                stu_num: "",
                start_week: "",
                end_week: "",
                app_section: "",
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
            dialogFormVisible: false,
            dialogFormVisible1: false,
            multipleSelection: [],


        }
    },
    created() {
        this.load()
        this.form.app_name = this.user.username
    },
    methods: {
        load() {
            this.request.get("/instructor-request/instructor" + this.user.user_id).then(res => {
                console.log(res)
                for (let i = 0; i < res.length; i++) {
                    let origin_appeal_time = res[i].appeal_time
                    let date1 = new Date(origin_appeal_time);
                    let time1 = date1.getFullYear() + '-' + ((date1.getMonth() + 1) < 10 ? "0" + (date1.getMonth() + 1) : (date1.getMonth() + 1)) + '-' + (date1.getDate() < 10 ? "0" + date1.getDate() : date1.getDate()) + ' ' + (date1.getHours() < 10 ? "0" + date1.getHours() : date1.getHours()) + ':' + (date1.getMinutes() < 10 ? "0" + date1.getMinutes() : date1.getMinutes()) + ':' + (date1.getSeconds() < 10 ? "0" + date1.getSeconds() : date1.getSeconds());
                    res[i].appeal_time = time1

                    if (res[i].admin_scrutinized) {
                        this.handledState = "通过"
                        this.tagForm = "success"
                    } else {
                        this.handledState = "未审核"
                        this.tagForm = "info"
                    }
                }
                this.tableData = res
            })
            console.log(this.user.user_id)
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
            this.form.app_name = this.user.username
            this.dialogFormVisible = true;
            // this.form = {}
        },
        save() {
            // this.request.post("/section/?name="+this.form.sec_name+"&length="+this.form.sec_length+"&width="+this.form.sec_width+"&library="+this.form.library+"&floor="+this.form.floor).then(res =>{
            //     if (res) {
            //         this.$message.success("添加区域成功")
            //         this.dialogFormVisible = false
            //         // this.resetDialog()
            //         this.load()
            //     } else {
            //         this.$message.error("添加区域失败")
            //     }
            // })
        },
        save1() {
            // let temp = "/desk/?deskId="+this.desk_id+"&axis1="+this.axis1+"&axis2="+this.axis2+"&capacity="+this.capacity+"&power="+this.power+"&sectionId="+this.$route.query.id
            // console.log(temp)
            // this.request.put(temp).then(res =>{
            //     if (res) {
            //         this.$message.success("修改成功")
            //         this.dialogFormVisible1 = false
            //         this.resetDialog()
            //         this.load()
            //     } else {
            //         this.$message.error("修改失败")
            //     }
            // })
        },
        handleChange(value) {
            console.log(value)
        },
        closeDialog() {
            this.dialogFormVisible = false;
            this.form = {}
        },
        handleUpdate(id) {
            // let sec_id = this.$route.query.id
            // this.request.get("/desk/"+sec_id).then(res=>{
            //     console.log(res)
            //     for(let i = 0;i<res.length;i++){
            //         if (res[i].desk_id === id){
            //             this.power = res[i].power
            //             this.desk_id = res[i].desk_id
            //             this.axis1 = res[i].axis1
            //             this.axis2 = res[i].axis2
            //             this.capacity = res[i].capacity
            //             break
            //         }
            //     }
            // })
            this.dialogFormVisible1 = true;
        },
    }
}
</script>

<style scoped>

</style>

