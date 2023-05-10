<template>
    <div class="wrapper">
        <div style="margin: 100px auto; background-color: rgba(225,255,225,0.76); width: 350px; height: 400px; padding: 20px; border-radius: 10px">
            <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>注 册</b></div>
            <el-form ref="userForm" :model="user" :rules="rules">
                <el-form-item prop="username">
                    <el-input v-model="user.username" placeholder="请输入账号" prefix-icon="el-icon-user" size="medium"
                              style="margin: 5px 0"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="user.password" placeholder="请输入密码" prefix-icon="el-icon-lock" show-password
                              size="medium" style="margin: 5px 0"></el-input>
                </el-form-item>
                <el-form-item prop="confirmPassword">
                    <el-input v-model="user.confirmPassword" placeholder="请确认密码" prefix-icon="el-icon-lock"
                              show-password
                              size="medium" style="margin: 5px 0"></el-input>
                </el-form-item>
                <el-form-item style="margin: 5px 0; text-align: center">
                    <el-button autocomplete="off" size="small" type="primary" @click="reg">注册</el-button>
                    <el-button autocomplete="off" size="small" type="warning" @click="$router.push('/login')">返回登录
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    name: "Register",
    data() {
        return {
            user: {},
            rules: {
                username: [
                    {required: true, message: '请输入用户名', trigger: 'blur'},
                    {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: '请输入密码', trigger: 'blur'},
                    {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
                ],
                confirmPassword: [
                    {required: true, message: '请输入密码', trigger: 'blur'},
                    {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
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
        reg() {
            this.$refs['userForm'].validate((valid) => {
                if (valid) {  // 表单校验合法
                    if (this.user.password !== this.user.confirmPassword) {
                        this.$message.error("两次输入的密码不一致")
                        return false
                    }
                    this.request.post("/auth/register", this.user).then(res => {
                        console.log(res)
                        this.flag = 1
                        this.$message.success("注册成功")
                        this.$router.push('/login')
                    }).catch(error => {
                        console.log(error)
                        this.$message.error("用户名已存在")
                    })
                }
            });
        },
        keyDown(e) {
            // 回车则执行登录方法 enter键的ASCII是13
            if (e.keyCode === 13) {
                this.reg() // 需要执行的方法方法
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