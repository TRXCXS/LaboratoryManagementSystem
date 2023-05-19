<template>
    <div>
        <!--        <div style="margin: 10px 0">-->
        <!--            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>-->
        <!--        </div>-->

        <el-table :data="tableData" :header-cell-class-name="headerBg" border stripe>
            <el-table-column label="申请维修ID" prop="repairRequestID">
            </el-table-column>
            <el-table-column label="报修日期" prop="requestTime">
            </el-table-column>
            <el-table-column label="实验室ID" prop="labID">
            </el-table-column>
            <el-table-column label="故障描述" prop="requestDescription">
            </el-table-column>
            <el-table-column label="维修开始时间" prop="repairStartTime">
            </el-table-column>
            <el-table-column label="维修结束时间" prop="repairEndTime">
            </el-table-column>
            <el-table-column label="实验员信息" prop="technicianMessage">
            </el-table-column>
            <el-table-column label="状态" prop="status" width="100px">


                <el-popover>
                    <el-tag slot="reference" :type="this.handledState==='已维修' ? 'success' : (this.handledState === '未维修' ? 'info' : 'warning')">
                        {{ handledState }}
                    </el-tag>
                </el-popover>

            </el-table-column>
            <el-table-column align="center" label="操作">
                <template slot-scope="scope">
                    <el-popconfirm
                            title="是否确定？报修状态将被置为“维修中”"
                            @confirm="setRepairing(scope.row.repairRequestID)"
                    >
                        <el-button slot="reference" type="warning">维修中 <i class="el-icon-setting"></i></el-button>
                    </el-popconfirm>
                    <el-button style="margin-left: 5px" type="success" @click="handleMaintenance(scope.row.repairRequestID)">维修完成 <i
                            class="el-icon-circle-check"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :visible.sync="dialogFormVisible" title="确认维修" width="32%">
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
        return {
            tableData: [],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',
            handledState: "",

            tempRepairRequestID:"",
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
    methods: {
        load() {
            this.request.get("/repair-request/all").then(res => {
                for (let i = 0; i < res.data.length; i++) {
                    // let origin_appeal_time = res[i].appeal_time
                    // let date1 = new Date(origin_appeal_time);
                    // let time1 = date1.getFullYear() + '-' + ((date1.getMonth() + 1) < 10 ? "0" + (date1.getMonth() + 1) : (date1.getMonth() + 1)) + '-' + (date1.getDate() < 10 ? "0" + date1.getDate() : date1.getDate()) + ' ' + (date1.getHours() < 10 ? "0" + date1.getHours() : date1.getHours()) + ':' + (date1.getMinutes() < 10 ? "0" + date1.getMinutes() : date1.getMinutes()) + ':' + (date1.getSeconds() < 10 ? "0" + date1.getSeconds() : date1.getSeconds());
                    // res[i].appeal_time = time1
                    if (res.data[i].status ==='NOT_REPAIRED') {
                        this.handledState = "未维修"
                    }else if(res.data[i].status ==='REPAIRED') {
                        this.handledState = "已维修"
                    }else {
                        this.handledState = "维修中"
                    }
                }
                console.log(res)
                this.tableData = res.data
            })
        },
        resetDialog() {
            this.username = ""
            this.password = ""
            this.role = ""
            this.load()
        },
        save(id) {
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
            this.request.put("/repair-request/repaired?repairRequestID="+this.tempRepairRequestID).then(res => {
                this.dialogFormVisible = false;
                this.$message.success("已设置")
                this.load()
            })
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
        handleMaintenance(id) {
            this.dialogFormVisible = true;
            this.description = ""
            this.tempRepairRequestID = id
        },
        setRepairing(id) {
            // this.request.put("/repair-request/repairing",{
            //     params:{
            //         repairRequestID:id
            //     }
            // }).then(res => {
            //     this.$message.success("已设置")
            // })
            this.request.put("/repair-request/repairing?repairRequestID="+id).then(res => {
                this.$message.success("已设置")
                this.load()
            })
        }
    }
}
</script>

<style scoped>

</style>