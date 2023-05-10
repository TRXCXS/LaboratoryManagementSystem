<template>
    <div>
        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
            <el-table-column prop="time" label="报修日期">
            </el-table-column>
            <el-table-column prop="lab_num" label="实验室编号">
            </el-table-column>
            <el-table-column prop="description" label="故障描述">
            </el-table-column>
            <el-table-column prop="admin_scrutinized" label="状态" width="100px">

                <el-popover>
                    <el-tag :type="this.handledState==='已维修' ? 'success' : 'info'" slot="reference" >{{handledState}}</el-tag>
                </el-popover>

            </el-table-column>
<!--            <el-table-column label="操作" align="center">-->
<!--                <template slot-scope="scope">-->
<!--                    <el-popconfirm-->
<!--                        title="是否确定？保修状态将被置为“维修中”"-->
<!--                        @confirm="reset"-->
<!--                    >-->
<!--                        <el-button slot="reference" type="warning">维修中 <i class="el-icon-edit"></i></el-button>-->
<!--                    </el-popconfirm>-->
<!--                    <el-button type="danger" @click="del(scope.row.user_id)" style="margin-left: 5px">删除 <i class="el-icon-remove-outline"></i></el-button>-->
<!--                </template>-->
<!--            </el-table-column>-->
        </el-table>

        <el-dialog title="报修信息" :visible.sync="dialogFormVisible"  width="30%">
            <el-form :label-width="formLabelWidth">
                <el-form-item label="报修教师">
                    <el-input v-model="teacher_name" autocomplete="off" disabled></el-input>
                </el-form-item>
                <el-form-item label="实验室编号">
                    <el-input v-model="lab_num" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="故障描述">
                    <el-input type="textarea" v-model="description" autocomplete="off"></el-input>
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
    name: "Tester",
    data() {
        const item = {
            time:"2023-4-10",
            lab_num:"532",
            description:"投影仪坏了",
            // handledState:"维修中"
        };
        return {
            // tableData: [],
            tableData: Array(10).fill(item),
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',
            handledState:"",

            teacher_name:"",
            lab_num:"",
            description:"",


            formLabelWidth: '80px',
            username: "",
            role: "",
            password: "",
            dialogFormVisible: false,
            multipleSelection: [],
        }
    },
    created() {
        this.load()
    },
    methods:{
        load(){
            this.request.get("/user/admins").then(res=>{
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
            this.request.post("/user/admins?username="+this.username+"&password="+this.password+"&role="+this.role).then(res =>{
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
        del(id){
            console.log(id)
            this.$confirm('此操作将永久删除该条记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.delete("/user/admins/"+id).then(res=>{
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
        handleAdd(){
            this.dialogFormVisible = true;
            this.form = {}
        },
        reset(){
            this.$message.success("已重置")
        }
    }
}
</script>

<style scoped>

</style>