<template>
    <div class="wrapper">
        <div style="margin-top: 50px; text-align: center">
            <img src="../assets/LoginLogo.png">
        </div>
        <div style="color: white;text-align: center;margin-bottom: 15px; font-family:  'Hiragino Sans GB'">
            <h1>计算机系实验室管理平台</h1>
        </div>
        <div style="margin: 0px auto; background-color: rgba(225,255,225,0.76); width: 350px; height: 300px; padding: 20px; border-radius: 10px">
            <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>登 录</b></div>
            <el-form ref="userForm" :model="user" :rules="rules">
                <el-form-item prop="loginID">
                    <el-input v-model="user.loginID" prefix-icon="el-icon-user" size="medium" placeholder="请输入您的登录ID"
                              style="margin: 10px 0"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="user.password" prefix-icon="el-icon-lock" show-password size="medium" placeholder="请输入您的密码"
                              style="margin: 10px 0"></el-input>
                </el-form-item>
                <el-form-item style="margin: 10px 0; text-align: right;">
                    <el-link :underline="false" href="/TimeTable/home" style="margin-right: 60px">游客模式查看课程表
                    </el-link>
                    <el-button autocomplete="off" size="small" type="primary" @click="login">登录</el-button>
<!--                    <el-button autocomplete="off" size="small" type="warning" @click="$router.push('/register')">注册-->
<!--                    </el-button>-->
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
                loginID: [
                    {required: true, message: '请输入登录ID', trigger: 'blur'},
                    // {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: '请输入密码', trigger: 'blur'},
                    // {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
                ],
            },
        }
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
        login() {
            this.$refs['userForm'].validate((valid) => {
                // if (valid) {  // 表单校验合法
                    console.log(this.user)
                    this.request.post("/auth/login", this.user).then(res => {
                        if (res.role[0] === "ROLE_STUDENT") {
                            this.$router.push("/Student/home")
                        } else{
                            this.$router.push("/Management/home")
                        }
                        localStorage.setItem("user", JSON.stringify(res))  // 存储用户信息到浏览器
                        this.$message.success("登录成功")
                    }).catch(error => {
                        console.log(error)
                        this.$message.error("用户名或密码错误")
                    })
                // }
            });
        },
        keyDown(e) {
            // 回车则执行登录方法 enter键的ASCII是13
            if (e.keyCode === 13) {
                this.login() // 需要执行的方法方法
            }
        }
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