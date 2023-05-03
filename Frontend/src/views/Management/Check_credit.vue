<template>
    <div>

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
            <el-table-column prop="credrec_id" label="信用记录表单项ID" >
            </el-table-column>
            <el-table-column prop="increment" label="加扣分">
            </el-table-column>
            <el-table-column prop="type" label="类型">
            </el-table-column>
<!--            <el-table-column prop="point" label="信用分">-->
<!--            </el-table-column>-->
        </el-table>
    </div>
</template>

<script>
export default {
    name: "Check_credit",
    data() {
        return {
            tableData:[],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg'
        }
    },
    created() {
        this.load()
        console.log(this.$route.query.id)
    },
    methods:{
        load(){
            this.request.get("/credit_record/"+this.$route.query.id).then(res=>{
                console.log(res)
                this.tableData = res
            })
        },
    }
}
</script>

<style scoped>

</style>