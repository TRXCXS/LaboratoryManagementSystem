<template>
  <div>
<!--      <el-card>-->
<!--          <div style="display: flex;justify-content: space-around; margin: 10px 0">-->
<!--              <h5 style="margin-top: 6px;">桌子检索:</h5>-->
<!--              <el-input style="width: 200px" placeholder="请输桌子ID" suffix-icon="el-icon-star-off" class="ml-5"></el-input>-->
<!--              <el-button type="primary">搜索</el-button>-->
<!--          </div>-->
<!--      </el-card>-->

      <div style="margin: 10px 0">
          <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
<!--          <el-button type="danger">批量删除 <i class="el-icon-remove-outline"></i></el-button>-->
      </div>

      <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
          <el-table-column prop="desk_id" label="桌子ID">
          </el-table-column>
          <el-table-column prop="coordinate" label="位置坐标">
          </el-table-column>
<!--          <el-table-column prop="axis2" label="行">-->
<!--          </el-table-column>-->
<!--          <el-table-column prop="axis1" label="列">-->
<!--          </el-table-column>-->
          <el-table-column prop="capacity" label="容量">
          </el-table-column>
          <el-table-column prop="power" label="是否可充电">
          </el-table-column>
          <el-table-column label="操作"  align="center" width="250px">
              <template slot-scope="scope">
                  <el-button type="success" @click="linkToSeat(scope.row.desk_id)" >进入 <i class="el-icon-caret-right"></i></el-button>
                  <el-button type="warning" @click="handleUpdate(scope.row.desk_id)" style="margin-left: 5px">修改 <i class="el-icon-remove-outline"></i></el-button>
                  <el-button type="danger" @click="del(scope.row.desk_id)" style="margin-left: 5px">删除 <i class="el-icon-remove-outline"></i></el-button>
              </template>
          </el-table-column>
      </el-table>

      <el-dialog title="修改桌子信息" :visible.sync="dialogFormVisible1"  width="35%">
          <el-form :label-width="formLabelWidth">
              <el-form-item label="桌子ID">
                  <el-input v-model="desk_id" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="行">
                  <el-input v-model="axis2" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="列">
                  <el-input v-model="axis1" autocomplete="off" ></el-input>
              </el-form-item>
              <el-form-item label="容量">
                  <el-input v-model="capacity" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="可否充电">
                  <el-select v-model="power" placeholder="请选择是否可充电">
                      <el-option label="是" value="true"></el-option>
                      <el-option label="否" value="false"></el-option>
                  </el-select>
              </el-form-item>
              <el-form-item label="区域ID">
                  <el-input v-model="sec_id" autocomplete="off" :disabled="true"></el-input>
              </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible1 = false">取 消</el-button>
              <el-button type="primary" @click="save1">确 定</el-button>
          </div>
      </el-dialog>

      <el-dialog title="新增桌子" :visible.sync="dialogFormVisible2"  width="30%">
          <el-form :label-width="formLabelWidth">
              <el-form-item label="行">
                  <el-input v-model="axis2" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="列">
                  <el-input v-model="axis1" autocomplete="off" ></el-input>
              </el-form-item>
              <el-form-item label="容量">
                  <el-input v-model="capacity" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="可否充电">
                  <el-select v-model="power" placeholder="请选择是否可充电">
                      <el-option label="是" value="true"></el-option>
                      <el-option label="否" value="false"></el-option>
                  </el-select>
              </el-form-item>
              <el-form-item label="区域ID">
                  <el-input v-model="sec_id" autocomplete="off" :disabled="true"></el-input>
              </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible2 = false">取 消</el-button>
              <el-button type="primary" @click="save2">确 定</el-button>
          </div>
      </el-dialog>
  </div>
</template>

<script>
export default {
    name: "Desk",
    data() {
        return {
            tableData:[],
            collapseBtnClass: 'el-icon-s-fold',
            isCollapse: false,
            sideWidth: 200,
            logoTextShow: true,
            headerBg: 'headerBg',

            formLabelWidth: '80px',
            desk_id:"",
            axis2:"",
            axis1:"",
            power:true,
            sec_id:this.$route.query.id,
            capacity:"",
            dialogFormVisible1: false,
            dialogFormVisible2: false,
            multipleSelection: [],
        }
    },
    created() {
        this.load()
    },
    methods:{
        load(){
            let id = this.$route.query.id
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
        resetDialog(){

        },
        save1(){
            let temp = "/desk/?deskId="+this.desk_id+"&axis1="+this.axis1+"&axis2="+this.axis2+"&capacity="+this.capacity+"&power="+this.power+"&sectionId="+this.$route.query.id
            console.log(temp)
            this.request.put(temp).then(res =>{
                if (res) {
                    this.$message.success("修改成功")
                    this.dialogFormVisible1 = false
                    this.resetDialog()
                    this.load()
                } else {
                    this.$message.error("修改失败")
                }
            })
        },
        save2(){
            let temp = "/desk/create?axis1="+this.axis1+"&axis2="+this.axis2+"&capacity="+this.capacity+"&power="+this.power+"&sectionId="+this.$route.query.id
            this.request.post(temp).then(res =>{
                if (res) {
                    this.$message.success("新增成功")
                    this.dialogFormVisible2 = false
                    this.resetDialog()
                    this.load()
                } else {
                    this.$message.error("新增失败")
                }
            })
        },
        handleAdd(){
            this.dialogFormVisible2 = true;
        },
        handleUpdate(id){
            let sec_id = this.$route.query.id
            this.request.get("/desk/"+sec_id).then(res=>{
                console.log(res)
                console.log("wsadasdghejhnodiash")
                for(let i = 0;i<res.length;i++){
                    if (res[i].desk_id === id){
                        this.power = res[i].power
                        this.desk_id = res[i].desk_id
                        this.axis1 = res[i].axis1
                        this.axis2 = res[i].axis2
                        this.capacity = res[i].capacity
                        break
                    }
                }
            })
            this.dialogFormVisible1 = true;
        },
        del(id){
            console.log(id)
            this.$confirm('此操作将永久删除该条记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.delete("/desk/"+id).then(res=>{
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
        reset(){
            this.$message.success("已重置")
        },
        linkToSeat(id){
            this.$router.push({
                path:"/Management/section/desk/seat",
                query:{id:id}
            })
        },
    }
}
</script>

<style scoped>

</style>