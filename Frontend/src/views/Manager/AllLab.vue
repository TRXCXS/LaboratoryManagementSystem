<template>
    <div>
        <el-table :data="tableData" :header-cell-class-name="headerBg" border stripe>
            <el-table-column label="实验室ID" prop="labID">
            </el-table-column>
            <el-table-column label="实验室门牌号" prop="labNumber">
            </el-table-column>
            <el-table-column label="实验室名称" prop="name">
            </el-table-column>
            <el-table-column label="实验室类型" prop="labType" >
            </el-table-column>
            <el-table-column label="设备数量" prop="deviceCount">
            </el-table-column>
            <el-table-column label="负责人" prop="technicians">
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
export default {
    name: "MyAppeal",
    data() {
        return {
            tableData: [],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',
            handledState: "",
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},

            formLabelWidth: '80px',

            technicians:"sss"

        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.request.get("/laboratory/all").then(res => {
                for (let i = 0; i < res.data.length; i++) {
                    if(res.data[i].technicians.length === 0){
                        res.data[i].technicians.push("无")
                    }
                }
                console.log(res)
                this.tableData = res.data
            })
        },
    }
}
</script>

<style scoped>

</style>