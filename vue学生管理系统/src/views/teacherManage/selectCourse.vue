<template>
  <!-- 授权用户  :page-size="2"
        :page-sizes="[2,10]"-->
  <el-dialog title="课程评分" :visible.sync="visible" width="1000px" top="5vh" append-to-body class="edit-dialog">
      <!-- 课程列表 开始 -->
      <div>学生：{{stuname}}</div>
      <br>
      <el-table
              v-loading="table.tableLoading"
              :data="table.list"
              class="table"
              border
      >
          <el-table-column
                  label="课程序号"
                  min-width="80"
          >
              <template slot-scope="scope">
                  <span>{{ scope.row.kid }}</span>
              </template>
          </el-table-column>



          <el-table-column
                  label="课程名称"
                  min-width="80"
          >
              <template slot-scope="scope">
                  <span>{{ scope.row.name }}</span>
              </template>
          </el-table-column>

          <el-table-column
                  label="课程开始时间"
                  min-width="80"
          >
              <template slot-scope="scope">
                  <span>{{ parseTime(scope.row.create_time) }}</span>
              </template>
          </el-table-column>

          <el-table-column
                  label="课程结束时间"
                  min-width="80"
          >
              <template slot-scope="scope">
                  <span>{{ parseTime(scope.row.end_time) }}</span>
              </template>
          </el-table-column>

          <el-table-column
                  label="课程院系"
                  min-width="80"
          >
              <template slot-scope="scope">
                  <span>{{ scope.row.faculty }}</span>
              </template>
          </el-table-column>

          <el-table-column
                  label="课程周数"
                  min-width="80"
          >
              <template slot-scope="scope">
                  <span>{{ scope.row.weeks }}</span>
              </template>
          </el-table-column>


          <el-table-column
                  label="评分"
                  min-width="120"
          >
           <template slot-scope="scope">
                  <el-input
                          v-model="scope.row.score"
                          maxlength="120" type='number'
                          oninput="this.value = this.value && parseInt(this.value);this.value < 0 || this.value>100 && (this.value = 100)"

                  />
            </template>
          </el-table-column>

          <el-table-column
                  fixed="right"
                  label="操作"
          >

              <template slot-scope="scope">
                  <el-button
                          size="mini"
                          type="primary"
                          @click="submitScore(scope.$index, scope.row)"
                  >提交分数</el-button>

              </template>
          </el-table-column>

      </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancel">关闭</el-button>
    </div>
  </el-dialog>
</template>

<script>

export default {
  name: "ScoreCourse",
  data() {
    return {
      table: {
            tableLoading: true,
            list: [],
            multipleSelection: [],
            // start: 0,
            // limit: 10,
            total: 0,
            // currentPage: 1,
      },
      stuname: null,
      sid: null,
      // 遮罩层
      visible: false,
      loading: true,

      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 角色表格数据
      roleList: [],
      // 岗位关联角色数组
      prList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        appId:undefined,
        roleName: undefined,
        roleKey: undefined,
        status: undefined
      },
      // 表单参数
      form: {},


    };
  },
    created() {
        // this.getTableInfo();
    },

  methods: {

      //  提交分数
      submitScore(index, row) {
          if (row.score==null || row.score=='') {
              alert("请输入分数");
              return;
          }

          const params = {
              id: row.id,
              sid: this.sid,
              score:row.score,
              kid:row.kid,
          };

          this.ccs.post('/addStudentKScore', params).then(rs=>{
              if(rs.code!='ccs'){
                  this.$message({
                      message: "评分成功",
                      type: "success",
                  });
                  this.visible = false;
                  this.getTableInfo();
              }else{
                  this.$store.dispatch('LogMessage', "评分失败!")
              }
          }).catch(err=>{
              this.$store.dispatch('LogMessage', "评分失败!")
              console.log(err)
          })
      },

      parseTime(time, pattern) {
          if (arguments.length === 0 || !time) {
              return null
          }
          const format = pattern || '{y}-{m}-{d} {h}:{i}:{s}'
          let date
          if (typeof time === 'object') {
              date = time
          } else {
              if ((typeof time === 'string') && (/^[0-9]+$/.test(time))) {
                  time = parseInt(time)
              } else if (typeof time === 'string') {
                  time = time.replace(new RegExp(/-/gm), '/').replace('T', ' ').replace(new RegExp(/\.[\d]{3}/gm), '');
              }
              if ((typeof time === 'number') && (time.toString().length === 10)) {
                  time = time * 1000
              }
              date = new Date(time)
          }
          const formatObj = {
              y: date.getFullYear(),
              m: date.getMonth() + 1,
              d: date.getDate(),
              h: date.getHours(),
              i: date.getMinutes(),
              s: date.getSeconds(),
              a: date.getDay()
          }
          const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
              let value = formatObj[key]
              // Note: getDay() returns 0 on Sunday
              if (key === 'a') { return ['日', '一', '二', '三', '四', '五', '六'][value] }
              if (result.length > 0 && value < 10) {
                  value = '0' + value
              }
              return value || 0
          })
          return time_str
      },

      getSid(sid, stuname){
          this.stuname=stuname;
          this.sid=sid;
          this.loading = true;
          this.getTableInfo();
          this.visible = true;
      },

      getTableInfo() {
          this.table.tableLoading = true;
          const  params = {
              tid: sessionStorage.getItem("teacher_id"),
              sid: this.sid,

          }

          this.ccs.post('/getScoreCourseList', params).then(rs=>{
              if(rs.code!='ccs'&&rs.rows && Array.isArray(rs.rows)){
                  this.table.tableLoading = false;
                  this.table.list = rs.rows;
                  this.table.total = rs.total || 0;
              }else{
                  this.$store.dispatch('LogMessage', "查询成功!")
              }
          }).catch(err=>{
              this.$store.dispatch('LogMessage', "查询失败!")
              console.log(err)
          })

      },



    // 取消按钮
    cancel() {
      this.visible = false;
      // this.$emit('handleCancel');
    },



  }
};
</script>

