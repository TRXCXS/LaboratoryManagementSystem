<template>
    <div>

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
            <el-table-column prop="reservation_id" label="预约ID" >
            </el-table-column>
            <el-table-column prop="res_time" label="预约日期" >
            </el-table-column>
            <el-table-column prop="seat_id" label="预约位置">
            </el-table-column>
            <el-table-column prop="time_start" label="开始时间" >
            </el-table-column>
            <el-table-column prop="time_end" label="结束时间" >
            </el-table-column>
            <el-table-column label="操作"  align="center">
                <template slot-scope="scope">
<!--                    <el-button type="success">修改 <i class="el-icon-edit"></i></el-button>-->
                    <el-button type="danger" @click="cancel_res(scope.row.reservation_id)">撤销 <i class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
export default {
    name: "MyAppointment",
    data() {
        return {
            tableData:[],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',

            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}

        }
    },
    created() {
        this.load()
    },
    methods:{
        load(){
            // console.log(user.user_id)
            this.request.get("/reservation/"+this.user.user_id).then(res=>{
                console.log(res)

                for(let i = 0;i<res.length;i++){
                    let origin_res_time = res[i].res_time
                    let origin_start_time = res[i].time_start
                    let origin_end_time = res[i].time_end
                    let date1 = new Date(origin_res_time);
                    let date2 = new Date(origin_start_time);
                    let date3 = new Date(origin_end_time);
                    let time1=date1.getFullYear() + '-' + ((date1.getMonth() + 1)<10?"0"+(date1.getMonth() + 1):(date1.getMonth() + 1) )+ '-' + (date1.getDate()<10?"0"+date1.getDate():date1.getDate() )+ ' ' + (date1.getHours()<10?"0"+date1.getHours():date1.getHours() )+ ':' + (date1.getMinutes()<10?"0"+date1.getMinutes():date1.getMinutes() )+ ':' + (date1.getSeconds()<10?"0"+date1.getSeconds():date1.getSeconds());
                    let time2=date1.getFullYear() + '-' + ((date2.getMonth() + 1)<10?"0"+(date2.getMonth() + 1):(date2.getMonth() + 1) )+ '-' + (date2.getDate()<10?"0"+date2.getDate():date2.getDate() )+ ' ' + (date2.getHours()<10?"0"+date2.getHours():date2.getHours() )+ ':' + (date2.getMinutes()<10?"0"+date2.getMinutes():date2.getMinutes() )+ ':' + (date2.getSeconds()<10?"0"+date2.getSeconds():date2.getSeconds());
                    let time3=date1.getFullYear() + '-' + ((date3.getMonth() + 1)<10?"0"+(date3.getMonth() + 1):(date3.getMonth() + 1) )+ '-' + (date3.getDate()<10?"0"+date3.getDate():date3.getDate() )+ ' ' + (date3.getHours()<10?"0"+date3.getHours():date3.getHours() )+ ':' + (date3.getMinutes()<10?"0"+date3.getMinutes():date3.getMinutes() )+ ':' + (date3.getSeconds()<10?"0"+date3.getSeconds():date3.getSeconds());
                    res[i].res_time = time1
                    res[i].time_start = time2
                    res[i].time_end = time3
                }
                this.tableData = res
            })
        },
        cancel_res(res_id){
            this.$confirm('确定取消预约?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.delete("/reservation/cancel/"+res_id).then(res=>{
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