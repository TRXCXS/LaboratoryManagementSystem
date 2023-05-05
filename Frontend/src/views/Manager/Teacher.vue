<template>
    <div>
        <el-card>
            <div style="display: flex;justify-content: space-around; margin: 10px 0">
                <h5 style="margin-top: 6px;">教师检索:</h5>
                <el-input style="width: 200px" placeholder="请输入教师姓名" suffix-icon="el-icon-search"></el-input>
                <!--                <el-input style="width: 200px" placeholder="请输入用户ID" suffix-icon="el-icon-star-off" class="ml-5"></el-input>-->
                <el-button type="primary">搜索</el-button>
            </div>
        </el-card>

        <div style="margin: 10px 0">
            <el-button type="primary"  @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
<!--            <el-button type="danger">批量删除 <i class="el-icon-remove-outline"></i></el-button>-->
            <el-button type="primary">导入 <i class="el-icon-bottom"></i></el-button>
<!--            <el-button type="primary">导出 <i class="el-icon-top"></i></el-button>-->
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
            <el-table-column prop="teacher_id" label="教师ID">
            </el-table-column>
            <el-table-column prop="teacher_name" label="教师姓名">
            </el-table-column>
            <el-table-column prop="title" label="职称">
            </el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-popconfirm
                        title="是否确定重置密码？密码将被重置为“123456”"
                        @confirm="reset"
                    >
                        <el-button slot="reference" type="warning">重置密码 <i class="el-icon-edit"></i></el-button>
                    </el-popconfirm>
                    <el-button type="danger" @click="del(scope.row.user_id)" style="margin-left: 5px">删除 <i class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog title="教师信息" :visible.sync="dialogFormVisible"  width="30%">
            <el-form :label-width="formLabelWidth">
                <el-form-item label="教师ID">
                    <el-input v-model="teacher_id" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="teacher_name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="职称">
                    <el-input v-model="teacher_title" autocomplete="off"></el-input>
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
import Login from "@/views/Login.vue";
import teacher from "@/views/Manager/Teacher.vue";

export default {
    name: "Teacher",
    computed: {
        teacher() {
            return teacher
        }
    },

    data() {
        const item = {
            teacher_id:"202025310320",
            teacher_name:"小填",
            title:"校长",
        };
        return {
            // tableData: [],
            tableData: Array(10).fill(item),
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,

            formLabelWidth: '80px',
            teacher_id:"",
            teacher_name:"",
            teacher_title:"",
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
            this.teacher_id =""
            this.teacher_name =""
            this.teacher_title=""
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