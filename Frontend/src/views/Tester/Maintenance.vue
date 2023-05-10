<template>
    <div>
        <!--        <div style="margin: 10px 0">-->
        <!--            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>-->
        <!--        </div>-->

        <el-table :data="tableData" :header-cell-class-name="headerBg" border stripe>
            <el-table-column label="报修日期" prop="time">
            </el-table-column>
            <el-table-column label="实验室编号" prop="lab_num">
            </el-table-column>
            <el-table-column label="故障描述" prop="description">
            </el-table-column>
            <el-table-column label="状态" prop="admin_scrutinized" width="100px">

                <el-popover>
                    <el-tag slot="reference" :type="this.handledState==='已维修' ? 'success' : 'info'">
                        {{ handledState }}
                    </el-tag>
                </el-popover>

            </el-table-column>
            <el-table-column align="center" label="操作">
                <template slot-scope="scope">
                    <el-popconfirm
                            title="是否确定？报修状态将被置为“维修中”"
                            @confirm="reset"
                    >
                        <el-button slot="reference" type="warning">维修中 <i class="el-icon-setting"></i></el-button>
                    </el-popconfirm>
                    <el-button style="margin-left: 5px" type="success" @click="handleMaintenance">维修完成 <i
                            class="el-icon-circle-check"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :visible.sync="dialogFormVisible" title="确认维修" width="30%">
            <el-form :label-width="formLabelWidth">
                <el-form-item label="维修情况说明">
                    <el-input v-model="description" autocomplete="off" type="textarea"></el-input>
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
            time: "2023-4-10",
            lab_num: "532",
            description: "投影仪坏了",
        };
        return {
            // tableData: [],
            tableData: Array(10).fill(item),
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',
            handledState: "",

            teacher_name: "",
            lab_num: "",
            description: "",


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
    methods: {
        load() {
            this.request.get("/user/admins").then(res => {
                console.log(res)
                this.tableData = res
            })
        },
        resetDialog() {
            this.username = ""
            this.password = ""
            this.role = ""
            this.load()
        },
        save() {
            // this.request.post("/user/admins?username="+this.username+"&password="+this.password+"&role="+this.role).then(res =>{
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
        del(id) {
            console.log(id)
            this.$confirm('此操作将永久删除该条记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.delete("/user/admins/" + id).then(res => {
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
        handleMaintenance() {
            this.dialogFormVisible = true;
            this.description = ""
        },
        reset() {
            this.$message.success("已设置")
        }
    }
}
</script>

<style scoped>

</style>