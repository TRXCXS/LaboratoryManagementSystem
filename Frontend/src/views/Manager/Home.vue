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
}
</script>

<style scoped>
    .el-carousel__item h3 {
        color: #475669;
        font-size: 14px;
        opacity: 0.75;
        line-height: 250px;
        margin: 0;
    }

    .el-carousel__item div {
        margin-top: -100px;
    }
</style>