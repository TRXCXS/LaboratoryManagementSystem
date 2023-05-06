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
        <el-menu-item index="/Management/home">
            <template slot="title">
                <i class="el-icon-s-home"></i>
                <span slot="title">主页</span>
            </template>
        </el-menu-item>


        <el-submenu index="2" :disabled="ManagerPortVisible" >
            <template slot="title">
                <i class="el-icon-user-solid"></i>
                <span slot="title">管理员端</span>
            </template>
            <el-menu-item index="/Management/semester">
                <template slot="title">
                    <i class="el-icon-time"></i>
                    <span slot="title">学期管理</span>
                </template>
            </el-menu-item>
            <el-submenu index="4">
                <template slot="title">
                    <i class="el-icon-user"></i>
                    <span slot="title">用户管理</span>
                </template>
                <el-menu-item index="/Management/tester" >
                    <i class="el-icon-s-custom"></i>
                    <span slot="title">实验员管理</span>
                </el-menu-item>
                <el-menu-item index="/Management/teacher">
                    <i class="el-icon-s-custom"></i>
                    <span slot="title">教师管理</span>
                </el-menu-item>
                <el-menu-item index="/Management/student">
                    <i class="el-icon-s-custom"></i>
                    <span slot="title">学生管理</span>
                </el-menu-item>
            </el-submenu>
            <el-menu-item index="/Management/classScheduling">
                <template slot="title">
                    <i class="el-icon-date"></i>
                    <span slot="title">实验排课</span>
                </template>
            </el-menu-item>
            <el-menu-item index="/Management/scrutinize">
                <template slot="title">
                    <i class="el-icon-chat-line-round"></i>
                    <span slot="title">实验室借用审批</span>
                </template>
            </el-menu-item>
        </el-submenu>


        <el-submenu index="5" :disabled="TeacherPortVisible">
            <template slot="title">
                <i class="el-icon-user-solid"></i>
                <span slot="title">教师端</span>
            </template>
            <el-menu-item index="/Management/Application">
                <template slot="title">
                    <i class="el-icon-location-outline"></i>
                    <span slot="title">申请登记</span>
                </template>
            </el-menu-item>
            <el-menu-item index="/Management/Warranty">
                <template slot="title">
                    <i class="el-icon-message-solid"></i>
                    <span slot="title">设备报修</span>
                </template>
            </el-menu-item>
        </el-submenu>

        <el-submenu index="6" :disabled="TesterPortVisible" >
            <template slot="title">
                <i class="el-icon-user-solid"></i>
                <span slot="title">实验员端</span>
            </template>
            <el-menu-item index="/Management/maintenance">
                <template slot="title">
                    <i class="el-icon-attract"></i>
                    <span slot="title">设备维修</span>
                </template>
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
            ManagerPortVisible:false,
            TeacherPortVisible:false,
            TesterPortVisible:false,
        }
    },
    created() {
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