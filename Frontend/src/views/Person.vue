<template>
    <div>
        <el-card>
            <el-descriptions :column="3" :size="size" border class="margin-top" title="个人中心">
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-user"></i>
                        用户ID
                    </template>
                    {{ user_id }}
                </el-descriptions-item>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-user"></i>
                        用户名
                    </template>
                    {{ user_name }}
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
                        信用分
                    </template>
                    {{ credit }}
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
            user_id: "",
            user_name: "",
            role: "",
            credit: ""

        }
    },
    created() {
        this.user_id = this.user.user_id
        this.user_name = this.user.username
        this.role = this.user.role
        this.credit = this.user.credit
    }
}
</script>

<style scoped>

</style>