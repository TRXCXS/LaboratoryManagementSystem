<template>
    <div class="wrapper">
        <div style="margin-top: 100px; text-align: center">
            <img src="../assets/LoginLogo.png">
        </div>
        <div style="margin: 0px auto; background-color: rgba(225,255,225,0.76); width: 350px; height: 300px; padding: 20px; border-radius: 10px">
            <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>登 录</b></div>
            <el-form :model="user" :rules="rules" ref="userForm">
                <el-form-item prop="username">
                    <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
                </el-form-item>
                <el-form-item style="margin: 10px 0; text-align: right">
                    <el-button type="primary" size="small"  autocomplete="off" @click="login">登录</el-button>
                    <el-button type="warning" size="small"  autocomplete="off" @click="$router.push('/register')">注册</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    name: "Login",
    data() {
        return {
            url: "https://www.scau.edu.cn/_upload/tpl/00/68/104/template104/images/logo.png",
            user: {},
            rules: {
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
                ],
            },

            flag:"0"
        }
    },
    methods: {
         login() {
            this.$refs['userForm'].validate((valid) => {
                if (valid) {  // 表单校验合法
                    this.request.post("/auth/login", this.user).then(res => {
                        if (res.role === "ROLE_USER"){
                            this.$router.push("/Reservation/home")
                        }else if (res.role === "ROLE_ADMIN" || res.role === "ROLE_SUPER_ADMIN"){
                            this.$router.push("/Management/home")
                        }else if(res.role === "ROLE_SCANNER"){
                            this.$router.push("/chooseScanner")
                        }
                        localStorage.setItem("user", JSON.stringify(res))  // 存储用户信息到浏览器
                        this.$message.success("登录成功")
                        // this.flag ="1"
                    }).catch(error=>{
                        console.log(error)
                        this.$message.error("用户名或密码错误")
                    })
                }
            });
        },
    }
}
</script>

<style>
.wrapper {
    height: 100vh;
    background-image: linear-gradient(to bottom right, #208c28, #0e4f93);
    overflow: hidden;
}
</style>