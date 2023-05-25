<template>
    <div>
        <el-card>
            <div style="display: flex; margin: 10px 0">
                <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
                <div style="display: flex ; margin-left: 280px">
                    <h5 style="margin-top: 6px; margin-right: 10px">教师检索:</h5>
                    <el-input placeholder="请输入教师姓名" style="width: 200px" suffix-icon="el-icon-search" @input="input" v-model="value"></el-input>
                </div>
            </div>
        </el-card>


        <div style="margin: 10px 0; width: 210px" >
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
                <el-button slot="trigger" size="small" type="primary">批量导入教师</el-button>
                <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">确定上传</el-button>
            </el-upload>
        </div>
        <el-table :data="tableData" :header-cell-class-name="headerBg" border stripe>
            <el-table-column label="教师ID" prop="userID">
            </el-table-column>
            <el-table-column label="工号" prop="loginID">
            </el-table-column>
            <el-table-column label="教师姓名" prop="roleSpecificInfo.name">
            </el-table-column>
            <el-table-column label="职称" prop="roleSpecificInfo.instructorTitle">
            </el-table-column>
            <el-table-column align="center" label="操作" width="300px">
                <template slot-scope="scope">
                    <el-button style="margin-right: 5px" type="primary" @click="handleUpdate(scope.row.userID)">修改 <i
                            class="el-icon-edit"></i></el-button>
                    <el-popconfirm
                            title="是否确定重置密码？密码将被重置为“123456”"
                            @confirm="reset(scope.row.userID)"
                    >
                        <el-button slot="reference" type="warning">重置密码 <i class="el-icon-edit"></i></el-button>
                    </el-popconfirm>
                    <el-button style="margin-left: 5px" type="danger" @click="del(scope.row.userID)">删除 <i
                            class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :visible.sync="dialogFormVisible" title="教师信息" width="30%">
            <el-form :label-width="formLabelWidth">
<!--                <el-form-item label="教师ID">-->
<!--                    <el-input v-model="teacher_id" autocomplete="off"></el-input>-->
<!--                </el-form-item>-->
                <el-form-item label="姓名">
                    <el-input v-model="addUser.roleSpecificInfo.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="登录ID">
                    <el-input v-model="addUser.loginID" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="addUser.password" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="职称">
                    <el-input v-model="addUser.roleSpecificInfo.title" autocomplete="off"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelAdd">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog :visible.sync="ModifyDialogFormVisible" title="教师信息" width="30%">
            <el-form :label-width="formLabelWidth">
                <!--                <el-form-item label="教师ID">-->
                <!--                    <el-input v-model="teacher_id" autocomplete="off"></el-input>-->
                <!--                </el-form-item>-->
                <el-form-item label="姓名">
                    <el-input v-model="modifyUser.roleSpecificInfo.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="登录ID">
                    <el-input v-model="modifyUser.loginID" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="职称">
                    <el-input v-model="modifyUser.roleSpecificInfo.instructorTitle" autocomplete="off"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelModify">取 消</el-button>
                <el-button type="primary" @click="saveModify">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
import teacher from "@/views/Manager/Teacher.vue";

export default {
    name: "Teacher",
    computed: {
        teacher() {
            return teacher
        }
    },

    data() {
        return {
            api:this.GLOBAL.BASE_URL+"/user/batch?usertype=Instructor",
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
            teacher_id: "",
            teacher_name: "",
            teacher_title: "",
            dialogFormVisible: false,
            ModifyDialogFormVisible: false,
            multipleSelection: [],

            headerBg: 'headerBg',
            resetPassword:{
                newPassword:"123456",
                userID:0
            },
            instructorID:"",
            addUser:{
                roles: [
                    "ROLE_INSTRUCTOR"
                ],
                roleSpecificInfo: {
                    instructorID: 0,
                    name:"",
                    title:""
                },
                password: "123456",
                loginID: ""
            },
            modifyUser:{
                roles: [
                    "ROLE_TECHNICIAN"
                ],
                roleSpecificInfo: {
                    name:"",
                    technicianTitle:"",
                    instructorTitle:"",
                    major:"",
                    clazz:"",
                },
                loginID: "",
                userID:"",
            },
        }
    },
    created() {
        this.load()
    },
    // 绑定监听事件
    mounted() {
        window.addEventListener('keydown', this.keyDown)
    },
    // 销毁事件
    destroyed() {
        window.removeEventListener('keydown', this.keyDown, false)
    },
    methods: {
        load() {
            this.request.get("/user/instructor/all").then(res => {
                console.log(res)
                this.tableData = res.data
            })
        },
        resetDialog() {
            this.addUser.roleSpecificInfo.name = ""
            this.addUser.roleSpecificInfo.title = ""
            this.addUser.password = ""
        },
        cancelAdd() {
            this.dialogFormVisible = false;
            this.resetDialog()
        },
        cancelModify() {
            this.ModifyDialogFormVisible = false;
            this.resetModifyDialog()
        },
        resetModifyDialog(){
            this.modifyUser.roleSpecificInfo.name = ""
            this.modifyUser.roleSpecificInfo.title = ""
            this.modifyUser.password = ""
        },
        save() {
            this.request.post("/user/instructor", this.addUser).then(res => {
               this.$message.success("添加成功")
               this.dialogFormVisible = false
               this.resetDialog()
               this.load()
            }).catch(res => {
                this.$message.error(res.response.data.message)
            })
        },
        saveModify(){
            console.log(this.modifyUser)
            this.request.put("/user/instructor",this.modifyUser).then(res =>{
                if (res) {
                    this.$message.success("修改成功")
                    this.ModifyDialogFormVisible = false
                    this.resetDialog()
                    this.load()
                } else {
                    this.$message.error("修改失败")
                }
            }).catch(error=>{
                this.$message.error("修改失败")
            })
        },
        handleAdd() {
            this.dialogFormVisible = true;
            this.resetDialog()
        },
        del(id) {
            console.log(id)
            this.$confirm('此操作将永久删除该条记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.delete("/user?userID="+id).then(res => {
                    console.log(res)
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
        input(){
            console.log(this.value)
            this.request.get("/user/instructor/name",{
                params:{
                    name:this.value
                }
            }).then(res =>{
                this.tableData = res.data
            })
        },
        reset(id) {
            this.resetPassword.userID = id
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
                console.log("sss")
                console.log(this.fileList)
            }
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
        },
        handleUpdate(instructorID){
            this.request.get("/user",{
                params:{
                    userID: instructorID
                }
            }).then(res=>{
                console.log(res)
                this.modifyUser.roleSpecificInfo.name = res.data.roleSpecificInfo.name
                this.modifyUser.loginID = res.data.loginID
                this.modifyUser.roleSpecificInfo.instructorTitle = res.data.roleSpecificInfo.instructorTitle
                this.modifyUser.userID = instructorID
                this.modifyUser.roles = res.data.role
            })
            this.ModifyDialogFormVisible = true;
            this.instructorID = instructorID
        },
    }
}
</script>

<style scoped>

</style>