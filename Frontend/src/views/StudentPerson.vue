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
                        学号/工号
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
                    <el-tag
                            size="small"
                            style="margin-right: 5px"
                            v-for="item in items"
                            :key="item.label"
                            :type="item.type"
                    >{{ item.label }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-monitor"></i>
                        专业
                    </template>
                    {{ major }}
                </el-descriptions-item>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-reading"></i>
                        班级
                    </template>
                    {{ clazz }}
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
            major:"",
            clazz:"",
            items:[],
            titles:[],
        }
    },
    created() {
        console.log(this.user)
        this.userID = this.user.userID
        this.userName = this.user.roleSpecificInfo.name
        this.role = this.user.role
        this.major = this.user.roleSpecificInfo.major
        this.clazz = this.user.roleSpecificInfo.class
        for (let i = 0; i < this.user.role.length; i++) {
            let label = null
            let type = ""
            if (this.user.role[i] === "ROLE_ADMIN"){
                this.role = "管理员"
                label = this.role
                type = "primary"
            }else if (this.user.role[i] === "ROLE_INSTRUCTOR"){
                this.role = "教师"
                label = this.role
                type = "success"
            }else if (this.user.role[i] === "ROLE_TECHNICIAN"){
                this.role = "实验员"
                label = this.role
                type = "warning"
            }else if (this.user.role[i] === "ROLE_STUDENT"){
                this.role = "学生"
                label = this.role
                type = "info"
            }
            this.items.push({label:label,type:type})
        }
        this.loginID = this.user.loginID
        console.log(this.user.roleSpecificInfo)
        // if (this.user.roleSpecificInfo.length === 3){
        //     let label = this.user.roleSpecificInfo.instructorTitle
        //     let type = "primary"
        //     this.titles.push({label:label,type:type})
        //     label = this.user.roleSpecificInfo.technicianTitle
        //     type = "warning"
        //     this.titles.push({label:label,type:type})
        // }
        // console.log(this.titles)

    }
}
</script>

<style scoped>

</style>