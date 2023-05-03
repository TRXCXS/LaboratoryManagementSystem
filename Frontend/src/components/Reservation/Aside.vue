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
            <b style="color: white" v-show="logoTextShow">图书馆座位预约系统</b>
        </div>
        <el-menu-item index="/Reservation/home">
            <template slot="title">
                <i class="el-icon-house"></i>
                <span slot="title">主页</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/Reservation/SelectSection">
            <template slot="title">
                <i class="el-icon-location-outline"></i>
                <span slot="title">选择座位</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/Reservation/MyAppointment">
            <template slot="title">
                <i class="el-icon-message-solid"></i>
                <span slot="title">我的预约</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/Reservation/MyAppeal">
            <template slot="title">
                <i class="el-icon-chat-line-round"></i>
                <span slot="title">我的申诉</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/Reservation/MyReport">
            <template slot="title">
                <i class="el-icon-warning-outline"></i>
                <span slot="title">我的举报</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/Reservation/CreditRecord">
            <template slot="title">
                <i class="el-icon-trophy"></i>
                <span slot="title">信用记录</span>
            </template>
        </el-menu-item>

<!--        <el-submenu index="2">-->
<!--            <template slot="title">-->
<!--                <i class="el-icon-menu"></i>-->
<!--                <span slot="title">系统管理</span>-->
<!--            </template>-->
<!--            <el-menu-item index="/Reservation/section">-->
<!--                <i class="el-icon-location"></i>-->
<!--                <span slot="title">区域管理</span>-->
<!--            </el-menu-item>-->
<!--            <el-menu-item index="/Reservation/manager">-->
<!--                <i class="el-icon-s-custom"></i>-->
<!--                <span slot="title">人员管理</span>-->
<!--            </el-menu-item>-->
<!--            <el-menu-item index="/Reservation/credit">-->
<!--                <i class="el-icon-trophy"></i>-->
<!--                <span slot="title">信用管理</span>-->
<!--            </el-menu-item>-->
<!--            <el-menu-item index="/Reservation/reservation">-->
<!--                <i class="el-icon-document"></i>-->
<!--                <span slot="title">预约管理</span>-->
<!--            </el-menu-item>-->
<!--        </el-submenu>-->
    </el-menu>
</template>

<script>
export default {
    name: "Aside",
    props: {
        isCollapse: Boolean,
        logoTextShow: Boolean
    }
}
</script>

<style scoped>

</style>