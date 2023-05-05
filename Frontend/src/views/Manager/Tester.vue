<template>
    <div>
        <el-card>
            <div style="display: flex;justify-content: space-around; margin: 10px 0">
                <h5 style="margin-top: 6px;">实验员检索:</h5>
                <el-input style="width: 200px" placeholder="请输入实验员姓名" suffix-icon="el-icon-search"></el-input>
                <!--                <el-input style="width: 200px" placeholder="请输入用户ID" suffix-icon="el-icon-star-off" class="ml-5"></el-input>-->
                <el-button type="primary">搜索</el-button>
            </div>
        </el-card>
        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            <el-button type="primary">导入 <i class="el-icon-bottom"></i></el-button>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
            <el-table-column prop="tester_id" label="实验员ID">
            </el-table-column>
            <el-table-column prop="tester_name" label="实验员姓名">
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

        <el-dialog title="实验员信息" :visible.sync="dialogFormVisible"  width="30%">
            <el-form :label-width="formLabelWidth">
                <el-form-item label="实验员ID">
                    <el-input v-model="tester_id" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="tester_name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="职称">
                    <el-input v-model="tester_title" autocomplete="off"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelAdd">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

<!--        <div style="padding: 10px 0">-->
<!--              <el-pagination-->
<!--                      :page-sizes="[5, 10, 15, 20]"-->
<!--                      :page-size="10"-->
<!--                      layout="total, sizes, prev, pager, next, jumper"-->
<!--                      :total="400">-->
<!--              </el-pagination>-->
<!--        </div>-->
    </div>
</template>

<script>
export default {
    name: "Tester",
    data() {
        const item = {
            tester_id:"202025310320",
            tester_name:"小鑫",
            title:"网络安全员",
        };
        return {
            // tableData: [],
            tableData: Array(10).fill(item),
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',

            formLabelWidth: '80px',
            tester_id:"",
            tester_name:"",
            tester_title:"",
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
            this.tester_id =""
            this.tester_name =""
            this.tester_title=""
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
        cancelAdd(){
            this.dialogFormVisible = false;
            this.resetDialog()
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