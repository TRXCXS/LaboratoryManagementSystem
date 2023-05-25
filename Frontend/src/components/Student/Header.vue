<template>
    <div style="line-height: 60px; display: flex; justify-content: space-between;">
        <div>
            <span :class="collapseBtnClass" style="cursor: pointer; font-size: 18px" @click="collapse"></span>

            <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
                <el-breadcrumb-item :to="'/Student/home'">首页</el-breadcrumb-item>
                <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <!--        <el-image-->
        <!--            style="width: 300px; height: 50px;margin-top: 5px; "-->
        <!--            :src="url"-->
        <!--        ></el-image>-->
        <div>
            <h1>学生端</h1>
        </div>

        <el-dropdown style="width: 260px; cursor: pointer; text-align: right"  trigger="click">
            <span>{{ user.roleSpecificInfo.name }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            <el-dropdown-menu style="width: 100px; text-align: center">
                <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                    <span style="text-decoration: none" @click="personCenter">个人中心</span>
                </el-dropdown-item>
                <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                    <span style="text-decoration: none" @click="logout">退出</span>
                </el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>

<script>
export default {
    name: "Header",
    data() {
        return {
            url: 'https://lib.scau.edu.cn/_upload/tpl/05/47/1351/template1351/img/logo/logo.png',
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        }
    },
    methods: {
        logout() {
            this.request.post("/auth/logout").then(res => {
                this.$router.push("/login")
                localStorage.removeItem("user")
                this.$message.success("退出成功")
            })
            // this.$router.push("/login")
            // localStorage.removeItem("user")
            // this.$message.success("退出成功")
        },
        personCenter() {
            this.$router.push("/Student/Person")
        }
    },
    props: {
        collapseBtnClass: String,
        collapse: "",
        // user: Object
    },
    computed: {
        currentPathName() {
            return this.$store.state.currentPathName;　　//需要监听的数据
        }
    }
}
</script>

<style scoped>

</style>