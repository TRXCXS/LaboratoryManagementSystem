<template>
    <div>
        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            <el-select v-model="chooseSemester" placeholder="请选择学期" style="padding-left: 10px" @change="change">
                <el-option label="2022-2023-1" value="2022-2023-1"></el-option>
                <el-option label="2022-2023-2" value="2022-2023-2"></el-option>
                <el-option label="2023-2024-1" value="2023-2024-1"></el-option>
                <el-option label="2023-2024-2" value="2023-2024-2"></el-option>
                <el-option label="2024-2025-1" value="2024-2025-1"></el-option>
                <el-option label="2024-2025-2" value="2024-2025-2"></el-option>
            </el-select>
        </div>

        <el-table :data="tableData" :header-cell-class-name="headerBg" border stripe>
            <el-table-column label="学期ID" prop="semesterID">
            </el-table-column>
            <el-table-column label="学期第一学年" prop="firstHalfYear">
            </el-table-column>
            <el-table-column label="学期第二学年" prop="secondHalfYear">
            </el-table-column>
            <el-table-column label="季度" prop="season">
            </el-table-column>
            <!--            <el-table-column label="学期开始时间" prop="startDate">-->
            <!--            </el-table-column>-->
            <el-table-column label="周数" prop="weekCount">
            </el-table-column>
        </el-table>

        <el-dialog :visible.sync="dialogFormVisible" title="新增学期信息" width="35%">
            <el-form :label-width="formLabelWidth">
                <el-form-item label="学期第一学年" label-width="100px">
                    <el-input v-model="addSemester.addFirstHalfYear" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="学期第二学年" label-width="100px">
                    <el-input v-model="addSemester.addSecondHalfYear" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="季度" label-width="100px">
                    <el-select v-model="addSemester.addSeason" placeholder="请选择季度">
                        <el-option label="1" value="1"></el-option>
                        <el-option label="2" value="2"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="学期开始时间" label-width="100px">
                    <el-input v-model="addSemester.addStartDate" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="周数" label-width="100px">
                    <el-input v-model="addSemester.addWeekCount" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelAdd">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>

export default {
    name: "Semester",
    data() {
        const item = {
            semesterID: "1",
            firstHalfYear: "2023",
            secondHalfYear: "2024",
            season: "1",
            startDate: "2023-2-3",
            weekCount: "18",
        };
        return {
            // tableData: [],
            tableData: Array(10).fill(item),
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,

            chooseSemester: "",

            addSemester: {
                addFirstHalfYear: "",
                addSecondHalfYear: "",
                addSeason: "",
                addStartDate: "",
                addWeekCount: "",
            },

            // Semester:{
            semesterID: "",
            firstHalfYear: "",
            secondHalfYear: "",
            season: "",
            startDate: "",
            weekCount: "",
            // },

            formLabelWidth: '80px',
            dialogFormVisible: false,
            multipleSelection: [],

            headerBg: 'headerBg'
        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.request.get("/semester/all").then(res => {
                console.log(res)
                this.tableData = res.data
            })
        },
        resetDialog() {
            this.addSemester = {}
            this.load()
        },
        cancelAdd() {
            this.dialogFormVisible = false;
            this.resetDialog()
        },
        save() {
            // this.request.post("/user/users?username=" + this.username + "&password=" + this.password + "&role=" + this.role).then(res => {
            this.request.post("/semester/?semesterID=" + this.semesterID + "&firstHalfYear=" + this.firstHalfYear + "&secondHalfYear=" + this.secondHalfYear + "&season=" + this.season + "&startDate=" + this.startDate + "&weekCount=" + this.weekCount).then(res => {
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
        handleAdd() {
            this.dialogFormVisible = true;
            this.addSemester = {}
        },
        change() {
            this.$store.state.semester = this.chooseSemester
        },
    }
}
</script>

<style scoped>

</style>