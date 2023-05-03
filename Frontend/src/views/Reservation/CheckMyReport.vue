<template>
    <div>
        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
            <el-table-column prop="report_id" label="举报ID" >
            </el-table-column>
            <el-table-column prop="report_time" label="举报时间" >
            </el-table-column>
            <el-table-column prop="seat_id" label="举报位置">
            </el-table-column>
            <el-table-column prop="description" label="描述">
            </el-table-column>
            <el-table-column prop="photo_id" label="图片">
            </el-table-column>
<!--            <el-table-column prop="admin_handled" label="处理">-->
<!--            </el-table-column>-->
            <el-table-column prop="admin_handled" label="状态">
                <el-popover>
                    <el-tag :type="this.handledState==='已处理' ? 'success' : 'info'" slot="reference" >{{handledState}}</el-tag>
                </el-popover>

<!--                <el-tag :type="this.handledState==='已处理' ? 'success' : 'info'" slot="reference" >{{handledState}}</el-tag>-->
            </el-table-column>
            <el-table-column label="操作"  align="center">
                <template slot-scope="scope">
<!--                    <el-button type="success">修改 <i class="el-icon-edit"></i></el-button>-->
                    <el-button type="danger" @click="cancel_report(scope.row.report_id)">撤销 <i class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-table-column>
        </el-table>
<!--        <div style="padding: 10px 0">-->
<!--            <el-pagination-->
<!--                :page-sizes="[5, 10, 15, 20]"-->
<!--                :page-size="10"-->
<!--                layout="total, sizes, prev, pager, next, jumper"-->
<!--                :total="400">-->
<!--            </el-pagination>-->
<!--        </div>-->
    </div>
</template>

<script>
export default {
    name: "MyReport",
    data() {
        return {
            tableData:[],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',
            handledState:"",
            tagForm:"",
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        }
    },
    created() {
        this.load()
    },
    methods:{
        load(){
            // console.log(user.user_id)
            this.request.get("/report/"+this.user.user_id).then(res=>{
                console.log(res)
                for(let i = 0;i<res.length;i++){
                    let origin_report_time = res[i].report_time
                    let date1 = new Date(origin_report_time);
                    let time1=date1.getFullYear() + '-' + ((date1.getMonth() + 1)<10?"0"+(date1.getMonth() + 1):(date1.getMonth() + 1) )+ '-' + (date1.getDate()<10?"0"+date1.getDate():date1.getDate() )+ ' ' + (date1.getHours()<10?"0"+date1.getHours():date1.getHours() )+ ':' + (date1.getMinutes()<10?"0"+date1.getMinutes():date1.getMinutes() )+ ':' + (date1.getSeconds()<10?"0"+date1.getSeconds():date1.getSeconds());
                    res[i].report_time = time1

                    if (res[i].admin_handled){
                        this.handledState = "已处理"
                        this.tagForm = "success"
                    }else {
                        this.handledState = "未处理"
                        this.tagForm = "info"
                    }
                }
                this.tableData = res
            })
            console.log(this.user.user_id)
        },
        cancel_report(rep_id){
            this.$confirm('确定取消举报?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.delete("/report/cancel/"+rep_id).then(res=>{
                    if (res) {
                        this.$message({
                            type: 'success',
                            message: '已取消!'
                        });
                        this.load()
                    } else {
                        this.$message.error("取消失败")
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
    }
}
</script>

<style scoped>

</style>