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

        <el-card >
            <div style="display:flex;justify-content: space-around">
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

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
            <el-table-column prop="desk_id" label="桌子ID">
            </el-table-column>
<!--            <el-table-column prop="axis2" label="行">-->
<!--            </el-table-column>-->
<!--            <el-table-column prop="axis1" label="列">-->
<!--            </el-table-column>-->
            <el-table-column prop="coordinate" label="位置坐标">
            </el-table-column>
            <el-table-column prop="capacity" label="容量">
            </el-table-column>
            <el-table-column prop="power" label="是否可充电">
            </el-table-column>
            <el-table-column label="操作"  align="center" width="250px">
                <template slot-scope="scope">
                    <el-button type="success" @click="linkToSeat(scope.row.desk_id)" >进入 <i class="el-icon-caret-right"></i></el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
export default {
    name: "SelectDesk",
    data() {
        return {
            tableData:[],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',


            value1:'',
            startTime: '',
            endTime: '',


            buttons: [],

            receivedData:null,
            searchSeats:'',

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
    methods:{
        load(){
            this.startTime=this.$route.query.startTime
            this.endTime=this.$route.query.endTime
            let id = this.$route.query.id
            if(id === '1'){
                this.src = this.url_main;
                this.srcList = this.srcList_main1A;
            }else if (id === '2'){
                this.src = this.url_main;
                this.srcList = this.srcList_main1B;
            }else if (id === '3'){
                this.src = this.url_main;
                this.srcList = this.srcList_main1C;
            }else if (id === '4'){
                this.src = this.url_tai;
                this.srcList = this.srcList_tai;
            }else{
                this.src = this.url_qilin;
                this.srcList = this.srcList_qilin;
            }

            this.request.get("/desk/"+id).then(res=>{
                console.log(res)
                for(let i = 0;i<res.length;i++){
                    if (res[i].power){
                        res[i].power = "是"
                    }
                    else res[i].power = "否"
                }
                this.tableData = res
                for(let i = 0;i<res.length;i++){
                    this.tableData[i].coordinate = "( "+res[i].axis1+" , "+res[i].axis2+" )"
                }
            })
        },
        linkToSeat(id){
            this.$router.push({
                path:"/Reservation/SelectSeat",
                query:{
                    desk_id:id,
                    startTime:this.startTime,
                    endTime:this.endTime
                }
            })
        },
    }
}
</script>

<style scoped>

</style>