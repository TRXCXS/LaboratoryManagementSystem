<template>
    <div>
        <!--        <el-card>-->
        <!--            <div style="display: flex;justify-content: space-around; margin: 10px 0">-->
        <!--                <h5 style="margin-top: 6px;">申诉搜索:</h5>-->
        <!--                <el-input style="width: 200px" placeholder="请输入姓名" suffix-icon="el-icon-search"></el-input>-->
        <!--                &lt;!&ndash;                <el-input style="width: 200px" placeholder="请输入邮箱" suffix-icon="el-icon-message" class="ml-5"></el-input>&ndash;&gt;-->
        <!--                &lt;!&ndash;                <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-position" class="ml-5"></el-input>&ndash;&gt;-->
        <!--                <el-button type="primary">搜索</el-button>-->
        <!--            </div>-->
        <!--        </el-card>-->


        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
            <el-table-column prop="appeal_id" label="申诉ID" >
            </el-table-column>
            <el-table-column prop="appeal_time" label="申诉时间" >
            </el-table-column>
<!--            <el-table-column prop="credrec_id" label="信用记录id">-->
<!--            </el-table-column>-->
            <el-table-column prop="description" label="描述">
            </el-table-column>
            <el-table-column prop="photo_id" label="图片">
            </el-table-column>
            <el-table-column prop="admin_scrutinized" label="状态">
<!--                <el-popover-->
<!--                    placement="top-start"-->
<!--                    title="申诉结果"-->
<!--                    width="200"-->
<!--                    trigger="hover"-->
<!--                    content="申诉成功，您的信用分已更改">-->
<!--                    <el-tag type="success" slot="reference">已处理</el-tag>-->
<!--                </el-popover>-->
<!--                    <el-tag type="info">未处理</el-tag>-->

                <el-popover>
                    <el-tag :type="this.handledState==='已处理' ? 'success' : 'info'" slot="reference" >{{handledState}}</el-tag>
                </el-popover>

            </el-table-column>
            <el-table-column label="操作"  align="center">
                <template slot-scope="scope">
<!--                    <el-button type="success">修改 <i class="el-icon-edit"></i></el-button>-->
                    <el-button type="danger" @click="cancel_appeal(scope.row.appeal_id)">撤销 <i class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
export default {
    name: "MyAppeal",
    data() {
        return {
            tableData: [],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',
            handledState:"",
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        }
    },
    created() {
        this.load()
    },
    methods:{
        load(){
            // console.log(user.user_id)
            this.request.get("/appeal/"+this.user.user_id).then(res=>{
                console.log(res)
                for(let i = 0;i<res.length;i++){
                    let origin_appeal_time = res[i].appeal_time
                    let date1 = new Date(origin_appeal_time);
                    let time1=date1.getFullYear() + '-' + ((date1.getMonth() + 1)<10?"0"+(date1.getMonth() + 1):(date1.getMonth() + 1) )+ '-' + (date1.getDate()<10?"0"+date1.getDate():date1.getDate() )+ ' ' + (date1.getHours()<10?"0"+date1.getHours():date1.getHours() )+ ':' + (date1.getMinutes()<10?"0"+date1.getMinutes():date1.getMinutes() )+ ':' + (date1.getSeconds()<10?"0"+date1.getSeconds():date1.getSeconds());
                    res[i].appeal_time = time1

                    if (res[i].admin_scrutinized){
                        this.handledState = "已处理"
                        this.tagForm = "success"
                    }else {
                        this.handledState = "未处理"
                        this.tagForm = "info"
                    }
                }
                this.tableData = res
            })
            console.log(this.user.user_id)
        },
        cancel_appeal(app_id){
            this.$confirm('确定取消申诉?', '提示', {
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
                    message: '已取消删除'
                });
            });
        },
    }
}
</script>

<style scoped>

</style>