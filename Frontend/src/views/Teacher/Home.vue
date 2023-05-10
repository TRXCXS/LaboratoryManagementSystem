<template>
    <div class="block">
        <span class="demonstration"></span>
        <!--        <el-carousel height="300px" >-->
        <!--            <el-carousel-item v-for="item in urlList" :key="item">-->
        <!--                <div>-->
        <!--                    <el-image :src="item" :preview-src-list="urlList" style="width: 100%;"></el-image>-->
        <!--                </div>-->
        <!--            </el-carousel-item>-->
        <!--        </el-carousel>-->

        <el-table
                :cell-class-name="setTextWeight"
                :data="tableData"
                :span-method="objectSpanMethod"
                class="no-header-only"
        >
            <el-table-column align="center" label="2022-2023-2 实验室排课表">
                <el-table-column
                        align="center"
                        lable=""
                        prop="day"
                ></el-table-column>
                <el-table-column
                        align="center"
                        label="实验室"
                        prop="room"
                ></el-table-column>
                <el-table-column
                        align="center"
                        label="机房"
                        prop="room_num"
                ></el-table-column>
                <el-table-column
                        align="center"
                        label="1-2"
                        prop="section1_2"
                ></el-table-column>
                <el-table-column
                        align="center"
                        label="3-5"
                        prop="section3_5"
                ></el-table-column>
                <el-table-column
                        align="center"
                        label="6-7"
                        prop="section6_7"
                ></el-table-column>
                <el-table-column
                        align="center"
                        label="8-9"
                        prop="section8_9"
                ></el-table-column>
                <el-table-column
                        align="center"
                        label="10-12"
                        prop="section10_12"
                ></el-table-column>
                <el-table-column
                        align="center"
                        label="13-15"
                        prop="section13_15"
                ></el-table-column>
            </el-table-column>
        </el-table>

        <!--        <el-card style="margin-top: 10px">-->
        <!--            <div style="margin-bottom: 10px">常用功能</div>-->
        <!--            <el-button class="el__button" type="success" round @click="$router.push('/Reservation/SelectSection')">我要预约</el-button>-->
        <!--            <el-button class="el__button" type="primary" round @click="$router.push('/Reservation/CreditRecord')">我要申诉</el-button>-->
        <!--            <el-button class="el__button" type="danger"  round @click="$router.push('/Reservation/Report')">我要举报</el-button>-->
        <!--        </el-card>-->

    </div>
</template>

<script>
export default {
    name: "Home",
    data() {
        return {
            urlList: ["http://lib.scau.edu.cn/_upload/tpl/05/47/1351/template1351/img/slider/slider_bg08.jpg",
                "http://lib.scau.edu.cn/_upload/tpl/05/47/1351/template1351/img/slider/slider_bg03.jpg"],

            // section的空格不能删！！用来区分每个单元格，全是一样的话他会自动合并
            tableData: [
                {
                    day: "星期一",
                    room: "软件实验室",
                    room_num: "732",
                    section1_2: " ",
                    section3_5: "课程：汇编语言，教师：张三，班级：21级软件工程，周次：5-12",
                    section6_7: "",
                    section8_9: "",
                    section10_12: "",
                    section13_15: "",
                },
                {
                    day: "星期一",
                    room: "软件实验室",
                    room_num: "734",
                    section1_2: "  ",
                    section3_5: "  ",
                    section6_7: "  ",
                    section8_9: "  ",
                    section10_12: "  ",
                    section13_15: "  ",
                },
                {
                    day: "星期一",
                    room: "软件实验室",
                    room_num: "736",
                    section1_2: "   ",
                    section3_5: "   ",
                    section6_7: "   ",
                    section8_9: "   ",
                    section10_12: "   ",
                    section13_15: "   ",
                },
                {
                    day: "星期一",
                    room: "软件实验室",
                    room_num: "738",
                    section1_2: "    ",
                    section3_5: "    ",
                    section6_7: "    ",
                    section8_9: "    ",
                    section10_12: "    ",
                    section13_15: "    ",
                },
                {
                    day: "星期一",
                    room: "软件实验室",
                    room_num: "735",
                    section1_2: "     ",
                    section3_5: "     ",
                    section6_7: "     ",
                    section8_9: "     ",
                    section10_12: "     ",
                    section13_15: "     ",
                },
                {
                    day: "星期一",
                    room: "软件实验室",
                    room_num: "532",
                    section1_2: "      ",
                    section3_5: "      ",
                    section6_7: "      ",
                    section8_9: "      ",
                    section10_12: "      ",
                    section13_15: "      ",
                },
                {
                    day: "星期一",
                    room: "软件实验室",
                    room_num: "536",
                    section1_2: "       ",
                    section3_5: "       ",
                    section6_7: "       ",
                    section8_9: "       ",
                    section10_12: "       ",
                    section13_15: "       ",
                },
                {
                    day: "星期一",
                    room: "软件实验室",
                    room_num: "537",
                    section1_2: "        ",
                    section3_5: "        ",
                    section6_7: "        ",
                    section8_9: "        ",
                    section10_12: "        ",
                    section13_15: "        ",
                },
                {
                    day: "星期一",
                    room: "软件实验室",
                    room_num: "535",
                    section1_2: "         ",
                    section3_5: "         ",
                    section6_7: "         ",
                    section8_9: "         ",
                    section10_12: "         ",
                    section13_15: "         ",
                },
                {
                    day: "星期一",
                    room: "计算机系统实验室",
                    room_num: "704-708",
                    section1_2: "          ",
                    section3_5: "          ",
                    section6_7: "          ",
                    section8_9: "          ",
                    section10_12: "          ",
                    section13_15: "          ",
                },
                {
                    day: "星期一",
                    room: "计算机硬件实验室",
                    room_num: "703-707",
                    section1_2: "           ",
                    section3_5: "           ",
                    section6_7: "           ",
                    section8_9: "           ",
                    section10_12: "           ",
                    section13_15: "           ",
                },
                {
                    day: "星期一",
                    room: "计算机系统实验室",
                    room_num: "801、803",
                    section1_2: "            ",
                    section3_5: "            ",
                    section6_7: "            ",
                    section8_9: "            ",
                    section10_12: "            ",
                    section13_15: "            ",
                },
                {
                    day: "星期一",
                    room: "物联网实验室",
                    room_num: "806、808",
                    section1_2: "             ",
                    section3_5: "             ",
                    section6_7: "             ",
                    section8_9: "             ",
                    section10_12: "             ",
                    section13_15: "             ",
                },
                {
                    day: "星期一",
                    room: "计算机网络实验室",
                    room_num: "832、834",
                    section1_2: "              ",
                    section3_5: "              ",
                    section6_7: "              ",
                    section8_9: "              ",
                    section10_12: "              ",
                    section13_15: "              ",
                },
            ],
            mergeObj: {}, // 用来记录需要合并行的下标
            mergeArr: ['day', 'room', 'room_num', 'section1_2', 'section3_5', 'section6_7', 'section8_9', 'section10_12', 'section13_15'] // 表格中的列名
        }
    },
    created() {
        let user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : null
        console.log(user.access_token)
    },
    mounted() {
        this.getSpanArr(this.tableData);
    },
    methods: {
        // getSpanArr方法
        getSpanArr(data) {
            this.mergeArr.forEach((key, index1) => {
                let count = 0; // 用来记录需要合并行的起始位置
                this.mergeObj[key] = []; // 记录每一列的合并信息
                data.forEach((item, index) => {
                    // index == 0表示数据为第一行，直接 push 一个 1
                    if (index === 0) {
                        this.mergeObj[key].push(1);
                    } else {
                        // 判断当前行是否与上一行其值相等 如果相等 在 count 记录的位置其值 +1 表示当前行需要合并 并push 一个 0 作为占位
                        if (item[key] === data[index - 1][key]) {
                            this.mergeObj[key][count] += 1;
                            this.mergeObj[key].push(0);
                        } else {
                            // 如果当前行和上一行其值不相等
                            count = index; // 记录当前位置
                            this.mergeObj[key].push(1); // 重新push 一个 1
                        }
                    }
                })
            })
        },
        // objectSpanMethod方法
        // 默认接受四个值 { 当前行的值, 当前列的值, 行的下标, 列的下标 }
        objectSpanMethod({row, column, rowIndex, columnIndex}) {
            // 判断列的属性
            if (this.mergeArr.indexOf(column.property) !== -1) {
                // 判断其值是不是为0
                if (this.mergeObj[column.property][rowIndex]) {
                    return [this.mergeObj[column.property][rowIndex], 1]
                } else {
                    // 如果为0则为需要合并的行
                    return [0, 0];
                }
            }
        },

        // 设置客户电量电费信息中零售交易电费加粗显示
        setTextWeight({rowIndex, columnIndex}) {
            if (rowIndex == 10) {
                return "text-set";
            }
        },

    },
}
</script>

<style scoped>
.el-carousel__item div {
    margin-top: -100px;
}

.el__button {
    width: 100px;
    height: 100px;
    font-size: 15px;
}
</style>