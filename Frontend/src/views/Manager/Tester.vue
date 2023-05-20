<template>
    <div>
        <el-card>
            <div style="display: flex; margin: 10px 0">
                <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
                <div style="display: flex;margin-left: 280px">
                    <h5 style="margin-top: 6px; margin-right: 10px">实验员检索:</h5>
                    <el-input placeholder="请输入实验员姓名" style="width: 200px" suffix-icon="el-icon-search"  @input="input" v-model="value"></el-input>
                </div>
            </div>
        </el-card>
        <div style="margin: 10px 0; width: 175px" >
            <el-upload
                class="upload-demo"
                ref="upload"
                action="http://localhost:8080/user/batch"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :file-list="fileList"
                :auto-upload="false">
                <el-button slot="trigger" size="small" type="primary">批量导入</el-button>
                <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">确定上传</el-button>
            </el-upload>
        </div>

        <el-table :data="tableData" :header-cell-class-name="headerBg" border stripe>
            <el-table-column label="实验员ID" prop="technicianID">
            </el-table-column>
            <el-table-column label="实验员姓名" prop="name">
            </el-table-column>
            <el-table-column label="职称" prop="title">
            </el-table-column>
            <el-table-column align="center" label="操作">
                <template slot-scope="scope">
                    <el-popconfirm
                            title="是否确定重置密码？密码将被重置为“123456”"
                            @confirm="reset(scope.row.technicianID)"
                    >
                        <el-button slot="reference" type="warning">重置密码 <i class="el-icon-edit"></i></el-button>
                    </el-popconfirm>
                    <el-button style="margin-left: 5px" type="danger" @click="del(scope.row.technicianID)">删除 <i
                            class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :visible.sync="dialogFormVisible" title="实验员信息" width="30%">
            <el-form :label-width="formLabelWidth">
<!--                <el-form-item label="实验员ID">-->
<!--                    <el-input v-model.number="addUser.roleSpecificInfo.technicianID" autocomplete="off"></el-input>-->
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
        return {
            value:"",
            tableData: [],
            fileList:[],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',

            formLabelWidth: '80px',
            tester_id: "",
            tester_name: "",
            tester_title: "",
            dialogFormVisible: false,
            multipleSelection: [],
            resetPassword:{
                newPassword:"123456",
                userID:0
            },

            addUser:{
                roles: [
                    "ROLE_TECHNICIAN"
                ],
                roleSpecificInfo: {
                    technicianID: 0,
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
            this.request.get("/user/technician/all").then(res => {
                console.log(res)
                this.tableData = res.data
            })
        },
        resetDialog() {
            this.addUser.roleSpecificInfo.name = ""
            this.addUser.roleSpecificInfo.title = ""
        },
        save() {
            console.log(this.addUser)
            this.request.post("/user/technician",this.addUser).then(res => {
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
        cancelAdd() {
            this.dialogFormVisible = false;
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
        handleAdd() {
            this.dialogFormVisible = true;
            this.resetDialog()
        },
        reset(id) {
            this.resetPassword.userID = id
            // console.log(this.resetPassword)
            this.request.put("/user/password?",this.resetPassword).then(res=>{
                console.log(res)
                this.$message.success("已重置")
            })
        },
        input(){
            console.log(this.value)
            this.request.get("/user/technician/name",{
                params:{
                    name:this.value
                }
            }).then(res =>{
                this.tableData = res.data
            })
        },
        handlePreview(file){
            console.log(file);
        },
        handleRemove(file,fileList){
            console.log(file, fileList);
        },
        submitUpload(){
            this.$refs.upload.submit();
        },
    }
}
</script>

<style scoped>

</style>