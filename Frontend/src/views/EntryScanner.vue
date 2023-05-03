<template>
  <div class="wrapper">
      <h1 style="color: #fff;padding: 50px">进门扫脸闸机</h1>
      <el-input style="width: 200px" placeholder="请输入user_id" suffix-icon="el-icon-search" v-model="user_id"></el-input>
      <el-button class="ml-5" type="primary" @click="sendUser_id">send</el-button>
      <div style="padding-top: 50px">
          <el-button type="primary" :disabled="signIn" @click="qiandao">签到</el-button>
          <el-button class="ml-5" type="primary" :disabled="signOut" @click="qiantui">签退</el-button>
          <el-button class="ml-5" type="primary" :disabled="temporarilyPart" @click="zanli">暂离</el-button>
          <el-button class="ml-5" type="primary" :disabled="back" @click="fanchang">返场</el-button>
      </div>
      <div style="padding-top: 50px">
          <el-button type="primary" @click="$router.push('/chooseScanner')">返回选择闸机</el-button>
      </div>
  </div>
</template>

<script>
export default {
    name: "entryScanner",
    data(){
        return{
            user_id:"",
            signIn: true,
            signOut: true,
            temporarilyPart: true,
            back: true,
            state:""

        }
    },
    methods:{
        qiandao(){
            this.request.put("/reservation/entry?user_id="+this.user_id+"&entry_state="+this.state).then(res=>{
                console.log(res)
                this.$message.success("签到成功")
                if (res === "RETURNABLE"){
                    this.temporarilyPart = true
                    this.signOut = true
                    this.signIn = true
                    this.back = false
                    this.state = "RETURNABLE"
                }
            })
        },
        zanli(){
            this.$message.success("暂离成功")
            this.request.put("/reservation/exit?user_id="+this.user_id+"&exit_state=AWAY").then(res=>{
                console.log(res)
            })
        },
        qiantui(){
            this.$message.success("签退成功")
            this.request.put("/reservation/exit?user_id="+this.user_id+"&exit_state=EXIT").then(res=>{
                console.log(res)
            })
        },
        fanchang(){
            this.$message.success("返场成功")
            this.request.put("/reservation/entry?user_id="+this.user_id+"&entry_state="+this.state).then(res=>{
                console.log(res)
            })
        },
        sendUser_id(){
            this.request.get("/reservation/entry_state/"+this.user_id).then(res=>{
                console.log(res)
                if (res === "CHECKINABLE"){
                    this.signIn = false
                    this.signOut = true
                    this.temporarilyPart = true
                    this.back = true
                    this.state = "CHECKINABLE"
                }else if (res === "LEAVABLE"){
                    this.temporarilyPart = false
                    this.signOut = false
                    this.signIn = true
                    this.back = true
                    this.state = "LEAVABLE"
                }else if (res === "RETURNABLE"){
                    this.temporarilyPart = true
                    this.signOut = true
                    this.signIn = true
                    this.back = false
                    this.state = "RETURNABLE"
                }else if (res === "ONLYEXITABLE"){
                    this.temporarilyPart = true
                    this.signOut = false
                    this.signIn = true
                    this.back = true
                    this.state = "LEAVABLE"
                }else if (res === "OTHER"){
                    this.temporarilyPart = true
                    this.signOut = true
                    this.signIn = true
                    this.back = true
                    this.state = "OTHER"
                    this.$message.warning("请走人工通道")
                }
            })
        }
    }
}
</script>

<style>
.wrapper {
    height: 100vh;
    background-image: linear-gradient(to bottom right, #208c28, #0e4f93);
    overflow: hidden;
    text-align: center;
}
</style>