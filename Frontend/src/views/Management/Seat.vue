<template>
    <div>
        <div style="margin: 10px 0">
            <el-button type="primary"  @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
            <el-table-column prop="seat_id" label="座位ID">
            </el-table-column>
            <el-table-column prop="number" label="座位编号">
            </el-table-column>
            <el-table-column label="操作"  align="center" width="250px">
                <template slot-scope="scope">
<!--                    <el-button type="warning" style="margin-left: 5px">修改 <i class="el-icon-remove-outline"></i></el-button>-->
                    <el-button type="danger" @click="del(scope.row.seat_id)"style="margin-left: 5px">删除 <i class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog title="新增座位" :visible.sync="dialogFormVisible"  width="30%">
            <el-form :label-width="formLabelWidth">
                <el-form-item label="桌子ID">
                    <el-input v-model="desk_id" autocomplete="off" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="座位编号">
                    <el-input v-model="number" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

<!--        <div style="padding: 10px 0">-->
<!--            <el-pagination-->
<!--                    :page-sizes="[5, 10, 15, 20]"-->
<!--                    :page-size="10"-->
<!--                    layout="total, sizes, prev, pager, next, jumper"-->
<!--                    :total="400">-->
<!--            </el-pagination>-->
<!--        </div>-->
    </div>
</template>

<script>
export default {
    name: "Seat",
    data() {
        return {
            tableData:[],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',

            formLabelWidth: '80px',
            desk_id: this.$route.query.id,
            number: "",
            dialogFormVisible: false,
            multipleSelection: [],
        }
    },
    created() {
        this.load()
    },

    methods:{
        load(){
            this.request.get("/seat/"+this.$route.query.id).then(res=>{
                console.log(res)
                this.tableData = res
            })
        },
        resetDialog(){
            this.number =""
            // this.load()
        },
        save(){
            let temp = "/seat/?number="+this.number+"&deskId="+this.$route.query.id
            console.log(temp)
            this.request.post(temp).then(res =>{
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
                this.request.delete("/seat/"+id).then(res=>{
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