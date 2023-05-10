<template>
    <div>
        <el-card>
            <div style="display: flex;justify-content: space-around; margin: 10px 0">
                <h5 style="margin-top: 6px;">学生检索:</h5>
                <el-input placeholder="请输入学生姓名" style="width: 200px" suffix-icon="el-icon-search"></el-input>
                <!--                <el-input style="width: 200px" placeholder="请输入用户ID" suffix-icon="el-icon-star-off" class="ml-5"></el-input>-->
                <el-button type="primary">搜索</el-button>
            </div>
        </el-card>

        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            <el-button type="primary">导入 <i class="el-icon-bottom"></i></el-button>
        </div>

        <el-table :data="tableData" :header-cell-class-name="headerBg" border stripe>
            <el-table-column label="学生账号" prop="student_id">
            </el-table-column>
            <el-table-column label="学生姓名" prop="student_name">
            </el-table-column>
            <el-table-column label="专业" prop="major">
            </el-table-column>
            <el-table-column label="班级" prop="class">
            </el-table-column>
            <el-table-column align="center" label="操作">
                <template slot-scope="scope">
                    <el-popconfirm
                            title="是否确定重置密码？密码将被重置为“123456”"
                            @confirm="reset"
                    >
                        <el-button slot="reference" type="warning">重置密码 <i class="el-icon-edit"></i></el-button>
                    </el-popconfirm>
                    <el-button style="margin-left: 5px" type="danger" @click="del(scope.row.user_id)">删除 <i
                            class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :visible.sync="dialogFormVisible" title="学生信息" width="30%">
            <el-form :label-width="formLabelWidth">
                <el-form-item label="学生ID">
                    <el-input v-model="student_id" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="student_name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="专业">
                    <el-input v-model="student_major" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="班级">
                    <el-input v-model="student_class" autocomplete="off"></el-input>
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

export default {
    name: "Student",

    data() {
        const item = {
            student_id: "202025310320",
            student_name: "小颜",
            major: "计算机科学与技术",
            class: "三班",
        };
        return {
            // tableData: [],
            tableData: Array(10).fill(item),
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,

            formLabelWidth: '80px',

            student_id: "",
            student_name: "",
            student_major: "",
            student_class: "",

            dialogFormVisible: false,
            multipleSelection: [],

            headerBg: 'headerBg'
        }
    },
    created() {
        this.load()
        console.log(this.currentSemester + "Ssssss")
    },
    computed: {
        currentSemester() {
            return this.$store.state.semester
        }
    },
    methods: {
        load() {
            // this.request.get("/user/users").then(res=>{
            //     console.log(res)
            //     this.tableData = res
            // })
        },
        cancelAdd() {
            this.dialogFormVisible = false;
            this.resetDialog()
        },
        resetDialog() {
            this.student_id = ""
            this.student_name = ""
            this.student_major = ""
            this.student_class = ""
            this.load()
        },
        save() {
            // this.request.post("/user/users?username="+this.username+"&password="+this.password+"&role="+this.role).then(res =>{
            //     if (res) {
            //         this.$message.success("添加成功")
            //         this.dialogFormVisible = false
            //         this.resetDialog()
            //         this.load()
            //     } else {
            //         this.$message.error("添加失败")
            //     }
            // })
        },
        handleAdd() {
            this.dialogFormVisible = true;
            this.form = {}
        },
        del(id) {
            console.log(id)
            this.$confirm('此操作将永久删除该条记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                // this.request.delete("/user/users/"+id).then(res=>{
                //     if (res) {
                //         this.$message({
                //             type: 'success',
                //             message: '删除成功!'
                //         });
                //         this.load()
                //     } else {
                //         this.$message.error("删除失败")
                //     }
                // })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },

        reset() {
            this.$message.success("已重置")
        }
    }
}
</script>

<style scoped>

</style>