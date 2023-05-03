<template>
  <div>
      <el-card style="width: 500px">
          <el-form ref="form"  label-width="80px">
              <el-form-item prop="no" label="举报位置">
                  <el-input v-model="s_id" placeholder="请输入要举报的座位ID"></el-input>
              </el-form-item >

              <el-form-item prop="no" label="上传图片">

                  <el-upload
                      class="upload-demo"
                      ref="upload"
                      :action="api"
                      :on-preview="handlePreview"
                      :on-remove="handleRemove"
                      :auto-upload="false"
                      :file-list="fileList"
                      :headers="headers"
                      :limit="1"
                      name="photo"
                      :data="{user_id:this.user.user_id,seat_id:s_id,description:des}"
                      :on-exceed="handleExceed"
                      :on-change="handleChange"
                      :on-success="handleUploadSuccess"
                      list-type="picture">
                      <el-button size="small" type="primary">点击上传</el-button>
                      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                  </el-upload>

              </el-form-item>

              <el-form-item prop="no" label="描述">
                  <el-input type="textarea" v-model="des"></el-input>
              </el-form-item>
              <el-form-item>
                  <el-button type="primary" @click="onSubmit">立即举报</el-button>
                  <el-button @click="$router.push('/Reservation/home')">取消</el-button>
              </el-form-item>
          </el-form>
      </el-card>
  </div>
</template>

<script>

import Login from "@/views/Login.vue";

export default {
    name: "Report",
    data() {
        return {
            api:this.GLOBAL.BASE_URL+"/report/",
            s_id: '',
            des:'',
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            form: {},
            headers:{
                Authorization: "Bearer "+JSON.parse(localStorage.getItem("user")).access_token
            },
            file:{},
            fileList: [],
        }
    },
    created() {
        console.log(this.GLOBAL.BASE_URL)
        console.log(this.api)
    },

    methods: {
        onSubmit() {
            if (this.fileList.length === 0) {
                return this.$message.warning("请选取文件后再上传");
            }
            this.$refs.upload.submit()//重点
        },
        handleUploadSuccess(response){
            if(response){
                this.$message.success("举报成功")
                this.$router.push("/Reservation/home")
            }else {
                this.$message.error("举报失败，位置不存在或文件过大")
            }
        },
        handleRemove(file, fileList) {
            console.log(file);
            console.log(fileList);
        },
        handlePreview(file) {
            console.log(file);
        },
        handleExceed(){
            this.$message.warning("一次只能提交一张图片")
        },
        handleChange(file){
            if (file.status === "ready" ){
                this.fileList.push(file)
                console.log("sss")
                console.log(this.fileList)
            }

        }

    }
}
</script>

<style scoped>

</style>