<template>
    <div>
<!--        <div style="margin: 10px 0">-->
<!--            <el-button type="primary"  @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>-->
<!--            &lt;!&ndash;            <el-button type="danger">批量删除 <i class="el-icon-remove-outline"></i></el-button>&ndash;&gt;-->
<!--            <el-button type="primary">导入 <i class="el-icon-bottom"></i></el-button>-->
<!--            &lt;!&ndash;            <el-button type="primary">导出 <i class="el-icon-top"></i></el-button>&ndash;&gt;-->
<!--        </div>-->

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
            <!--            <el-table-column prop="app_time" label="申请时间" >-->
            <!--            </el-table-column>-->
            <el-table-column prop="app_semester" label="上课学期" >
            </el-table-column>
            <el-table-column prop="course_name" label="课程名称"  width="100px">
            </el-table-column>
            <el-table-column prop="lab_type" label="实验室类型" width="100px">
            </el-table-column>
            <el-table-column prop="class" label="学生班级" width="150px">
            </el-table-column>
            <el-table-column prop="stu_num" label="学生人数" width="80px">
            </el-table-column>
            <el-table-column prop="start_week" label="起始周" width="60px">
            </el-table-column>
            <el-table-column prop="end_week" label="结束周" width="60px">
            </el-table-column>
            <el-table-column prop="app_section" label="节次" width="50px">
            </el-table-column>
            <el-table-column prop="admin_scrutinized" label="状态" width="100px">

                <el-popover>
                    <el-tag :type="this.handledState==='已排课' ? 'success' : 'info'" slot="reference" >{{handledState}}</el-tag>
                </el-popover>

            </el-table-column>
            <el-table-column label="操作"  align="center" >
                <template slot-scope="scope">
                    <el-button type="primary"  >排课 <i class="el-icon-edit"></i></el-button>
<!--                    <el-button type="primary">确认使用完毕 <i class="el-icon-remove-outline"></i></el-button>-->
                </template>
            </el-table-column>
        </el-table>

        <el-dialog title="用户信息" :visible.sync="dialogFormVisible"  width="30%">
            <el-form :label-width="formLabelWidth">
                <el-form-item label="用户名">
                    <el-input v-model="username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="password" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="角色">
                    <el-input v-model="role" autocomplete="off"></el-input>
                </el-form-item>

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
    name: "ClassScheduling",
    data() {
        const item = {
            app_time:"2023-4-15-20:30:15",
            app_semester:"2023-2024-1",
            course_name:"现代软件开发",
            lab_type:"机房",
            class:"计算机科学与技术三班",
            stu_num:"55",
            start_week:"1",
            end_week:"12",
            app_section:"5-6",
        };
        return {
            // tableData: [],
            tableData: Array(10).fill(item),
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,

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