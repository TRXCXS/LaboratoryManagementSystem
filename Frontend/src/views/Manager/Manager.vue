<template>
    <div>
        <el-card style="width: 500px">
            <h2 style="text-align: center;margin-bottom: 10px">创建用户</h2>
                <el-form ref="form" :model="form" label-width="80px">
                    <el-form-item label="用户名称">
                        <el-input v-model="addUser.roleSpecificInfo.name"></el-input>
                    </el-form-item>
                    <el-form-item label="用户职称">
                        <el-input v-model="addUser.roleSpecificInfo.title"></el-input>
                    </el-form-item>
                    <el-form-item label="用户登录ID">
                        <el-input v-model="addUser.loginID"></el-input>
                    </el-form-item>
                  <el-form-item label="用户角色">
                      <el-checkbox-group v-model="form.type">
                          <el-checkbox label="实验员" name="ROLE_TECHNICIAN"></el-checkbox>
                          <el-checkbox label="教师" name="ROLE_INSTRUCTOR"></el-checkbox>
                          <el-checkbox label="管理员" name="ROLE_ADMIN"></el-checkbox>
                          <el-checkbox label="学生" name="ROLE_STUDENT"></el-checkbox>
                      </el-checkbox-group>
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
                    accept=".xls,.xlsx,.csv"
                    :action="api"
                    :on-preview="handlePreview"
                    :on-remove="handleRemove"
                    :on-change="handleChange"
                    :file-list="fileList"
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
            api:this.GLOBAL.BASE_URL+"/user/batch?usertype=Administrator",
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
                    name:"",
                    title:""
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
        submitUpload(){
            if (this.fileList.length === 0) {
                return this.$message.warning("请选取文件后再上传");
            }
            this.$refs.upload.submit();
            this.load()
            this.$message.success("导入成功！")
        },
    }
}
</script>

<style scoped>

</style>