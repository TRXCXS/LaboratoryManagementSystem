<template>
    <el-menu :collapse="isCollapse" :collapse-transition="false"
             :default-openeds="['1', '3']"
             active-text-color="rgb(238, 207, 2)"
             background-color="rgb(38, 91, 73)"
             router
             style="min-height: 100%; overflow-x: hidden"
             text-color="#fff"
    >
        <div style="height: 60px; line-height: 60px; text-align: center">
            <img alt="" src="../../assets/logo.png" style="width: 20px; position: relative; top: 5px; right: 5px">
            <b v-show="logoTextShow" style="color: white">计算机实验室管理平台</b>
        </div>
        <el-menu-item index="/Management/home">
            <template slot="title">
                <i class="el-icon-s-home"></i>
                <span slot="title">主页</span>
            </template>
        </el-menu-item>


        <el-submenu :disabled="ManagerPortVisible" index="2" >
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
                <el-menu-item index="/Management/manager">
                    <i class="el-icon-s-custom"></i>
                    <span slot="title">管理员管理</span>
                </el-menu-item>
                <el-menu-item index="/Management/tester">
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
            <el-menu-item index="/Management/allLab">
                <template slot="title">
                    <i class="el-icon-monitor"></i>
                    <span slot="title">查看所有实验室</span>
                </template>
            </el-menu-item>
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


        <el-submenu :disabled="TeacherPortVisible" index="5">
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

        <el-submenu :disabled="TesterPortVisible" index="6">
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
    data() {
        return {
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            ManagerPortVisible: false,
            TeacherPortVisible: false,
            TesterPortVisible: false,
        }
    },
    created() {
        console.log(this.user)
        if (this.user.role.length === 3) {
            this.ManagerPortVisible = false
            this.TeacherPortVisible = false
            this.TesterPortVisible = false
        }else if (this.user.role.length === 1){
            if (this.user.role[0] === "ROLE_ADMIN"){
                this.ManagerPortVisible = false
                this.TeacherPortVisible = true
                this.TesterPortVisible = true
            }else if (this.user.role[0] === "ROLE_INSTRUCTOR"){
                this.ManagerPortVisible = true
                this.TeacherPortVisible = false
                this.TesterPortVisible = true
            }else if (this.user.role[0] === "ROLE_TECHNICIAN"){
                this.ManagerPortVisible = true
                this.TeacherPortVisible = true
                this.TesterPortVisible = false
            }
        }else if (this.user.role.length === 2){
            console.log("sss???")
            // console.log(this.user.role)
            if (this.user.role[0] === "ROLE_ADMIN" && this.user.role[1] === "ROLE_INSTRUCTOR" || this.user.role[1] === "ROLE_ADMIN" && this.user.role[0] === "ROLE_INSTRUCTOR"){
                this.ManagerPortVisible = false
                this.TeacherPortVisible = false
                this.TesterPortVisible = true
            }else if (this.user.role[0] === "ROLE_ADMIN" && this.user.role[1] === "ROLE_TECHNICIAN" || this.user.role[1] === "ROLE_ADMIN" && this.user.role[0] === "ROLE_TECHNICIAN"){
                this.ManagerPortVisible = false
                this.TeacherPortVisible = true
                this.TesterPortVisible = false
            }else if (this.user.role[0] === "ROLE_TECHNICIAN" && this.user.role[1] === "ROLE_INSTRUCTOR" || this.user.role[1] === "ROLE_TECHNICIAN" && this.user.role[0] === "ROLE_INSTRUCTOR"){
                this.ManagerPortVisible = true
                this.TeacherPortVisible = false
                this.TesterPortVisible = false
            }
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