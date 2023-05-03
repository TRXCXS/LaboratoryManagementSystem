<template>
    <div>
        <!--        <el-card>-->
        <!--            <div style="display: flex;justify-content: space-around; margin: 10px 0">-->
        <!--                <h5 style="margin-top: 6px;">信用检索:</h5>-->
        <!--                <el-input style="width: 200px" placeholder="请输入姓名" suffix-icon="el-icon-search"></el-input>-->
        <!--                &lt;!&ndash;                <el-input style="width: 200px" placeholder="请输入邮箱" suffix-icon="el-icon-message" class="ml-5"></el-input>&ndash;&gt;-->
        <!--                &lt;!&ndash;                <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-position" class="ml-5"></el-input>&ndash;&gt;-->
        <!--                <el-button type="primary">搜索</el-button>-->
        <!--            </div>-->
        <!--        </el-card>-->


        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
            <el-table-column prop="credrec_id" label="信用记录表单项ID" >
            </el-table-column>
            <el-table-column prop="increment" label="加扣分">
            </el-table-column>
            <el-table-column prop="type" label="类型">
            </el-table-column>
            <el-table-column label="操作"  align="center">
                <template slot-scope="scope">
                    <el-button type="warning" @click="linkToAppeal(scope.row.credrec_id)" >申诉 <i class="el-icon-thumb"></i></el-button>
                </template>
            </el-table-column>
        </el-table>
<!--        <div style="padding: 10px 0;">-->
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
    name: "CreditRecord",
    data() {
        // const item = {
        //     date: '2016-05-02',
        //     increment:'-10',
        //     type:'占座被举报',
        //     point: '120',
        // };
        return {
            // tableData: Array(10).fill(item),
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
        // console.log(this.$route.query.id)
    },
    methods:{
        load(){
            // this.request.get("/credit_record/"+this.$route.query.id).then(res=>{
            //     console.log(res)
            //     this.tableData = res
            // })
            this.request.get("/credit_record/"+this.user.user_id).then(res=>{
                console.log(res)
                this.tableData = res
            })
        },
        linkToAppeal(id){
            this.$router.push({
                path:"/Reservation/Appeal",
                query:{
                    credit_record_id:id,
                }
            })
        },
    }
}
</script>

<style scoped>

</style>