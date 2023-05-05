<template>
    <div>
        <div style="margin: 10px 0">
            <el-button type="primary"  @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            <el-select v-model="semester" placeholder="请选择学期" style="padding-left: 10px">
                <el-option label="2022-2023-1" value="2022-2023-1"></el-option>
                <el-option label="2022-2023-1" value="2022-2023-1"></el-option>
                <el-option label="2022-2023-1" value="2022-2023-1"></el-option>
                <el-option label="2022-2023-1" value="2022-2023-1"></el-option>
                <el-option label="2022-2023-1" value="2022-2023-1"></el-option>
                <el-option label="2022-2023-1" value="2022-2023-1"></el-option>
            </el-select>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
            <el-table-column prop="user_id" label="ID">
            </el-table-column>
            <el-table-column prop="username" label="学期">
            </el-table-column>
            <el-table-column prop="role" label="角色">
            </el-table-column>
            <el-table-column prop="credit" label="信用分">
            </el-table-column>
<!--            <el-table-column label="操作" align="center">-->
<!--                <template slot-scope="scope">-->
<!--                    <el-popconfirm-->
<!--                        title="是否确定重置密码？密码将被重置为“123456”"-->
<!--                        @confirm="reset"-->
<!--                    >-->
<!--                        <el-button slot="reference" type="warning">重置密码 <i class="el-icon-edit"></i></el-button>-->
<!--                    </el-popconfirm>-->
<!--                    <el-button type="danger" @click="del(scope.row.user_id)" style="margin-left: 5px">删除 <i class="el-icon-remove-outline"></i></el-button>-->
<!--                </template>-->
<!--            </el-table-column>-->
        </el-table>

        <el-dialog title="新增学期信息" :visible.sync="dialogFormVisible"  width="30%">
            <el-form :label-width="formLabelWidth">
                <el-form-item label="学期">
                    <el-input v-model="addSemester" autocomplete="off"></el-input>
                </el-form-item>
<!--                <el-form-item label="密码">-->
<!--                    <el-input v-model="password" autocomplete="off"></el-input>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="角色">-->
<!--                    <el-input v-model="role" autocomplete="off"></el-input>-->
<!--                </el-form-item>-->

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>

export default {
    name: "Semester",
    data() {
        return {
            tableData: [],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            semester:"",
            addSemester:"",

            formLabelWidth: '80px',
            username: "",
            role: "",
            password: "",
            dialogFormVisible: false,
            multipleSelection: [],

            headerBg: 'headerBg'
        }
    },
    created() {
        this.load()
    },
    methods:{
        load(){
            this.request.get("/user/users").then(res=>{
                console.log(res)
                this.tableData = res
            })
        },
        resetDialog(){
            this.username =""
            this.password =""
            this.role=""
            this.load()
        },
        cancelAdd(){
            this.dialogFormVisible = false;
            this.resetDialog()
        },
        save(){
            this.request.post("/user/users?username="+this.username+"&password="+this.password+"&role="+this.role).then(res =>{
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
        handleAdd(){
            this.dialogFormVisible = true;
            this.form = {}
        },
        del(id){
            console.log(id)
            this.$confirm('此操作将永久删除该条记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.delete("/user/users/"+id).then(res=>{
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

        reset(){
            this.$message.success("已重置")
        }
    }
}
</script>

<style scoped>

</style>