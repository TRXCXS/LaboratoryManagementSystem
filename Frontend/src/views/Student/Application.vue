<template>
    <div>
        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
            <el-table-column prop="app_time" label="申请时间" >
            </el-table-column>
            <el-table-column prop="app_semester" label="申请学期" >
            </el-table-column>
            <el-table-column prop="app_week" label="申请周次"  width="100px">
            </el-table-column>
            <el-table-column prop="app_section" label="申请节次" width="100px">
            </el-table-column>
            <el-table-column prop="app_labNum" label="申请实验室编号" width="150px">
            </el-table-column>
            <el-table-column prop="app_reason" label="申请原因" width="150px">
            </el-table-column>
            <el-table-column prop="admin_scrutinized" label="状态" width="100px">

                <el-popover>
                    <el-tag :type="this.handledState==='已处理' ? 'success' : 'info'" slot="reference" >{{handledState}}</el-tag>
                </el-popover>

            </el-table-column>
            <el-table-column label="操作"  align="center" width="250px">
                <template slot-scope="scope">
                    <el-button type="warning"  @click="handleUpdate(scope.row.desk_id)"  >修改 <i class="el-icon-edit"></i></el-button>
                    <el-button type="primary" @click="confirm_finishUsing(scope.row.appeal_id)">确认使用完毕 <i class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog title="填写借用信息" :visible.sync="dialogFormVisible"  width="32%">
            <el-form :label-width="formLabelWidth">
                <el-form-item label="申请学期">
<!--                    <el-input v-model="form.app_semester" autocomplete="off"></el-input>-->
                    <el-select v-model="form.app_semester" placeholder="请选择学期">
                        <el-option label="2022-2023-1" value="2022-2023-1"></el-option>
                        <el-option label="2022-2023-1" value="2022-2023-1"></el-option>
                        <el-option label="2022-2023-1" value="2022-2023-1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="申请学生">
                    <el-input v-model="form.app_name" autocomplete="off" disabled></el-input>
                </el-form-item>
                <el-form-item label="申请周次">
<!--                    <el-input v-model="form.app_week" autocomplete="off"></el-input>-->
                    <el-input-number v-model="form.app_week" @change="handleChange" label="选择周次" :min="1" :max="20"></el-input-number>
                </el-form-item>
                <el-form-item label="申请节次">
<!--                    <el-input v-model="form.app_week" autocomplete="off"></el-input>-->
                    <el-select v-model="form.app_section" placeholder="请选择节次">
                        <el-option label="1-2" value="2022-2023-1"></el-option>
                        <el-option label="3-5" value="2022-2023-1"></el-option>
                        <el-option label="6-7" value="2022-2023-1"></el-option>
                        <el-option label="8-9" value="2022-2023-1"></el-option>
                        <el-option label="10-12" value="2022-2023-1"></el-option>
                        <el-option label="13-15" value="2022-2023-1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="申请实验室编号">
                    <el-input v-model="form.app_labNum" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="申请原因">
                    <el-input type="textarea"  v-model="form.app_reason" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="closeDialog">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="修改申请信息" :visible.sync="dialogFormVisible1"  width="32%">
            <el-form :label-width="formLabelWidth">
                <el-form-item label="申请学期">
                    <!--                    <el-input v-model="form.app_semester" autocomplete="off"></el-input>-->
                    <el-select v-model="form.app_semester" placeholder="请选择学期">
                        <el-option label="2022-2023-1" value="2022-2023-1"></el-option>
                        <el-option label="2022-2023-1" value="2022-2023-1"></el-option>
                        <el-option label="2022-2023-1" value="2022-2023-1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="申请学生">
                    <el-input v-model="form.app_name" autocomplete="off" disabled></el-input>
                </el-form-item>
                <el-form-item label="申请周次">
                    <!--                    <el-input v-model="form.app_week" autocomplete="off"></el-input>-->
                    <el-input-number v-model="form.app_week" @change="handleChange" label="选择周次" :min="1" :max="20"></el-input-number>
                </el-form-item>
                <el-form-item label="申请节次">
                    <!--                    <el-input v-model="form.app_week" autocomplete="off"></el-input>-->
                    <el-select v-model="form.app_section" placeholder="请选择节次">
                        <el-option label="1-2" value="2022-2023-1"></el-option>
                        <el-option label="3-5" value="2022-2023-1"></el-option>
                        <el-option label="6-7" value="2022-2023-1"></el-option>
                        <el-option label="8-9" value="2022-2023-1"></el-option>
                        <el-option label="10-12" value="2022-2023-1"></el-option>
                        <el-option label="13-15" value="2022-2023-1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="申请实验室编号">
                    <el-input v-model="form.app_labNum" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="申请原因">
                    <el-input type="textarea" v-model="form.app_reason" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible1 = false">取 消</el-button>
                <el-button type="primary" @click="save1">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
export default {
    name: "MyAppeal",
    data() {
        const item = {
            app_time:"2023-4-10-15:20:20",
            app_semester:"2022-2023-2",
            app_week:"12",
            app_section:"5-6",
            app_labNum:"532",
            admin_scrutinized:"已处理",
            app_reason: '上海市普陀区金沙江路 1518 弄水水水水水水水水水水水水水'
        };
        return {
            // tableData: [],
            tableData: Array(10).fill(item),
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',
            handledState:"",
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},

            form:{
                app_time:"",
                app_reason:"",
                app_semester: "",
                app_name: "",
                app_week: "",
                app_section: "",
                app_labNum: "",
            },


            formLabelWidth: '80px',

            password: "",
            dialogFormVisible: false,
            dialogFormVisible1: false,
            multipleSelection: [],


        }
    },
    created() {
        this.load()
        this.form.app_name = this.user.username
    },
    methods:{
        load(){
            this.request.get("/appeal/"+this.user.user_id).then(res=>{
                console.log(res)
                for(let i = 0;i<res.length;i++){
                    let origin_appeal_time = res[i].appeal_time
                    let date1 = new Date(origin_appeal_time);
                    let time1=date1.getFullYear() + '-' + ((date1.getMonth() + 1)<10?"0"+(date1.getMonth() + 1):(date1.getMonth() + 1) )+ '-' + (date1.getDate()<10?"0"+date1.getDate():date1.getDate() )+ ' ' + (date1.getHours()<10?"0"+date1.getHours():date1.getHours() )+ ':' + (date1.getMinutes()<10?"0"+date1.getMinutes():date1.getMinutes() )+ ':' + (date1.getSeconds()<10?"0"+date1.getSeconds():date1.getSeconds());
                    res[i].appeal_time = time1

                    if (res[i].admin_scrutinized){
                        this.handledState = "通过"
                        this.tagForm = "success"
                    }else {
                        this.handledState = "未审核"
                        this.tagForm = "info"
                    }
                }
                this.tableData = res
            })
            console.log(this.user.user_id)
        },
        confirm_finishUsing(app_id){
            this.$confirm('确定使用完毕?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.delete("/appeal/cancel/"+app_id).then(res=>{
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
                    message: '已取消'
                });
            });
        },
        handleAdd(){
            this.form.app_name = this.user.username
            this.dialogFormVisible = true;
            // this.form = {}
        },
        save(){
            // this.request.post("/section/?name="+this.form.sec_name+"&length="+this.form.sec_length+"&width="+this.form.sec_width+"&library="+this.form.library+"&floor="+this.form.floor).then(res =>{
            //     if (res) {
            //         this.$message.success("添加区域成功")
            //         this.dialogFormVisible = false
            //         // this.resetDialog()
            //         this.load()
            //     } else {
            //         this.$message.error("添加区域失败")
            //     }
            // })
        },
        save1(){
            // let temp = "/desk/?deskId="+this.desk_id+"&axis1="+this.axis1+"&axis2="+this.axis2+"&capacity="+this.capacity+"&power="+this.power+"&sectionId="+this.$route.query.id
            // console.log(temp)
            // this.request.put(temp).then(res =>{
            //     if (res) {
            //         this.$message.success("修改成功")
            //         this.dialogFormVisible1 = false
            //         this.resetDialog()
            //         this.load()
            //     } else {
            //         this.$message.error("修改失败")
            //     }
            // })
        },
        handleChange(value){
            console.log(value)
        },
        closeDialog(){
            this.dialogFormVisible = false;
            this.form={}
        },
        handleUpdate(id){
            // let sec_id = this.$route.query.id
            // this.request.get("/desk/"+sec_id).then(res=>{
            //     console.log(res)
            //     for(let i = 0;i<res.length;i++){
            //         if (res[i].desk_id === id){
            //             this.power = res[i].power
            //             this.desk_id = res[i].desk_id
            //             this.axis1 = res[i].axis1
            //             this.axis2 = res[i].axis2
            //             this.capacity = res[i].capacity
            //             break
            //         }
            //     }
            // })
            this.dialogFormVisible1 = true;
        },
    }
}
</script>

<style scoped>

</style>
















<!--<template>-->
<!--    <div style="margin: 10px 0">-->
<!--&lt;!&ndash;        <el-card >&ndash;&gt;-->
<!--&lt;!&ndash;            <div style="display:flex;justify-content: space-around">&ndash;&gt;-->
<!--&lt;!&ndash;                <div class="parent">&ndash;&gt;-->
<!--&lt;!&ndash;                    <div>&ndash;&gt;-->
<!--&lt;!&ndash;                        <h5 style="float: left;margin-top: 6px;margin-right: 15px">区域检索:</h5>&ndash;&gt;-->
<!--&lt;!&ndash;                        <el-cascader :options="options" clearable @change="handleChange"></el-cascader>&ndash;&gt;-->
<!--&lt;!&ndash;                        <div style="margin: 10px 0">&ndash;&gt;-->
<!--&lt;!&ndash;                            <h5 style="float: left;margin-top: 6px;margin-right: 15px">预约时间:</h5>&ndash;&gt;-->
<!--&lt;!&ndash;                            <el-form style="float: left" :inline="true" :model="exportDateModel" :rules="rules">&ndash;&gt;-->
<!--&lt;!&ndash;                                <el-form-item  prop="exportDate">&ndash;&gt;-->
<!--&lt;!&ndash;                                    <el-date-picker&ndash;&gt;-->
<!--&lt;!&ndash;                                        class="mr-5"&ndash;&gt;-->
<!--&lt;!&ndash;                                        value-format="yyyy-MM-dd"&ndash;&gt;-->
<!--&lt;!&ndash;                                        v-model="exportDateModel.exportDate"&ndash;&gt;-->
<!--&lt;!&ndash;                                        type="date"&ndash;&gt;-->
<!--&lt;!&ndash;                                        placeholder="选择日期"&ndash;&gt;-->
<!--&lt;!&ndash;                                        :clearable = "false"&ndash;&gt;-->
<!--&lt;!&ndash;                                        :picker-options="pickerOptions"&ndash;&gt;-->
<!--&lt;!&ndash;                                        @change="chooseDate">&ndash;&gt;-->
<!--&lt;!&ndash;                                    </el-date-picker>&ndash;&gt;-->
<!--&lt;!&ndash;                                </el-form-item>&ndash;&gt;-->
<!--&lt;!&ndash;                                <el-form-item>&ndash;&gt;-->
<!--&lt;!&ndash;                                    <el-time-select&ndash;&gt;-->
<!--&lt;!&ndash;                                        class="mr-5"&ndash;&gt;-->
<!--&lt;!&ndash;                                        :clearable = "false"&ndash;&gt;-->
<!--&lt;!&ndash;                                        placeholder="起始时间"&ndash;&gt;-->
<!--&lt;!&ndash;                                        v-model="startTime"&ndash;&gt;-->
<!--&lt;!&ndash;                                        @change="chooseTime_start"&ndash;&gt;-->
<!--&lt;!&ndash;                                        :picker-options="{&ndash;&gt;-->
<!--&lt;!&ndash;                              start: '07:00',&ndash;&gt;-->
<!--&lt;!&ndash;                              step: '00:30',&ndash;&gt;-->
<!--&lt;!&ndash;                              end: '22:30'&ndash;&gt;-->
<!--&lt;!&ndash;                            }">&ndash;&gt;-->
<!--&lt;!&ndash;                                    </el-time-select>&ndash;&gt;-->
<!--&lt;!&ndash;                                </el-form-item>&ndash;&gt;-->
<!--&lt;!&ndash;                                <el-form-item>&ndash;&gt;-->
<!--&lt;!&ndash;                                    <el-time-select&ndash;&gt;-->
<!--&lt;!&ndash;                                        class="mr-5"&ndash;&gt;-->
<!--&lt;!&ndash;                                        :clearable = "false"&ndash;&gt;-->
<!--&lt;!&ndash;                                        placeholder="结束时间"&ndash;&gt;-->
<!--&lt;!&ndash;                                        v-model="endTime"&ndash;&gt;-->
<!--&lt;!&ndash;                                        @change="chooseTime_end"&ndash;&gt;-->
<!--&lt;!&ndash;                                        :picker-options="{&ndash;&gt;-->
<!--&lt;!&ndash;                              start: '07:00',&ndash;&gt;-->
<!--&lt;!&ndash;                              step: '00:30',&ndash;&gt;-->
<!--&lt;!&ndash;                              end: '22:30',&ndash;&gt;-->
<!--&lt;!&ndash;                              minTime: startTime&ndash;&gt;-->
<!--&lt;!&ndash;                            }">&ndash;&gt;-->
<!--&lt;!&ndash;                                    </el-time-select>&ndash;&gt;-->
<!--&lt;!&ndash;                                </el-form-item>&ndash;&gt;-->
<!--&lt;!&ndash;                            </el-form>&ndash;&gt;-->
<!--&lt;!&ndash;                        </div>&ndash;&gt;-->
<!--&lt;!&ndash;                    </div>&ndash;&gt;-->
<!--&lt;!&ndash;                </div>&ndash;&gt;-->


<!--&lt;!&ndash;                <div class="demo-image__preview">&ndash;&gt;-->
<!--&lt;!&ndash;                    <h5 style="float:left;margin-top: 40px;margin-left:50px;margin-right: 15px">查看布局和实景:</h5>&ndash;&gt;-->
<!--&lt;!&ndash;                    <el-image&ndash;&gt;-->
<!--&lt;!&ndash;                        style="width: 130px; height: 100px"&ndash;&gt;-->
<!--&lt;!&ndash;                        :src="src"&ndash;&gt;-->
<!--&lt;!&ndash;                        :preview-src-list="srcList">&ndash;&gt;-->
<!--&lt;!&ndash;                    </el-image>&ndash;&gt;-->
<!--&lt;!&ndash;                </div>&ndash;&gt;-->
<!--&lt;!&ndash;            </div>&ndash;&gt;-->
<!--&lt;!&ndash;        </el-card>&ndash;&gt;-->

<!--        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" style="margin-top: 20px">-->
<!--            <el-table-column prop="library" label="图书馆">-->
<!--            </el-table-column>-->
<!--            <el-table-column prop="floor" label="楼层">-->
<!--            </el-table-column>-->
<!--            <el-table-column prop="sec_id" label="区域ID" >-->
<!--            </el-table-column>-->
<!--            <el-table-column prop="name" label="区域名称">-->
<!--            </el-table-column>-->
<!--&lt;!&ndash;            <el-table-column prop="length" label="区域长度">&ndash;&gt;-->
<!--&lt;!&ndash;            </el-table-column>&ndash;&gt;-->
<!--&lt;!&ndash;            <el-table-column prop="width" label="区域宽度">&ndash;&gt;-->
<!--&lt;!&ndash;            </el-table-column>&ndash;&gt;-->
<!--            <el-table-column label="操作"  align="center">-->
<!--                <template slot-scope="scope">-->
<!--&lt;!&ndash;                    <el-button type="success"  @click="confirm_res">选座 <i class="el-icon-edit"></i></el-button>&ndash;&gt;-->
<!--                    <el-button type="success" @click="linkToDesk(scope.row.sec_id)" >进入 <i class="el-icon-caret-right"></i></el-button>-->
<!--                </template>-->
<!--            </el-table-column>-->
<!--        </el-table>-->
<!--    </div>-->
<!--</template>-->

<!--<script>-->


<!--import selectDesk from "@/views/Student/SelectDesk.vue";-->

<!--export default {-->
<!--    name: "Application",-->
<!--    data() {-->
<!--        const validatorDate = (rule, value, callback) => {-->
<!--            return value !== '' ? callback() : callback(new Error(rule.message));-->
<!--        };-->
<!--        return {-->
<!--            date_flag:"",-->
<!--            time_start_flag:"",-->
<!--            time_end_flag:"",-->
<!--            exportDateModel:{-->
<!--                exportDate:""-->
<!--            },-->
<!--            rules: {-->
<!--                exportDate: [{ required: true, message: '日期是必填字段', validator: validatorDate }]-->
<!--            },-->
<!--            pickerOptions: {-->
<!--                disabledDate(time) {-->
<!--                    return time.getTime() < Date.now() - 24 * 60 * 60 * 1000;-->
<!--                }-->
<!--            },-->

<!--            value1:'',-->
<!--            startTime: '',-->
<!--            endTime: '',-->
<!--            standardization_startTime: '',-->
<!--            standardization_endTime: '',-->


<!--            tableData: [],-->
<!--            collapseBtnClass: 'el-icon-s-fold',-->
<!--            isCollapse: false,-->
<!--            sideWidth: 200,-->
<!--            logoTextShow: true,-->
<!--            headerBg: 'headerBg',-->



<!--            receivedData:null,-->
<!--            searchSeats:'',-->
<!--            options: [{-->
<!--                value: '主图书馆',-->
<!--                label: '主图书馆',-->
<!--                // disabled: true,-->
<!--                children: [{-->
<!--                    value: '楼层',-->
<!--                    label: '楼层',-->
<!--                    children: [{-->
<!--                        value: '一楼',-->
<!--                        label: '一楼',-->
<!--                        children: [{-->
<!--                            value: 'section1A',-->
<!--                            label: '区域A（中文社科书库1A）'-->
<!--                        },{-->
<!--                            value: 'section1B',-->
<!--                            label: '区域B（中文社科书库1B）'-->
<!--                        },{-->
<!--                            value: 'section1C',-->
<!--                            label: '区域C（思政学习空间）'-->
<!--                        },]-->

<!--                    }, {-->
<!--                        value: '二楼',-->
<!--                        label: '二楼',-->

<!--                        children: [{-->
<!--                            value: 'section2A',-->
<!--                            label: '区域A（文学历史地理书库2A）'-->
<!--                        },{-->
<!--                            value: 'section2B',-->
<!--                            label: '区域B（中文艺术外文书库2B）'-->
<!--                        },{-->
<!--                            value: 'section2C',-->
<!--                            label: '区域C（读者自修室）'-->
<!--                        },{-->
<!--                            value: 'section2D',-->
<!--                            label: '区域D（外文过刊室）'-->
<!--                        },]-->

<!--                    },]-->
<!--                }]-->
<!--            }, {-->
<!--                value: '泰山分馆',-->
<!--                label: '泰山分馆',-->
<!--                children: [{-->
<!--                    value: '楼层',-->
<!--                    label: '楼层',-->
<!--                    children: [{-->
<!--                        value: '一楼',-->
<!--                        label: '一楼',-->
<!--                    }, ]-->
<!--                }]-->
<!--            }, {-->
<!--                value: '启林分馆',-->
<!--                label: '启林分馆',-->
<!--                children: [{-->
<!--                    value: 'storey',-->
<!--                    label: '楼层',-->
<!--                    children: [{-->
<!--                        value: 'theFirstFloor',-->
<!--                        label: '一楼',-->
<!--                        children: [{-->
<!--                            value: 'qi_section1A',-->
<!--                            label: '区域A'-->
<!--                        },{-->
<!--                            value: 'qi_section1B',-->
<!--                            label: '区域B'-->
<!--                        },{-->
<!--                            value: 'qi_section1C',-->
<!--                            label: '区域C'-->
<!--                        },]-->

<!--                    }, {-->
<!--                        value: 'theSecondFloor',-->
<!--                        label: '二楼',-->

<!--                        children: [{-->
<!--                            value: 'qi_sectionA',-->
<!--                            label: '区域A'-->
<!--                        },{-->
<!--                            value: 'qi_sectionB',-->
<!--                            label: '区域B'-->
<!--                        },{-->
<!--                            value: 'qi_sectionC',-->
<!--                            label: '区域C'-->
<!--                        },]-->

<!--                    },]-->
<!--                }]-->
<!--            }],-->

<!--            src:'https://p5.itc.cn/images01/20200729/0160e895f197404c82139aae65770792.jpeg',-->
<!--            srcList: [-->
<!--                'http://lib.scau.edu.cn/_upload/article/images/f8/f3/1dc7529740d8a8b6219228cd2952/c8f42997-8660-4eb4-951f-ea254fb8efd5.png',-->
<!--                'http://lib.scau.edu.cn/_upload/article/images/f8/f3/1dc7529740d8a8b6219228cd2952/07b08b17-1954-42e0-a8c3-fc70376d3479.png'-->
<!--            ],-->

<!--            url_main: 'https://p5.itc.cn/images01/20200729/0160e895f197404c82139aae65770792.jpeg',-->
<!--            srcList_main1A: [-->
<!--                'http://lib.scau.edu.cn/_upload/article/images/f8/f3/1dc7529740d8a8b6219228cd2952/c8f42997-8660-4eb4-951f-ea254fb8efd5.png',-->
<!--                require('../../assets/main_1A.png'),-->
<!--            ],-->
<!--            srcList_main1B: [-->
<!--                'http://lib.scau.edu.cn/_upload/article/images/f8/f3/1dc7529740d8a8b6219228cd2952/c8f42997-8660-4eb4-951f-ea254fb8efd5.png',-->
<!--                require('../../assets/main_1B.png'),-->
<!--            ],-->
<!--            srcList_main1C: [-->
<!--                'http://lib.scau.edu.cn/_upload/article/images/f8/f3/1dc7529740d8a8b6219228cd2952/c8f42997-8660-4eb4-951f-ea254fb8efd5.png',-->
<!--                require('../../assets/main_sizheng.png'),-->
<!--            ],-->
<!--            url_qilin: 'https://img2.baidu.com/it/u=112279909,3720965161&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500',-->
<!--            srcList_qilin: [-->
<!--                'https://img2.baidu.com/it/u=3313177448,2563101906&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=334',-->
<!--                'https://img0.baidu.com/it/u=3146844640,35044300&fm=253&fmt=auto&app=138&f=JPEG?w=670&h=446'-->
<!--            ],-->
<!--            url_tai: 'https://taojin-his.cdn.bcebos.com/a6efce1b9d16fdfa030a2badbf8f8c5494ee7b0b.jpg',-->
<!--            srcList_tai: [-->
<!--                require('../../assets/tai.png'),-->
<!--                'https://img2.baidu.com/it/u=847251344,1458907948&fm=253&fmt=auto&app=138&f=JPEG?w=333&h=500',-->
<!--                'https://img1.baidu.com/it/u=805139816,2531714913&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500'-->
<!--            ]-->

<!--        }-->
<!--    },-->
<!--    created() {-->
<!--        this.date_flag = ""-->
<!--        this.time_start_flag = ""-->
<!--        this.time_end_flag=""-->
<!--        this.load()-->
<!--    },-->
<!--    methods: {-->
<!--        load(){-->
<!--            this.request.get("/section/all").then(res=>{-->
<!--                console.log(res)-->
<!--                this.tableData = res-->
<!--            })-->
<!--        },-->
<!--        linkToDesk(id){-->
<!--            console.log(this.time_end_flag,this.time_start_flag,this.date_flag)-->
<!--            console.log(this.date_flag==="1",this.date_flag)-->
<!--            if (this.date_flag ==="1"&&this.time_start_flag==="1"&&this.time_end_flag==="1"){-->
<!--                this.$router.push({-->
<!--                    path:"/Reservation/SelectDesk",-->
<!--                    query:{-->
<!--                        id:id,-->
<!--                        startTime:this.standardization_startTime,-->
<!--                        endTime:this.standardization_endTime-->
<!--                    }-->
<!--                })-->
<!--            }else {-->
<!--                this.$message({-->
<!--                    message: '请正确填写预约时间',-->
<!--                    type: 'warning'-->
<!--                });-->

<!--            }-->
<!--        },-->
<!--        handleChange(value) {-->
<!--            console.log(value)-->
<!--            console.log("&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;")-->
<!--            this.request.get("/section/"+value[0]+"/"+value[2]).then(res=>{-->
<!--                console.log(res)-->
<!--                this.tableData = res-->
<!--            })-->
<!--            if(value[3]==='section1A'){-->
<!--                this.src = this.url_main;-->
<!--                this.srcList = this.srcList_main1A;-->
<!--                this.receivedData = value[3]-->
<!--            }else if (value[3]==='section1B'){-->
<!--                this.src = this.url_main;-->
<!--                this.srcList = this.srcList_main1B;-->
<!--            }else if (value[3]==='section1C'){-->
<!--                this.src = this.url_main;-->
<!--                this.srcList = this.srcList_main1C;-->
<!--            }else if (value[0]==='tai'){-->
<!--                this.src = this.url_tai;-->
<!--                this.srcList = this.srcList_tai;-->
<!--            }else if (value[3]==='qi_section1A'){-->
<!--                this.src = this.url_qilin;-->
<!--                this.srcList = this.srcList_qilin;-->
<!--            }else if (value[3]==='qi_section1B'){-->
<!--                this.src = this.url_qilin;-->
<!--                this.srcList = this.srcList_qilin;-->
<!--            }else if (value[3]==='qi_section1C'){-->
<!--                this.src = this.url_qilin;-->
<!--                this.srcList = this.srcList_qilin;-->
<!--            }else{-->
<!--                this.src = this.url_main;-->
<!--                this.srcList = this.srcList_main1A;-->
<!--            }-->
<!--        },-->

<!--        chooseDate(){-->
<!--            console.log(this.exportDateModel.exportDate)-->
<!--            this.date_flag ="1"-->
<!--        },-->
<!--        chooseTime_start(){-->
<!--            // this.startTime = this.exportDateModel.exportDate+" "+this.startTime+":00"-->
<!--            this.standardization_startTime = this.exportDateModel.exportDate+" "+this.startTime+":00"-->
<!--            console.log(this.startTime)-->
<!--            this.time_start_flag ="1"-->
<!--        },-->
<!--        chooseTime_end(){-->
<!--            // this.endTime = this.exportDateModel.exportDate+" "+this.endTime+":00"-->
<!--            this.standardization_endTime = this.exportDateModel.exportDate+" "+this.endTime+":00"-->
<!--            console.log(this.endTime)-->
<!--            this.time_end_flag = "1"-->
<!--        },-->

<!--        search(){-->
<!--            console.log(this.searchSeats);-->
<!--        },-->

<!--    },-->
<!--}-->
<!--</script>-->

<!--<style >-->
<!--    .parent{-->
<!--        display: flex;-->
<!--        justify-content: center;-->
<!--        align-items: center;-->
<!--    }-->
<!--</style>-->
