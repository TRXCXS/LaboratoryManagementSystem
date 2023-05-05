<template>
    <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden"
             background-color="rgb(38, 91, 73)"
             text-color="#fff"
             active-text-color="rgb(238, 207, 2)"
             :collapse-transition="false"
             :collapse="isCollapse"
             router
    >
        <div style="height: 60px; line-height: 60px; text-align: center">
            <img src="../../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; right: 5px">
            <b style="color: white" v-show="logoTextShow">计算机系实验室管理平台</b>
        </div>
        <el-menu-item index="/Manager/home">
            <template slot="title">
                <i class="el-icon-house"></i>
                <span slot="title">主页</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/Manager/semester">
            <template slot="title">
                <i class="el-icon-time"></i>
                <span slot="title">学期管理</span>
            </template>
        </el-menu-item>
        <el-submenu index="3">
            <template slot="title">
                <i class="el-icon-user"></i>
                <span slot="title">用户管理</span>
            </template>
            <el-menu-item index="/Manager/tester" :disabled="dialogFormVisible" >
                <i class="el-icon-s-custom"></i>
                <span slot="title">实验员管理</span>
            </el-menu-item>
            <el-menu-item index="/Manager/teacher">
                <i class="el-icon-s-custom"></i>
                <span slot="title">教师管理</span>
            </el-menu-item>
            <el-menu-item index="/Manager/student">
                <i class="el-icon-s-custom"></i>
                <span slot="title">学生管理</span>
            </el-menu-item>
        </el-submenu>
        <el-menu-item index="/Manager/classScheduling">
            <template slot="title">
                <i class="el-icon-date"></i>
                <span slot="title">实验排课</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/Manager/scrutinize">
            <template slot="title">
                <i class="el-icon-chat-line-round"></i>
                <span slot="title">实验室借用审批</span>
            </template>
        </el-menu-item>
    </el-menu>
</template>

<script>
export default {
    name: "Aside",
    data(){
        return{
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            dialogFormVisible:false,
        }
    },
    created() {
        // console.log(this.user.role)
        // console.log(this.user.role === "ROLE_SUPER_ADMIN")
        if (this.user.role === "ROLE_ADMIN"){
            this.dialogFormVisible = true
        }
    },
    props: {
        isCollapse: Boolean,
        logoTextShow: Boolean
    }
}
</script>

<style scoped>

</style>