<template>
    <div>
        <el-card>
            <div style="display: flex; margin: 10px 0;">
                <el-button type="primary" @click="handleAdd" style="margin-right: 10px">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
                <div style="display: flex; margin-left: 280px">
                    <h5 style="margin-top: 6px;margin-right: 10px">学生检索:</h5>
                    <el-input placeholder="请输入学生姓名" style="width: 200px" suffix-icon="el-icon-search" @input="input" v-model="value"></el-input>
                </div>
            </div>
        </el-card>
        <div style="margin: 10px 0; width: 175px" >
            <el-upload
                class="upload-demo"
                ref="upload"
                accept=".xls,.xlsx"
                :action="api"
                :headers="headers"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :on-change="handleChange"
                :on-error = "handleError"
                :on-success = "handleSuccess"
                :file-list="fileList"
                :limit="1"
                name="table"
                :auto-upload="false">
                <el-button slot="trigger" size="small" type="primary">批量导入</el-button>
                <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">确定上传</el-button>
            </el-upload>
        </div>

        <el-table :data="tableData" :header-cell-class-name="headerBg" border stripe>
            <el-table-column label="学生账号" prop="studentID">
            </el-table-column>
            <el-table-column label="学生姓名" prop="name">
            </el-table-column>
            <el-table-column label="专业" prop="major">
            </el-table-column>
            <el-table-column label="班级" prop="clazz">
            </el-table-column>
            <el-table-column align="center" label="操作">
                <template slot-scope="scope">
                    <el-popconfirm
                            title="是否确定重置密码？密码将被重置为“123456”"
                            @confirm="reset(scope.row.studentID)"
                    >
                        <el-button slot="reference" type="warning">重置密码 <i class="el-icon-edit"></i></el-button>
                    </el-popconfirm>
                    <el-button style="margin-left: 5px" type="danger" @click="del(scope.row.studentID)">删除 <i
                            class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :visible.sync="dialogFormVisible" title="学生信息" width="30%">
            <el-form :label-width="formLabelWidth">
<!--                <el-form-item label="学生ID">-->
<!--                    <el-input v-model="student_id" autocomplete="off"></el-input>-->
<!--                </el-form-item>-->
                <el-form-item label="姓名">
                    <el-input v-model="addUser.roleSpecificInfo.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="登录ID">
                    <el-input v-model.number="addUser.loginID" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="专业">
                    <el-input v-model="addUser.roleSpecificInfo.major" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="班级">
                    <el-input v-model="addUser.roleSpecificInfo.clazz" autocomplete="off"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelAdd">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>

export default {
    name: "Student",

    data() {
        return {
            api:this.GLOBAL.BASE_URL+"/user/batch?usertype=Student",
            headers:{
                Authorization: "Bearer "+JSON.parse(localStorage.getItem("user")).accessToken
            },
            value:"",
            tableData: [],
            fileList:[],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,

            formLabelWidth: '80px',

            student_id: "",
            student_name: "",
            student_major: "",
            student_class: "",

            dialogFormVisible: false,
            multipleSelection: [],

            headerBg: 'headerBg',

            resetPassword:{
                newPassword:"123456",
                userID:0
            },


            addUser:{
                roles: [
                    "ROLE_STUDENT"
                ],
                roleSpecificInfo: {
                    studentID: 0,
                    name:"",
                    major:"",
                    clazz:""
                },
                password: "123456",
                loginID: ""
            }

        }
    },
    created() {
        this.load()
        // console.log(this.currentSemester + "Ssssss")
    },
    // 绑定监听事件
    mounted() {
        window.addEventListener('keydown', this.keyDown)
    },
    // 销毁事件
    destroyed() {
        window.removeEventListener('keydown', this.keyDown, false)
    },
    computed: {
        currentSemester() {
            return this.$store.state.semester
        }
    },
    methods: {
        load() {
            console.log("23232323")
            this.request.get("/user/student/all").then(res=>{
                console.log(res)
                this.tableData = res.data
            })
        },
        cancelAdd() {
            this.dialogFormVisible = false;
            this.resetDialog()
        },
        resetDialog() {
            this.addUser.roleSpecificInfo.name = ""
            this.addUser.roleSpecificInfo.major = ""
            this.addUser.roleSpecificInfo.clazz = ""
        },
        save() {
            this.request.post("/user/student",this.addUser).then(res =>{
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
        },
        input(){
            console.log(this.value)
            this.request.get("/user/student/name",{
                params:{
                    name:this.value
                }
            }).then(res =>{
                this.tableData = res.data
            })
        },
        del(id) {
            console.log(id)
            this.$confirm('此操作将永久删除该条记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.delete("/user?userID="+id).then(res=>{
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

        reset(id) {
            this.resetPassword.userID = id
            // console.log(this.resetPassword)
            this.request.put("/user/password?",this.resetPassword).then(res=>{
                console.log(res)
                this.$message.success("已重置")
            })
        },
        handlePreview(file){
            console.log(file);
        },
        handleRemove(file,fileList){
            console.log(file, fileList);
        },
        handleChange(file){
            if (file.status === "ready" ){
                this.fileList.push(file)
                console.log(this.fileList)
            }
            console.log(file.status)
        },
        handleError(err,file,fileList){
            console.log(err)
            this.$message.error("导入失败！导入数据与原有数据冲突！")
        },
        handleSuccess(){
            this.$message.success("导入成功！")
            this.load()
        },
        submitUpload(){
            if (this.fileList.length === 0) {
                return this.$message.warning("请选取文件后再上传");
            }
            this.$refs.upload.submit();
        },
        keyDown(e) {
            // 回车则执行登录方法 enter键的ASCII是13
            if (e.keyCode === 13) {
                this.save() // 需要执行的方法方法
            }
        }
    }
}
</script>

<style scoped>

</style>