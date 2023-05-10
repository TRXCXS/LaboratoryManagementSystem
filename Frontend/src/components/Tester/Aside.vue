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
        <el-menu-item index="/Tester/home">
            <template slot="title">
                <i class="el-icon-house"></i>
                <span slot="title">主页</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/Tester/maintenance">
            <template slot="title">
                <i class="el-icon-attract"></i>
                <span slot="title">设备维修</span>
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