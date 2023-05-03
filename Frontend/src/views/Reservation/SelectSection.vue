<template>
    <div style="margin: 10px 0">
        <el-card >
            <div style="display:flex;justify-content: space-around">
                <div class="parent">
                    <div>
                        <h5 style="float: left;margin-top: 6px;margin-right: 15px">区域检索:</h5>
                        <el-cascader :options="options" clearable @change="handleChange"></el-cascader>
                        <div style="margin: 10px 0">
                            <h5 style="float: left;margin-top: 6px;margin-right: 15px">预约时间:</h5>
                            <el-form style="float: left" :inline="true" :model="exportDateModel" :rules="rules">
                                <el-form-item  prop="exportDate">
                                    <el-date-picker
                                        class="mr-5"
                                        value-format="yyyy-MM-dd"
                                        v-model="exportDateModel.exportDate"
                                        type="date"
                                        placeholder="选择日期"
                                        :clearable = "false"
                                        :picker-options="pickerOptions"
                                        @change="chooseDate">
                                    </el-date-picker>
                                </el-form-item>
                                <el-form-item>
                                    <el-time-select
                                        class="mr-5"
                                        :clearable = "false"
                                        placeholder="起始时间"
                                        v-model="startTime"
                                        @change="chooseTime_start"
                                        :picker-options="{
                              start: '07:00',
                              step: '00:30',
                              end: '22:30'
                            }">
                                    </el-time-select>
                                </el-form-item>
                                <el-form-item>
                                    <el-time-select
                                        class="mr-5"
                                        :clearable = "false"
                                        placeholder="结束时间"
                                        v-model="endTime"
                                        @change="chooseTime_end"
                                        :picker-options="{
                              start: '07:00',
                              step: '00:30',
                              end: '22:30',
                              minTime: startTime
                            }">
                                    </el-time-select>
                                </el-form-item>
                            </el-form>
                        </div>
                    </div>
                </div>


                <div class="demo-image__preview">
                    <h5 style="float:left;margin-top: 40px;margin-left:50px;margin-right: 15px">查看布局和实景:</h5>
                    <el-image
                        style="width: 130px; height: 100px"
                        :src="src"
                        :preview-src-list="srcList">
                    </el-image>
                </div>
            </div>
        </el-card>

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" style="margin-top: 20px">
            <el-table-column prop="library" label="图书馆">
            </el-table-column>
            <el-table-column prop="floor" label="楼层">
            </el-table-column>
            <el-table-column prop="sec_id" label="区域ID" >
            </el-table-column>
            <el-table-column prop="name" label="区域名称">
            </el-table-column>
<!--            <el-table-column prop="length" label="区域长度">-->
<!--            </el-table-column>-->
<!--            <el-table-column prop="width" label="区域宽度">-->
<!--            </el-table-column>-->
            <el-table-column label="操作"  align="center">
                <template slot-scope="scope">
<!--                    <el-button type="success"  @click="confirm_res">选座 <i class="el-icon-edit"></i></el-button>-->
                    <el-button type="success" @click="linkToDesk(scope.row.sec_id)" >进入 <i class="el-icon-caret-right"></i></el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>


import selectDesk from "@/views/Reservation/SelectDesk.vue";

export default {
    name: "SelectSeats",
    data() {
        const validatorDate = (rule, value, callback) => {
            return value !== '' ? callback() : callback(new Error(rule.message));
        };
        return {
            date_flag:"",
            time_start_flag:"",
            time_end_flag:"",
            exportDateModel:{
                exportDate:""
            },
            rules: {
                exportDate: [{ required: true, message: '日期是必填字段', validator: validatorDate }]
            },
            pickerOptions: {
                disabledDate(time) {
                    return time.getTime() < Date.now() - 24 * 60 * 60 * 1000;
                }
            },

            value1:'',
            startTime: '',
            endTime: '',
            standardization_startTime: '',
            standardization_endTime: '',


            tableData: [],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',



            receivedData:null,
            searchSeats:'',
            options: [{
                value: '主图书馆',
                label: '主图书馆',
                // disabled: true,
                children: [{
                    value: '楼层',
                    label: '楼层',
                    children: [{
                        value: '一楼',
                        label: '一楼',
                        children: [{
                            value: 'section1A',
                            label: '区域A（中文社科书库1A）'
                        },{
                            value: 'section1B',
                            label: '区域B（中文社科书库1B）'
                        },{
                            value: 'section1C',
                            label: '区域C（思政学习空间）'
                        },]

                    }, {
                        value: '二楼',
                        label: '二楼',

                        children: [{
                            value: 'section2A',
                            label: '区域A（文学历史地理书库2A）'
                        },{
                            value: 'section2B',
                            label: '区域B（中文艺术外文书库2B）'
                        },{
                            value: 'section2C',
                            label: '区域C（读者自修室）'
                        },{
                            value: 'section2D',
                            label: '区域D（外文过刊室）'
                        },]

                    },]
                }]
            }, {
                value: '泰山分馆',
                label: '泰山分馆',
                children: [{
                    value: '楼层',
                    label: '楼层',
                    children: [{
                        value: '一楼',
                        label: '一楼',
                    }, ]
                }]
            }, {
                value: '启林分馆',
                label: '启林分馆',
                children: [{
                    value: 'storey',
                    label: '楼层',
                    children: [{
                        value: 'theFirstFloor',
                        label: '一楼',
                        children: [{
                            value: 'qi_section1A',
                            label: '区域A'
                        },{
                            value: 'qi_section1B',
                            label: '区域B'
                        },{
                            value: 'qi_section1C',
                            label: '区域C'
                        },]

                    }, {
                        value: 'theSecondFloor',
                        label: '二楼',

                        children: [{
                            value: 'qi_sectionA',
                            label: '区域A'
                        },{
                            value: 'qi_sectionB',
                            label: '区域B'
                        },{
                            value: 'qi_sectionC',
                            label: '区域C'
                        },]

                    },]
                }]
            }],

            src:'https://p5.itc.cn/images01/20200729/0160e895f197404c82139aae65770792.jpeg',
            srcList: [
                'http://lib.scau.edu.cn/_upload/article/images/f8/f3/1dc7529740d8a8b6219228cd2952/c8f42997-8660-4eb4-951f-ea254fb8efd5.png',
                'http://lib.scau.edu.cn/_upload/article/images/f8/f3/1dc7529740d8a8b6219228cd2952/07b08b17-1954-42e0-a8c3-fc70376d3479.png'
            ],

            url_main: 'https://p5.itc.cn/images01/20200729/0160e895f197404c82139aae65770792.jpeg',
            srcList_main1A: [
                'http://lib.scau.edu.cn/_upload/article/images/f8/f3/1dc7529740d8a8b6219228cd2952/c8f42997-8660-4eb4-951f-ea254fb8efd5.png',
                require('../../assets/main_1A.png'),
            ],
            srcList_main1B: [
                'http://lib.scau.edu.cn/_upload/article/images/f8/f3/1dc7529740d8a8b6219228cd2952/c8f42997-8660-4eb4-951f-ea254fb8efd5.png',
                require('../../assets/main_1B.png'),
            ],
            srcList_main1C: [
                'http://lib.scau.edu.cn/_upload/article/images/f8/f3/1dc7529740d8a8b6219228cd2952/c8f42997-8660-4eb4-951f-ea254fb8efd5.png',
                require('../../assets/main_sizheng.png'),
            ],
            url_qilin: 'https://img2.baidu.com/it/u=112279909,3720965161&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500',
            srcList_qilin: [
                'https://img2.baidu.com/it/u=3313177448,2563101906&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=334',
                'https://img0.baidu.com/it/u=3146844640,35044300&fm=253&fmt=auto&app=138&f=JPEG?w=670&h=446'
            ],
            url_tai: 'https://taojin-his.cdn.bcebos.com/a6efce1b9d16fdfa030a2badbf8f8c5494ee7b0b.jpg',
            srcList_tai: [
                require('../../assets/tai.png'),
                'https://img2.baidu.com/it/u=847251344,1458907948&fm=253&fmt=auto&app=138&f=JPEG?w=333&h=500',
                'https://img1.baidu.com/it/u=805139816,2531714913&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500'
            ]

        }
    },
    created() {
        this.date_flag = ""
        this.time_start_flag = ""
        this.time_end_flag=""
        this.load()
    },
    methods: {
        load(){
            this.request.get("/section/all").then(res=>{
                console.log(res)
                this.tableData = res
            })
        },
        linkToDesk(id){
            console.log(this.time_end_flag,this.time_start_flag,this.date_flag)
            console.log(this.date_flag==="1",this.date_flag)
            if (this.date_flag ==="1"&&this.time_start_flag==="1"&&this.time_end_flag==="1"){
                this.$router.push({
                    path:"/Reservation/SelectDesk",
                    query:{
                        id:id,
                        startTime:this.standardization_startTime,
                        endTime:this.standardization_endTime
                    }
                })
            }else {
                this.$message({
                    message: '请正确填写预约时间',
                    type: 'warning'
                });

            }
        },
        handleChange(value) {
            console.log(value)
            console.log("--------------------")
            this.request.get("/section/"+value[0]+"/"+value[2]).then(res=>{
                console.log(res)
                this.tableData = res
            })
            if(value[3]==='section1A'){
                this.src = this.url_main;
                this.srcList = this.srcList_main1A;
                this.receivedData = value[3]
            }else if (value[3]==='section1B'){
                this.src = this.url_main;
                this.srcList = this.srcList_main1B;
            }else if (value[3]==='section1C'){
                this.src = this.url_main;
                this.srcList = this.srcList_main1C;
            }else if (value[0]==='tai'){
                this.src = this.url_tai;
                this.srcList = this.srcList_tai;
            }else if (value[3]==='qi_section1A'){
                this.src = this.url_qilin;
                this.srcList = this.srcList_qilin;
            }else if (value[3]==='qi_section1B'){
                this.src = this.url_qilin;
                this.srcList = this.srcList_qilin;
            }else if (value[3]==='qi_section1C'){
                this.src = this.url_qilin;
                this.srcList = this.srcList_qilin;
            }else{
                this.src = this.url_main;
                this.srcList = this.srcList_main1A;
            }
        },

        chooseDate(){
            console.log(this.exportDateModel.exportDate)
            this.date_flag ="1"
        },
        chooseTime_start(){
            // this.startTime = this.exportDateModel.exportDate+" "+this.startTime+":00"
            this.standardization_startTime = this.exportDateModel.exportDate+" "+this.startTime+":00"
            console.log(this.startTime)
            this.time_start_flag ="1"
        },
        chooseTime_end(){
            // this.endTime = this.exportDateModel.exportDate+" "+this.endTime+":00"
            this.standardization_endTime = this.exportDateModel.exportDate+" "+this.endTime+":00"
            console.log(this.endTime)
            this.time_end_flag = "1"
        },

        search(){
            console.log(this.searchSeats);
        },

    },
}
</script>

<style >
    .el-row {
        margin-bottom: 20px;
        &:last-child {
            margin-bottom: 0;
        }
    }
    .el-col {
        border-radius: 4px;
    }
    .bg-purple-dark {
        background: #99a9bf;
    }
    .bg-purple {
        background: #d3dce6;
    }
    .bg-purple-light {
        background: #e5e9f2;
    }
    .grid-content {
        border-radius: 4px;
        min-height: 36px;
    }
    .row-bg {
        padding: 10px 0;
        background-color: #f9fafc;
    }

    .parent{
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .container {
        display: grid;
        grid-template-columns: repeat(7, 1fr);
        grid-template-rows: repeat(8, 100px);
        grid-gap: 10px;
    }

    .item {
        background-color: #ddd;
        padding: 20px;
        opacity: 50%;
    }

    .background {
        background-image: url("../../../src/assets/tai.jpg");
        background-size: auto;
        background-repeat: no-repeat;
        background-position: top,center;
        height: 2300px;
    }



</style>