<template>
    <el-table
            :data="tableData"
            style="width: 100%"
            :cell-style="columnStyle">
        <el-table-column
                prop="day"
                label=""
                width="150">
        </el-table-column>
        <el-table-column
                :data="tableData.lab"
                label="实验室"
                width="150">
        </el-table-column>
        <el-table-column
                prop="room"
                label="机房"
                width="150">
        </el-table-column>
        <el-table-column
                prop="week_1-2"
                label="1-2"
                width="150">
        </el-table-column>
        <el-table-column
                prop="week_3-5"
                label="3-5"
                width="150">
        </el-table-column>
        <el-table-column
                prop="week_6-7"
                label="6-7"
                width="150">
        </el-table-column>
        <el-table-column
                prop="week_10-12"
                label="10-12"
                width="150">
        </el-table-column>
        <el-table-column
                prop="week_13-15"
                label="13-15"
                width="150">
        </el-table-column>
        <el-table-column label="配送信息">
            <el-table-column
                      prop="name"
                      label="姓名"
                      width="120">
              </el-table-column>
            <el-table-column label="地址">
                <el-table-column
                        prop="province"
                        label="省份"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="city"
                        label="市区"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="address"
                        label="地址"
                        width="300">
                </el-table-column>
                <el-table-column
                        prop="zip"
                        label="邮编"
                        width="120">
                </el-table-column>
            </el-table-column>
        </el-table-column>
    </el-table>
</template>

<script>
export default {
    name: "SchoolTimetable",
    data() {
        return {

            tableData: [{
                day: '星期一',
                lab:[{
                    ruanjianshiyanshi:"软件实验室"
                },{
                    jisuanjixitongshiyanshi:"计算机系统实验室"
                }],
                province: '上海',
                city: '普陀区',
                address: '上海市普陀区金沙江路 1518 弄',
                zip: 200333
            }, {
                day: '星期五',
                name: '王小虎',
                province: '上海',
                city: '普陀区',
                address: '上海市普陀区金沙江路 1518 弄',
                zip: 200333
            },]
        }
    },
    methods:{
        columnStyle({ row, column, rowIndex, columnIndex }) {
            if (columnIndex == 0) {
                // 修改每行第一个单元格的背景色
                return "background:#f3f6fc;";
            }else{
                return "background:#ffffff;";
            }
        },
    }
}
</script>

<style scoped>

</style>