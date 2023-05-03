<template>
    <div class="wrapper">
        <h1 style="color: #fff;padding: 50px">请选择闸机</h1>
        <el-dropdown trigger="click">
        <span class="el-dropdown-link">
          请点击<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item >
                    <span style="text-decoration: none" @click="linkToEntryScanner">进门闸机</span>
                </el-dropdown-item>
                <el-dropdown-item >
                    <span style="text-decoration: none" @click="linkToExitScanner">出门闸机</span>
                </el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>

<script>
export default {
    name: "chooseScanner",
    data(){
        return{
            user_id:"",
            signIn: true,
            signOut: true,
            temporarilyPart: true,
            back: true,
            state:"",

            activeIndex: '1',
            activeIndex2: '1'

        }
    },
    methods:{
        linkToEntryScanner(){
            this.$router.push('/entryScanner')
        },
        linkToExitScanner(){
            this.$router.push('/exitScanner')
        },
        qiandao(){
            this.request.put("/reservation/entry?user_id="+this.user_id+"&entry_state="+this.state).then(res=>{
                console.log(res)
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
            this.request.put("/reservation/exit?user_id="+this.user_id+"&entry_state=AWAY").then(res=>{
                console.log(res)
            })
        },
        qiantui(){
            this.request.put("/reservation/entry?user_id="+this.user_id+"&entry_state=EXIT").then(res=>{
                console.log(res)
            })
        },
        fanchang(){
            this.request.put("/reservation/entry?user_id="+this.user_id+"&entry_state="+this.state).then(res=>{
                console.log(res)
            })
        },
        sendUser_id(){
            // console.log(this.user_id)
            this.request.get("/reservation/entry_state/"+this.user_id).then(res=>{
                console.log(res)
                if (res === "CHECKINABLE"){
                    this.signIn = false
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
  .el-dropdown-link {
      cursor: pointer;
      color: #fff;
  }
  .el-icon-arrow-down {
      font-size: 12px;
  }
  .demonstration {
      display: block;
      color: #8492a6;
      font-size: 14px;
      margin-bottom: 20px;
  }
</style>