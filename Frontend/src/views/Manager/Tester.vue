<template>
    <div>
        <el-card>
            <div style="display: flex; margin: 10px 0">
                <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
                <div style="display: flex;margin-left: 280px">
                    <h5 style="margin-top: 6px; margin-right: 10px">实验员检索:</h5>
                    <el-input placeholder="请输入实验员姓名" style="width: 200px" suffix-icon="el-icon-search"  @input="input" v-model="value"></el-input>
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
                <el-button slot="trigger" size="small" type="primary">批量导入实验员</el-button>
                <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">确定上传</el-button>
            </el-upload>

<!--            <el-upload-->
<!--                class="upload-demo"-->
<!--                ref="upload"-->
<!--                action="https://jsonplaceholder.typicode.com/posts/"-->
<!--                :on-preview="handlePreview"-->
<!--                :on-remove="handleRemove"-->
<!--                :file-list="fileList"-->
<!--                :auto-upload="false">-->
<!--                <el-button slot="trigger" size="small" type="primary">选取文件</el-button>-->
<!--                <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>-->
<!--                <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>-->
<!--            </el-upload>-->


        </div>

        <el-table :data="tableData" :header-cell-class-name="headerBg" border stripe>
            <el-table-column label="实验员ID" prop="technicianID">
            </el-table-column>
            <el-table-column label="实验员姓名" prop="name">
            </el-table-column>
            <el-table-column label="职称" prop="title">
            </el-table-column>
            <el-table-column align="center" label="操作">
                <template slot-scope="scope">
                    <el-button style="margin-right: 5px" type="primary" @click="handleUpdate(scope.row.technicianID)">修改 <i
                            class="el-icon-edit"></i></el-button>
                    <el-popconfirm
                            title="是否确定重置密码？密码将被重置为“123456”"
                            @confirm="reset(scope.row.technicianID)"
                    >
                        <el-button slot="reference" type="warning">重置密码 <i class="el-icon-edit"></i></el-button>
                    </el-popconfirm>
                    <el-button style="margin-left: 5px" type="danger" @click="del(scope.row.technicianID)">删除 <i
                            class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :visible.sync="dialogFormVisible" title="实验员信息" width="30%">
            <el-form :label-width="formLabelWidth">
<!--                <el-form-item label="实验员ID">-->
<!--                    <el-input v-model.number="addUser.roleSpecificInfo.technicianID" autocomplete="off"></el-input>-->
<!--                </el-form-item>-->
                <el-form-item label="姓名">
                    <el-input v-model="addUser.roleSpecificInfo.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="登录ID">
                    <el-input v-model.number="addUser.loginID" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model.number="addUser.password" autocomplete="off"></el-input>
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

        <el-dialog :visible.sync="ModifyDialogFormVisible" title="修改实验员信息" width="30%">
            <el-form :label-width="formLabelWidth">
                <!--                <el-form-item label="实验员ID">-->
                <!--                    <el-input v-model.number="addUser.roleSpecificInfo.technicianID" autocomplete="off"></el-input>-->
                <!--                </el-form-item>-->
                <el-form-item label="姓名">
                    <el-input v-model="modifyUser.roleSpecificInfo.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="登录ID">
                    <el-input v-model.number="modifyUser.loginID" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="职称">
                    <el-input v-model="modifyUser.roleSpecificInfo.technicianTitle" autocomplete="off"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelModify">取 消</el-button>
                <el-button type="primary" @click="saveModify">确 定</el-button>
            </div>
        </el-dialog>

        <!--        <div style="padding: 10px 0">-->
        <!--              <el-pagination-->
        <!--                      :page-sizes="[5, 10, 15, 20]"-->
        <!--                      :page-size="10"-->
        <!--                      layout="total, sizes, prev, pager, next, jumper"-->
        <!--                      :total="400">-->
        <!--              </el-pagination>-->
        <!--        </div>-->
    </div>
</template>

<script>
export default {
    name: "Tester",
    data() {
        return {
            api:this.GLOBAL.BASE_URL+"/user/batch?usertype=Technician",
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
            headerBg: 'headerBg',

            technicianID:"",

            formLabelWidth: '80px',
            tester_id: "",
            tester_name: "",
            tester_title: "",
            dialogFormVisible: false,
            ModifyDialogFormVisible: false,
            multipleSelection: [],
            resetPassword:{
                newPassword:"123456",
                userID:0
            },

            addUser:{
                roles: [
                    "ROLE_TECHNICIAN"
                ],
                roleSpecificInfo: {
                    technicianID: 0,
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

            userType:"Administrator",

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
            this.request.get("/user/technician/all").then(res => {
                console.log(res)
                this.tableData = res.data
            })
        },
        resetDialog() {
            this.addUser.roleSpecificInfo.name = ""
            this.addUser.roleSpecificInfo.title = ""
            this.addUser.password = ""
        },
        resetModifyDialog(){
            this.modifyUser.roleSpecificInfo.name = ""
            this.modifyUser.roleSpecificInfo.title = ""
            this.modifyUser.password = ""
        },
        saveModify(){
            console.log(this.modifyUser)
            // this.modifyUser.roleSpecificInfo.technicianID = this.technicianID
            this.request.put("/user/technician",this.modifyUser).then(res =>{
                if (res) {
                    this.$message.success("修改成功")
                    this.dialogFormVisible = false
                    this.resetDialog()
                    this.load()
                } else {
                    this.$message.error("修改失败")
                }
            }).catch(error=>{
                this.$message.error("修改失败")
            })
            this.ModifyDialogFormVisible = false
        },
        save() {
            console.log(this.addUser)
            this.request.post("/user/technician",this.addUser).then(res => {
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
        cancelAdd() {
            this.dialogFormVisible = false;
            this.resetDialog()
        },
        cancelModify() {
            this.ModifyDialogFormVisible = false;
            this.resetModifyDialog()
        },
        del(id) {
            console.log(id)
            this.$confirm('此操作将永久删除该条记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.delete("/user?userID=" + id).then(res => {
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
        handleAdd() {
            this.dialogFormVisible = true;
            this.resetDialog()
        },
        reset(id) {
            this.resetPassword.userID = id
            // console.log(this.resetPassword)
            this.request.put("/user/password?",this.resetPassword).then(res=>{
                console.log(res)
                this.$message.success("已重置")
            })
        },
        input(){
            console.log(this.value)
            this.request.get("/user/technician/name",{
                params:{
                    name:this.value
                }
            }).then(res =>{
                this.tableData = res.data
            })
        },
        handlePreview(file){
            console.log(file);
            console.log("Sssss")
        },
        handleRemove(file,fileList){
            console.log(file, fileList);
            console.log("ddd")
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
        handleUpdate(technicianID){
            this.request.get("/user",{
                params:{
                    userID: technicianID
                }
            }).then(res=>{
                console.log(res)
                this.modifyUser.roleSpecificInfo.name = res.data.roleSpecificInfo.name
                this.modifyUser.loginID = res.data.loginID
                this.modifyUser.roleSpecificInfo.technicianTitle = res.data.roleSpecificInfo.technicianTitle
                this.modifyUser.userID = technicianID
                this.modifyUser.roles = res.data.role
            })
            this.ModifyDialogFormVisible = true;
            this.technicianID = technicianID
        },
    }
}
</script>

<style scoped>

</style>