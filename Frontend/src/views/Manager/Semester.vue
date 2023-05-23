<template>
    <div>
        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            <el-select v-model.number="chooseSemester" placeholder="请选择学期" style="padding-left: 10px" @change="change">
                <el-option
                    v-for="option in options"
                    :key="option.value"
                    :label="option.label"
                    :value="option.value"
                ></el-option>

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
                    <el-input v-model.number="addSemester.firstHalfYear" autocomplete="off"></el-input>
                </el-form-item>
<!--                <el-form-item label="学期ID" label-width="100px">-->
<!--                    <el-input v-model.number="addSemester.semesterID" autocomplete="off"></el-input>-->
<!--                </el-form-item>-->
                <el-form-item label="学期第二学年" label-width="100px">
                    <el-input v-model.number="addSemester.secondHalfYear" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="季度" label-width="100px">
                    <el-select v-model="addSemester.season" placeholder="请选择季度">
                        <el-option label="春季" value="SPRING"></el-option>
                        <el-option label="秋季" value="FALL"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="学期开始时间" label-width="100px">
<!--                    <el-input v-model="addSemester.startDate" autocomplete="off"></el-input>-->
                    <el-date-picker
                        v-model="addSemester.startDate"
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="选择日期时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="周数" label-width="100px">
<!--                    <el-input v-model="addSemester.weekCount" autocomplete="off"></el-input>-->
                    <el-input-number v-model="addSemester.weekCount" :min="1" :max="20" label="周数"></el-input-number>
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
        return {
            tableData: [],
            options: [],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,

            chooseSemester: "",

            addSemester: {
                semesterID: 1,
                firstHalfYear: "",
                secondHalfYear: "",
                season: "",
                startDate: "",
                weekCount: 18,
            },

            semesterID: "",
            firstHalfYear: "",
            secondHalfYear: "",
            season: "",
            startDate: "",
            weekCount: "",


            formLabelWidth: '80px',
            dialogFormVisible: false,
            multipleSelection: [],

            headerBg: 'headerBg',
        }
    },
    created() {
        this.load()
    },
    // 绑定监听事件
    mounted() {
        window.addEventListener('keydown', this.keyDown)
    },
    // 销毁事件
    destroyed() {
        window.removeEventListener('keydown', this.keyDown, false)
    },
    methods: {
        load() {
            this.request.get("/semester/all").then(res => {
                console.log(res)
                this.tableData = res.data
                this.options=[]
                for(let i = 0;i<res.data.length;i++){
                    let label =""
                    let value = ""
                    if (res.data[i].season === "FALL"){
                        label = res.data[i].firstHalfYear+"-"+res.data[i].secondHalfYear+"-2"
                        value = res.data[i].semesterID
                    }else {
                        label = res.data[i].firstHalfYear+"-"+res.data[i].secondHalfYear+"-1"
                        value = res.data[i].semesterID
                    }
                    this.options.push({label:label,value:value})
                }
            })
        },
        resetDialog() {
            this.addSemester.semesterID =0
            this.addSemester.firstHalfYear =""
            this.addSemester.secondHalfYear =""
            this.addSemester.season =""
            this.addSemester.startDate =""
            this.addSemester.weekCount =""
            this.load()
        },
        cancelAdd() {
            this.dialogFormVisible = false;
            this.resetDialog()
        },
        save() {
            console.log(this.addSemester)
            this.request.post("/semester",this.addSemester).then(res => {
                if (res) {
                    this.$message.success("添加成功")
                    this.dialogFormVisible = false
                    this.resetDialog()
                    this.load()
                } else {
                    this.$message.error("添加失败")
                }
            }).catch(() => {
                this.$message({
                    type: 'error',
                    message: '学期已存在！'
                });
            });
        },
        handleAdd() {
            this.dialogFormVisible = true;
            this.resetDialog()
        },
        change(value) {
            this.request.put("/semester/current",value).then(res => {
                console.log(res)
                this.$store.state.semester = value
            })
        },
        keyDown(e) {
            // 回车则执行登录方法 enter键的ASCII是13
            if (e.keyCode === 13) {
                this.save() // 需要执行的方法方法
            }
        }
    }
}
</script>

<style scoped>

</style>