<template>
    <div>
        <!--        <el-card>-->
        <!--            <div style="display: flex;justify-content: space-around; margin: 10px 0">-->
        <!--                <h5 style="margin-top: 6px;">桌子检索:</h5>-->
        <!--                <el-input style="width: 200px" placeholder="请输桌子ID" suffix-icon="el-icon-star-off" class="ml-5"></el-input>-->
        <!--                <el-button type="primary">搜索</el-button>-->
        <!--            </div>-->
        <!--        </el-card>-->

        <!--        <div style="margin: 10px 0">-->
        <!--            <el-button type="primary">新增 <i class="el-icon-circle-plus-outline"></i></el-button>-->
        <!--            <el-button type="danger">批量删除 <i class="el-icon-remove-outline"></i></el-button>-->
        <!--        </div>-->

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
            <el-table-column prop="seat_id" label="座位ID">
            </el-table-column>
            <el-table-column prop="number" label="座位编号">
            </el-table-column>
            <el-table-column label="操作"  align="center" width="250px">
                <template slot-scope="scope">
                    <el-button type="success"  @click="confirm_res(scope.row.seat_id)">选座 <i class="el-icon-edit"></i></el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
export default {
    name: "SelectSeat",
    data() {
        return {
            tableData:[],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',

            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},

            desk_id:"",
            seat_id:"",
            startTime:"",
            endTime:"",

        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.desk_id = this.$route.query.desk_id
            this.startTime = this.$route.query.startTime
            this.endTime = this.$route.query.endTime

            this.request.get("/seat/available/" + this.$route.query.desk_id).then(res => {
                console.log("dasdasdasdsada")
                console.log(res)
                // for(let i = 0;i<res.length;i++){
                //     if (res[i].power){
                //         res[i].power = "是"
                //     }
                //     else res[i].power = "否"
                // }
                this.tableData = res
            })
        },

        confirm_res(seat_id){
            this.seat_id=seat_id
            this.$confirm('请确保你已确认好要预约的座位位置，是否确定预约？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.post("/reservation/reserve?user_id="+this.user.user_id+"&seat_id="+this.seat_id+"&time_start="+this.startTime+"&time_end="+this.endTime).then(res=>{
                    if (res) {
                        this.$message({
                            type: 'success',
                            message: '预约成功!'
                        });
                        this.load()
                    } else {
                        this.$message.error("预约失败")
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消'
                });
            });
        },

    },

}
</script>

<style scoped>

</style>