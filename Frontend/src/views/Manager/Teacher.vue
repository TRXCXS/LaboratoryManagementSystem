<template>
    <div>
        <el-card>
            <div style="display: flex;justify-content: space-around; margin: 10px 0">
                <h5 style="margin-top: 6px;">教师检索:</h5>
                <el-input placeholder="请输入教师姓名" style="width: 200px" suffix-icon="el-icon-search"></el-input>
                <!--                <el-input style="width: 200px" placeholder="请输入用户ID" suffix-icon="el-icon-star-off" class="ml-5"></el-input>-->
                <el-button type="primary">搜索</el-button>
            </div>
        </el-card>

        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            <!--            <el-button type="danger">批量删除 <i class="el-icon-remove-outline"></i></el-button>-->
            <el-button type="primary">导入 <i class="el-icon-bottom"></i></el-button>
            <!--            <el-button type="primary">导出 <i class="el-icon-top"></i></el-button>-->
        </div>

        <el-table :data="tableData" :header-cell-class-name="headerBg" border stripe>
            <el-table-column label="教师ID" prop="instructorID">
            </el-table-column>
            <el-table-column label="教师姓名" prop="name">
            </el-table-column>
            <el-table-column label="职称" prop="title">
            </el-table-column>
            <el-table-column align="center" label="操作">
                <template slot-scope="scope">
                    <el-popconfirm
                            title="是否确定重置密码？密码将被重置为“123456”"
                            @confirm="reset(scope.row.instructorID)"
                    >
                        <el-button slot="reference" type="warning">重置密码 <i class="el-icon-edit"></i></el-button>
                    </el-popconfirm>
                    <el-button style="margin-left: 5px" type="danger" @click="del(scope.row.instructorID)">删除 <i
                            class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :visible.sync="dialogFormVisible" title="教师信息" width="30%">
            <el-form :label-width="formLabelWidth">
<!--                <el-form-item label="教师ID">-->
<!--                    <el-input v-model="teacher_id" autocomplete="off"></el-input>-->
<!--                </el-form-item>-->
                <el-form-item label="姓名">
                    <el-input v-model="addUser.roleSpecificInfo.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="职称">
                    <el-input v-model="addUser.roleSpecificInfo.title" autocomplete="off"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelAdd">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
import teacher from "@/views/Manager/Teacher.vue";

export default {
    name: "Teacher",
    computed: {
        teacher() {
            return teacher
        }
    },

    data() {
        return {
            tableData: [],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,

            formLabelWidth: '80px',
            teacher_id: "",
            teacher_name: "",
            teacher_title: "",
            dialogFormVisible: false,
            multipleSelection: [],

            headerBg: 'headerBg',
            resetPassword:{
                newPassword:"123456",
                userID:0
            },
            addUser:{
                roles: [
                    "ROLE_INSTRUCTOR"
                ],
                roleSpecificInfo: {
                    instructorID: 0,
                    name:"",
                    title:""
                },
                password: "123456",
                loginID: 0
            }
        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.request.get("/user/instructor/all").then(res => {
                console.log(res)
                this.tableData = res.data
            })
        },
        resetDialog() {
            this.addUser.roleSpecificInfo.name = ""
            this.addUser.roleSpecificInfo.title = ""
        },
        cancelAdd() {
            this.dialogFormVisible = false;
            this.resetDialog()
        },
        save() {
            this.request.post("/user/instructor", this.addUser).then(res => {
                if (res) {
                    this.$message.success("添加成功")
                    this.dialogFormVisible = false
                    this.resetDialog()
                    this.load()
                } else {
                    this.$message.error("添加失败")
                }
            })
        },
        handleAdd() {
            this.dialogFormVisible = true;
            this.resetDialog()
        },
        del(id) {
            console.log(id)
            this.$confirm('此操作将永久删除该条记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.delete("/user?userID=" + id).then(res => {
                    if (res) {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        this.load()
                    } else {
                        this.$message.error("删除失败")
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },

        reset(id) {
            this.resetPassword.userID = id
            this.request.put("/user/password?",this.resetPassword).then(res=>{
                console.log(res)
                this.$message.success("已重置")
            })
        }
    }
}
</script>

<style scoped>

</style>