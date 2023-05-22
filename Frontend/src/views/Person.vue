<template>
    <div>
        <el-card>
            <el-descriptions :column="3"  border class="margin-top" title="个人中心">
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-user"></i>
                        用户ID
                    </template>
                    {{ userID }}
                </el-descriptions-item>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-user"></i>
                        登录ID
                    </template>
                    {{ loginID }}
                </el-descriptions-item>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-user"></i>
                        用户名
                    </template>
                    {{ userName }}
                </el-descriptions-item>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-tickets"></i>
                        角色
                    </template>
                    <el-tag size="small">{{ role }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-mobile-phone"></i>
                        职称
                    </template>
                    {{ title }}
                </el-descriptions-item>
            </el-descriptions>
        </el-card>
    </div>
</template>

<script>
export default {
    name: "Person",
    data() {
        return {
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            userID: "",
            userName: "",
            role: "",
            loginID: "",
            title: "",

        }
    },
    created() {
        this.userID = this.user.userID
        this.userName = this.user.roleSpecificInfo.name
        this.role = this.user.role
        for (let i = 0; i < this.user.role.length; i++) {
            if (this.role[i] === "ROLE_ADMIN"){
                this.role = "管理员"
            }else if (this.role[i] === "ROLE_INSTRUCTOR"){
                this.role = "教师"
            }else if (this.role[i] === "ROLE_TECHNICIAN"){
                this.role = "实验员"
            }else if (this.role[i] === "ROLE_STUDENT"){
                this.role = "学生"
            }
        }
        this.loginID = this.user.loginID
        this.title = this.user.roleSpecificInfo.instructorTitle
    }
}
</script>

<style scoped>

</style>