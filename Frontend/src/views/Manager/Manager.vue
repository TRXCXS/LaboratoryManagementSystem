<template>
    <div>
        <el-card style="width: 500px">
            <h2 style="text-align: center;margin-bottom: 10px">创建用户</h2>
                <el-form ref="form" :model="form" label-width="80px">
                    <el-form-item label="用户角色">
                        <el-checkbox-group v-model="form.type" @change="change">
                            <el-checkbox label="实验员" name="ROLE_TECHNICIAN" :disabled="checkboxTester" v-model="testerChecked"></el-checkbox>
                            <el-checkbox label="教师" name="ROLE_INSTRUCTOR" :disabled="checkboxTeacher" v-model="teacherChecked"></el-checkbox>
                            <el-checkbox label="管理员" name="ROLE_ADMIN" :disabled="checkboxAdmin" v-model="adminChecked"></el-checkbox>
                            <el-checkbox label="学生" name="ROLE_STUDENT" :disabled="checkboxStudent" v-model="studentChecked"></el-checkbox>
                        </el-checkbox-group>
                    </el-form-item>
                    <el-form-item label="用户名称">
                        <el-input v-model="addUser.roleSpecificInfo.name"></el-input>
                    </el-form-item>
                    <el-form-item label="登录ID">
                        <el-input v-model="addUser.loginID"></el-input>
                    </el-form-item>
                    <el-form-item label="用户职称">
                        <el-input v-model="addUser.roleSpecificInfo.title" :disabled="titleDisabled"></el-input>
                    </el-form-item>
                    <el-form-item label="学生专业">
                        <el-input v-model="addUser.roleSpecificInfo.major" :disabled="majorDisabled"></el-input>
                    </el-form-item>
                    <el-form-item label="学生班级">
                        <el-input v-model="addUser.roleSpecificInfo.clazz" :disabled="classDisabled"></el-input>
                    </el-form-item>
                    <el-form-item style="text-align: center;margin-left: -90px">
                        <el-button type="primary" @click="onSubmit">立即创建</el-button>
                    </el-form-item>
                </el-form>
        </el-card>
        <div style="margin: 10px 0; width: 175px" >
            <el-upload
                    class="upload-demo"
                    ref="upload"
                    accept=".xls,.xlsx"
                    :action="api"
                    :on-preview="handlePreview"
                    :on-remove="handleRemove"
                    :on-change="handleChange"
                    :on-error = "handleError"
                    :on-success = "handleSuccess"
                    :file-list="fileList"
                    :headers="headers"
                    :limit="1"
                    name="table"
                    :auto-upload="false">
                <el-button slot="trigger" size="small" type="primary">批量导入</el-button>
                <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">确定上传</el-button>
            </el-upload>

        </div>
    </div>
</template>

<script>

export default {
    name: "Manager",
    data() {
        return {
            testerChecked:false,
            studentChecked:false,
            teacherChecked:false,
            adminChecked:false,

            majorDisabled:true,
            classDisabled:true,
            titleDisabled:true,
            checkboxTester:false,
            checkboxTeacher:false,
            checkboxStudent:false,
            checkboxAdmin:false,
            api:this.GLOBAL.BASE_URL+"/user/batch?usertype=Administrator",
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

            formLabelWidth: '80px',
            dialogFormVisible: false,
            multipleSelection: [],

            addUser:{
                roles: [

                ],
                roleSpecificInfo: {
                    // name:"",
                    // title:""
                },
                password: "123456",
                loginID: ""
            },

            userType:"Administrator",

            form: {
                type: [],
            }

        }
    },
    methods: {
        change(){
            console.log(this.form.type)
            // console.log(this.studentChecked)
            // console.log(this.testerChecked)
            // console.log(this.teacherChecked)
            // console.log(this.adminChecked)
            // if (this.studentChecked === true){
            //     this.checkboxTester = false
            //     this.checkboxAdmin = false
            //     this.checkboxTeacher = false
            // }else if (this.testerChecked === true || this.teacherChecked === true || this.adminChecked ===true){
            //     this.checkboxStudent = false
            // }
            if (this.form.type.length===1 && this.form.type[0] === "学生"){
                this.majorDisabled = false
                this.classDisabled = false
                this.titleDisabled = true
            }else if (this.form.type.length===1 && this.form.type[0] !== "学生"){
                this.majorDisabled = true
                this.classDisabled = true
                this.titleDisabled = false
            }
        },
        onSubmit(){
            console.log(this.form.type)
            console.log(this.form.type.length)
            for (let i = 0; i <this.form.type.length; i++) {
                if (this.form.type[i] === "实验员") {
                    this.form.type[i] = "ROLE_TECHNICIAN"
                }else if (this.form.type[i] === "管理员"){
                    this.form.type[i] = "ROLE_ADMIN"
                }else if (this.form.type[i] === "学生"){
                    this.form.type[i] = "ROLE_STUDENT"
                }else if (this.form.type[i] === "教师"){
                    this.form.type[i] = "ROLE_INSTRUCTOR"
                }
            }
            this.addUser.roles = this.form.type
            console.log(this.addUser)
            this.request.post("/user",this.addUser).then(res => {
                this.$message({
                    type: 'success',
                    message: '创建成功！'
                });
            }).catch(() => {
                this.$message({
                    type: 'error',
                    message: '创建用户失败，角色冲突或登录ID已存在！'
                });
            });
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
            console.log("dddeed")
        },
        handleRemove(file,fileList){
            console.log(file, fileList);
            console.log("ddd")
            this.fileList.remove
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
    }
}
</script>

<style scoped>

</style>