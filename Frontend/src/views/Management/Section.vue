<template>
    <div>
        <div style="margin: 10px 0">
            <el-card>
                <div style="display:flex;justify-content: space-around">
                    <div class="parent">
                        <div>
                            <h5 style="float: left;margin-top: 6px;margin-right: 15px">区域检索:</h5>
                            <el-cascader :options="options" clearable @change="handleChange"></el-cascader>
                        </div>
                    </div>
<!--                    <div class="parent">-->
<!--                        <div>-->
<!--                            <h5 style="float:left;margin-top: 6px;margin-left:50px;margin-right: 15px">区域id:</h5>-->
<!--                            <el-input style="width: 200px" placeholder="请输入区域ID" suffix-icon="el-icon-search" v-model="searchSeats"></el-input>-->
<!--                            <el-button class="ml-5" type="primary" @click="search">搜索</el-button>-->
<!--                        </div>-->
<!--                    </div>-->


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
        </div>

        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增区域 <i class="el-icon-circle-plus-outline"></i></el-button>
<!--            <el-button type="danger">批量删除 <i class="el-icon-remove-outline"></i></el-button>-->
<!--            <el-button type="primary">导入 <i class="el-icon-bottom"></i></el-button>-->
<!--            <el-button type="primary">导出 <i class="el-icon-top"></i></el-button>-->
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
            <el-table-column prop="library" label="图书馆">
            </el-table-column>
            <el-table-column prop="floor" label="楼层">
            </el-table-column>
            <el-table-column prop="sec_id" label="区域ID" >
            </el-table-column>
            <el-table-column prop="name" label="区域名称">
            </el-table-column>
            <el-table-column label="操作"  width="200" align="center">
                <template slot-scope="scope">
                    <el-button type="success" @click="linkToDesk(scope.row.sec_id)" >进入 <i class="el-icon-caret-right"></i></el-button>
                    <el-button type="danger" @click="del(scope.row.sec_id)">删除 <i class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog title="区域信息" :visible.sync="dialogFormVisible"  width="32%">
            <el-form :label-width="formLabelWidth">
                <el-form-item label="区域名称">
                    <el-input v-model="form.sec_name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="区域长度">
                    <el-input v-model="form.sec_length" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="区域宽度">
                    <el-input v-model="form.sec_width" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="区域所属图书馆">
                    <el-input v-model="form.library" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="区域楼层">
                    <el-input v-model="form.floor" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>


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
    name: "Section",
    data() {

        // const item = {
        //     library: '泰山分馆',
        //     floor: '一楼',
        //     name: '阅览室',
        //     sec_id:"4"
        // };
        return {
            searchSeats:'',

            tableData: [],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',

            formLabelWidth: '80px',

            password: "",
            dialogFormVisible: false,
            multipleSelection: [],
            form:{
                sec_name: "",
                sec_length: "",
                sec_width: "",
                library: "",
                floor: "",
            },

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
        this.load()
    },
    methods: {
        load(){
            this.request.get("/section/all").then(res=>{
                console.log(res)
                this.tableData = res
            })
        },
        save(){
            this.request.post("/section/?name="+this.form.sec_name+"&length="+this.form.sec_length+"&width="+this.form.sec_width+"&library="+this.form.library+"&floor="+this.form.floor).then(res =>{
                if (res) {
                    this.$message.success("添加区域成功")
                    this.dialogFormVisible = false
                    // this.resetDialog()
                    this.load()
                } else {
                    this.$message.error("添加区域失败")
                }
            })
        },
        handleAdd(){
            this.dialogFormVisible = true;
            this.form = {}
        },
        linkToDesk(id){
            this.$router.push({
                path:"/Management/section/desk",
                query:{id:id}
            })
        },

        handleChange(value) {
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

        search(){
            console.log(this.searchSeats);
        },

        del(id){
            console.log(id)
            this.$confirm('此操作将永久删除该条记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.delete("/section/"+id).then(res=>{
                    if (res) {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        this.load()
                    } else {
                        this.$message.error("删除失败")
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
    .parent{
        display: flex;
        justify-content: center;
        align-items: center;
    }
</style>