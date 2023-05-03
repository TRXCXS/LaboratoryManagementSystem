<template>
    <div class="block">
        <span class="demonstration"></span>
        <el-carousel height="300px" >
            <el-carousel-item v-for="item in urlList" :key="item">
                <div>
                    <el-image :src="item" :preview-src-list="urlList" style="width: 100%;"></el-image>
                </div>
            </el-carousel-item>
        </el-carousel>

        <el-card style="margin-top: 10px">
            <div style="margin-bottom: 10px">常用功能</div>
            <el-button class="el__button" type="success" round @click="$router.push('/Reservation/SelectSection')">我要预约</el-button>
            <el-button class="el__button" type="primary" round @click="$router.push('/Reservation/CreditRecord')">我要申诉</el-button>
            <el-button class="el__button" type="danger"  round @click="$router.push('/Reservation/Report')">我要举报</el-button>
<!--            <el-button class="el__button" type="info"  round @click="$router.push('/scanner')">扫脸闸机</el-button>-->
        </el-card>

    </div>
</template>

<script>
export default {
    name: "Home",
    data(){
        return{
            urlList:["http://lib.scau.edu.cn/_upload/tpl/05/47/1351/template1351/img/slider/slider_bg08.jpg",
                    "http://lib.scau.edu.cn/_upload/tpl/05/47/1351/template1351/img/slider/slider_bg03.jpg"],
        }
    },
    created() {
        let user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : null
        console.log(user.access_token)
    }
}
</script>

<style scoped>
    /*.el-carousel__item h3 {*/
    /*    color: #475669;*/
    /*    font-size: 14px;*/
    /*    opacity: 0.75;*/
    /*    line-height: 500px;*/
    /*    margin: 0;*/
    /*}*/

    .el-carousel__item div {
        margin-top: -100px;
    }


    .el-carousel__item:nth-child(2n) {
        background-color: #99a9bf;
    }

    .el-carousel__item:nth-child(2n+1) {
        background-color: #d3dce6;
    }

    .el__button{
        width: 100px;
        height: 100px;
        font-size: 15px;
    }
</style>