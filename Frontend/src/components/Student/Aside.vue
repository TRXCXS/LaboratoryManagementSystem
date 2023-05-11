<template>
    <el-menu :collapse="isCollapse" :collapse-transition="false"
             :default-openeds="['1', '3']"
             active-text-color="rgb(238, 207, 2)"
             background-color="rgb(38, 91, 73)"
             customClass="customClass"
             router
             style="min-height: 100%; overflow-x: hidden"
             text-color="#fff"
    >
        <div style="height: 60px; line-height: 60px; text-align: center">
            <img alt="" src="../../assets/logo.png" style="width: 20px; position: relative; top: 5px; right: 5px">
            <b v-show="logoTextShow" style="color: white">计算机实验室管理平台</b>
        </div>
        <!--        <el-menu-item index="/Reservation/home">-->
        <!--            <template slot="title">-->
        <!--                <i class="el-icon-house"></i>-->
        <!--                <span slot="title">主页</span>-->
        <!--            </template>-->
        <!--        </el-menu-item>-->
        <!--        <el-menu-item index="/Reservation/SelectSection">-->
        <!--            <template slot="title">-->
        <!--                <i class="el-icon-location-outline"></i>-->
        <!--                <span slot="title">选择座位</span>-->
        <!--            </template>-->
        <!--        </el-menu-item>-->
        <!--        <el-menu-item index="/Reservation/MyAppointment">-->
        <!--            <template slot="title">-->
        <!--                <i class="el-icon-message-solid"></i>-->
        <!--                <span slot="title">我的预约</span>-->
        <!--            </template>-->
        <!--        </el-menu-item>-->
        <!--        <el-menu-item index="/Reservation/MyAppeal">-->
        <!--            <template slot="title">-->
        <!--                <i class="el-icon-chat-line-round"></i>-->
        <!--                <span slot="title">我的申诉</span>-->
        <!--            </template>-->
        <!--        </el-menu-item>-->
        <!--        <el-menu-item index="/Reservation/MyReport">-->
        <!--            <template slot="title">-->
        <!--                <i class="el-icon-warning-outline"></i>-->
        <!--                <span slot="title">我的举报</span>-->
        <!--            </template>-->
        <!--        </el-menu-item>-->
        <!--        <el-menu-item index="/Reservation/CreditRecord">-->
        <!--            <template slot="title">-->
        <!--                <i class="el-icon-trophy"></i>-->
        <!--                <span slot="title">信用记录</span>-->
        <!--            </template>-->
        <!--        </el-menu-item>-->

        <el-menu-item index="/Student/home">
            <template slot="title">
                <i class="el-icon-house"></i>
                <span slot="title">主页</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/Student/Application">
            <template slot="title">
                <i class="el-icon-location-outline"></i>
                <span slot="title">实验室借用申请</span>
            </template>
        </el-menu-item>
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