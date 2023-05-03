<template>
    <div>
<!--        <el-card style="margin-bottom: 20px">-->
<!--            <div style="display: flex;justify-content: space-around; margin: 10px 0">-->
<!--                <h5 style="margin-top: 6px;">查找预约:</h5>-->

<!--                <template>-->
<!--                    <div class="block">-->
<!--                        <span class="demonstration"></span>-->
<!--                        <el-date-picker-->
<!--                            v-model="value1"-->
<!--                            type="datetimerange"-->
<!--                            range-separator="至"-->
<!--                            start-placeholder="开始日期"-->
<!--                            end-placeholder="结束日期">-->
<!--                        </el-date-picker>-->
<!--                    </div>-->
<!--                </template>-->

<!--                <el-input style="width: 200px" placeholder="请输入预约人姓名" suffix-icon="el-icon-search"></el-input>-->
<!--                <el-button type="primary">搜索</el-button>-->
<!--            </div>-->
<!--        </el-card>-->

<!--        <div style="margin: 10px 0">-->
<!--            <el-button type="primary">新增 <i class="el-icon-circle-plus-outline"></i></el-button>-->
<!--            <el-button type="danger">批量删除 <i class="el-icon-remove-outline"></i></el-button>-->
<!--            <el-button type="primary">导入 <i class="el-icon-bottom"></i></el-button>-->
<!--            <el-button type="primary">导出 <i class="el-icon-top"></i></el-button>-->
<!--        </div>-->

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
            <el-table-column prop="reservation_id" label="预约工单号">
            </el-table-column>
            <el-table-column prop="res_time" label="预约日期" >
            </el-table-column>
            <el-table-column prop="user_id" label="预约人ID">
            </el-table-column>
            <el-table-column prop="seat_id" label="预约座位" >
            </el-table-column>
            <el-table-column prop="time_start" label="开始时间" >
            </el-table-column>
            <el-table-column prop="time_end" label="结束时间" >
            </el-table-column>

            <el-table-column label="操作"  align="center">
                <template slot-scope="scope">
<!--                    <el-button type="info">查看 <i class="el-icon-view"></i></el-button>-->
                    <el-button type="danger" @click="releaseSeats(scope.row.reservation_id)">强制释放 <i class="el-icon-remove-outline"></i></el-button>
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
    name: "Reservation",
    data() {
        return {
            tableData:[],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',

            value1: '',
        }
    },
    created() {
        this.load()
    },
    methods:{
        load(){
            this.request.get("/reservation/all").then(res=>{
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
            // console.log(this.user.user_id)
        },
        releaseSeats(id){
            console.log(id)
            this.$confirm('此操作将释放该此座位, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.delete("/reservation/release/"+id).then(res=>{
                    if (res) {
                        this.$message({
                            type: 'success',
                            message: '释放座位成功!'
                        });
                        this.load()
                    } else {
                        this.$message.error("释放座位失败")
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