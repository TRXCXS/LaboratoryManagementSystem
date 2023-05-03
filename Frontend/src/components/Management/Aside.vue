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
            <b style="color: white" v-show="logoTextShow">图书馆座位管理系统</b>
        </div>
        <el-menu-item index="/Management/home">
            <template slot="title">
                <i class="el-icon-house"></i>
                <span slot="title">主页</span>
            </template>
        </el-menu-item>
        <el-submenu index="2">
            <template slot="title">
                <i class="el-icon-menu"></i>
                <span slot="title">系统管理</span>
            </template>
            <el-menu-item index="/Management/section"  :disabled="dialogFormVisible">
                <i class="el-icon-location"></i>
                <span slot="title">区域管理</span>
            </el-menu-item>

            <el-submenu index="3">
                <template slot="title">
                    <i class="el-icon-user"></i>
                    <span slot="title">人员管理</span>
                </template>
<!--                <el-menu-item index="/Management/manager" disabled>-->
<!--                    <i class="el-icon-s-custom"></i>-->
<!--                    <span slot="title">管理员管理</span>-->
<!--                </el-menu-item>-->
                <el-menu-item index="/Management/manager" :disabled="dialogFormVisible" >
                    <i class="el-icon-s-custom"></i>
                    <span slot="title">管理员管理</span>
                </el-menu-item>
                <el-menu-item index="/Management/user">
                    <i class="el-icon-s-custom"></i>
                    <span slot="title">用户管理</span>
                </el-menu-item>
            </el-submenu>

            <el-menu-item index="/Management/credit">
                <i class="el-icon-trophy"></i>
                <span slot="title">信用管理</span>
            </el-menu-item>
            <el-menu-item index="/Management/reservation">
                <i class="el-icon-document"></i>
                <span slot="title">预约管理</span>
            </el-menu-item>
        </el-submenu>
        <el-submenu index="4">
            <template slot="title">
                <i class="el-icon-bell"></i>
                <span slot="title">请求处理</span>
            </template>
            <el-menu-item index="/Management/report">
                <i class="el-icon-warning"></i>
                <span slot="title">举报处理</span>
            </el-menu-item>
            <el-menu-item index="/Management/appeal">
                <i class="el-icon-question"></i>
                <span slot="title">申诉处理</span>
            </el-menu-item>
        </el-submenu>
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