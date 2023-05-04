<template>
  <el-container>
    <el-main>
      <el-form
        ref="searchForm"
        :model="searchForm"
        label-width="120px"
        class="search-form"
      >
        <el-row :gutter="20">
          <el-col :span="18">
            <el-row class="param-row">
              <el-col :span="8">
                <el-form-item
                  label="院系"
                  prop="faculty"
                >
                  <el-input
                          placeholder="例如：计算机学院"
                          v-model="searchForm.faculty"
                    maxlength="60"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <!--  <el-row class="param-row">

            </el-row> -->
          </el-col>
          <el-col :span="6">
            <el-row
              type="flex"
              justify="center"
            >
              <el-button
                type="primary"
                @click="handleSearch"
              >查询</el-button>
              <el-button @click="handleSearchClear">清空</el-button>
            </el-row>
          </el-col>
        </el-row>
      </el-form>
      <div class="content">
        <el-alert
          title
          type="info"
          class="alert"
          :closable="false"
        >
          <div class="left">
            功课管理
          </div>
          <div class="right">
            <el-button
              size="mini"
              type="primary"
              @click="handleAdd"
            >
              <i class="el-icon-plus" />
              新增
            </el-button>
            <!-- <el-button
              :loading="downloadLoading"
              size="mini"
              type="primary"
              @click="handleDownload"
            >
              <i class="el-icon-download" />

              导出
            </el-button> -->
          </div>
        </el-alert>
        <!-- 列表 开始 -->
          <el-table
                  v-loading="table.tableLoading"
                  :data="table.list"
                  class="table"
                  border
          >
              <el-table-column
                      label="功课序号"
                      min-width="80"
                      v-if="false"
              >
                  <template slot-scope="scope">
                      <span>{{ scope.row.id }}</span>
                  </template>
              </el-table-column>

              <el-table-column
                      label="星期几"
                      min-width="80"
              >
                  <template slot-scope="scope">
                      <span>{{ parseWeekday(scope.row.weekday) }}</span>
                  </template>
              </el-table-column>


              <el-table-column
                      label="上午8-9点"
                      min-width="80"
              >
                  <template slot-scope="scope">
                      <span>{{ scope.row.am8 }}</span>
                  </template>
              </el-table-column>

              <el-table-column
                      label="上午10-11点"
                      min-width="80"
              >
                  <template slot-scope="scope">
                      <span>{{ scope.row.am10 }}</span>
                  </template>
              </el-table-column>

              <el-table-column
                      label="下午2-3点"
                      min-width="80"
              >
                  <template slot-scope="scope">
                      <span>{{ scope.row.pm2}}</span>
                  </template>
              </el-table-column>

              <el-table-column
                      label="下午4-5点"
                      min-width="80"
              >
                  <template slot-scope="scope">
                      <span>{{ scope.row.pm4 }}</span>
                  </template>
              </el-table-column>

              <el-table-column
                      label="功课院系"
                      min-width="80"
              >
                  <template slot-scope="scope">
                      <span>{{ scope.row.faculty }}</span>
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
                              @click="handleEdit(scope.$index, scope.row)"
                      >编辑</el-button>

                      <el-button
                              size="mini"
                              type="danger"
                              @click="handleRemove(scope.$index, scope.row)"
                      >删除</el-button>
                  </template>

              </el-table-column>
          </el-table>
        <el-pagination
          class="pagination"
          background
          :current-page.sync="table.currentPage"
          :page-sizes="[10, 20, 30, 50, 100, 200]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="table.total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>

      <!-- 新增/修改 弹框开始 -->
      <el-dialog
        v-if="editAddDialog.visible"
        :title="editAddDialog.title"
        :visible.sync="editAddDialog.visible"
        width="50%"
      >
        <el-form
          ref="editAddForm"
          label-position="left"
          label-width="120px"
          :model="editAddForm"
        >

          <el-form-item
                    label="星期几"
                    prop="name"
            >
              <el-select v-model="editAddForm.weekday" placeholder="请选择">
                  <el-option
                          v-for="item in options"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                  </el-option>
              </el-select>
            </el-form-item>

          <el-form-item
            label="上午8-10点功课"
            prop=""
          >
            <el-input
              v-model="editAddForm.am8"
              maxlength="20"
              style="width:60%"
              placeholder="例如：高等数学"
            />
          </el-form-item>

            <el-form-item
                    label="上午10-11点功课"
                    prop=""
            >
                <el-input
                        v-model="editAddForm.am10"
                        maxlength="20"
                        style="width:60%"
                        placeholder="例如：高等数学"
                />
            </el-form-item>

            <el-form-item
                    label="上午2-3点功课"
                    prop=""
            >
                <el-input
                        v-model="editAddForm.pm2"
                        maxlength="20"
                        style="width:60%"
                        placeholder="例如：高等数学"
                />
            </el-form-item>

            <el-form-item
                    label="上午4-5点功课"
                    prop=""
            >
                <el-input
                        v-model="editAddForm.pm4"
                        maxlength="20"
                        style="width:60%"
                        placeholder="例如：高等数学"
                />
            </el-form-item>

          <el-form-item
            label="功课院系"
            prop=""
          >
            <el-input
              v-model="editAddForm.faculty"
              maxlength="355"
              style="width:60%"
              placeholder="例如：计算机学院"
            />
          </el-form-item>

        </el-form>

        <div
          slot="footer"
          class="dialog-footer"
        >
          <el-row
            type="flex"
            justify="center"
          >
            <el-button @click="editAddDialog.visible = false">取 消</el-button>
            <el-button
              type="primary"
              @click="submitAddEdit"
            >保 存</el-button>
          </el-row>
        </div>
      </el-dialog>


    </el-main>
  </el-container>
</template>
<script>
// import {
//   getTableInfo,
//   removeRow,
//   addRole,
//   editRole,
//   rowDtl,
//   exportMember,
//   getRightsTableInfo,
//   saveGrant,
// } from "@/api/userMemberGrade";
// import Upload from "@/components/Upload";
// import { parseTime } from "@/utils";

export default {
  // 加载组件
  // components: {
  //   Upload,
  // },

  // name: 'HotProduct',
  data() {
    return {
        options: [{
            value: '1',
            label: '星期一'
        }, {
            value: '2',
            label: '星期二'
        }, {
            value: '3',
            label: '星期三'
        }, {
            value: '4',
            label: '星期四'
        }, {
            value: '5',
            label: '星期五'
        }, {
            value: '6',
            label: '星期六'
        }, {
            value: '7',
            label: '星期日'
        }

        ],

      faculty:'',
            //上传过的文件
      uploadFileList:[{url:'https://img-blog.csdnimg.cn/f358b00e2d5f44eca9c424864bd943cf.png'}],
      // 初始化旧的所选数据集合
      oldSelectedList: [],
      // 查看等级线下的权益详情

      detail: [],
      dialogTableVisible: false,

      allImageDisable: false,
      // 上限
      logoUploadLimit: 1,
      // 图片列表
      logofilelist: [], // 已upload
      logolist: [], // 未upload

      options_lvl_type: [
        {
          value: "",
          label: "--请选择--",
        },
        {
          value: "0",
          label: "司龄",
        },
        {
          value: "1",
          label: "活跃值",
        },
      ],
      options_limit_type: [
        {
          value: "",
          label: "--请选择--",
        },
        {
          value: "0",
          label: "年限",
        },
        {
          value: "1",
          label: "分值",
        },
      ],
      grantDialog: {
        visible: false,
      },
      grantTable: {
        tableLoading: false,
        list: [],
        multipleSelection: [],
        lvl_id: "",
        u_id: "",
        cycle_type: "",
        equity_cycle: "",
        equity_nm: "",
        equity_source: "",
        equity_type: "",
        img_url: "",
      },
      table: {
        tableLoading: true,
        list: [],
        multipleSelection: [],
        start: 0,
        limit: 10,
        total: 0,
        currentPage: 1,
      },
      searchForm: {
        faculty:'',
        weekday:''
      },
      dialog02: {
        title: "",
        type: "",
        user_id: "",
        dialogFormVisible: false,
      },

      editAddForm: {
          id:'',    // 功课id
          am2:'',   //功课名称
          am8:'',   //
          weekday:'',  //
          pm2:'', //
          pm4:'',  //
          faculty:''//院系
      },
      //商品分类数组
      typeList:[],
      editAddDialog: {
        title: "",
        type: "",
        visible: false,
      },
      dialogRules: {
        name: [
          { required: true, message: "请输入医生名称", trigger: "blur" },
        ],
        desc: [
          { required: true, message: "请输入医生详情", trigger: "blur" },
        ],
        // img_id: [
        //   { required: true, message: "请上传商品图片", trigger: "blur" },
        // ],
        //   details_img_id: [
        //   { required: true, message: "请上传商品图片", trigger: "blur" },
        // ],
      },
      productDialog: {
        visible: false,
      },
      downloadLoading: false,
      roles: [],
      uoloadFileListCcs:[]
    };
  },
  created() {
    this.getTableInfo();

  },

  // 加载方法初始化
  methods: {
      parseWeekday(val) {
         if (val == '1') {
             return "星期一"
         } else if (val == '2') {
             return "星期二"
         } else if (val == '3') {
             return "星期三"
         }  else if (val == '4') {
             return "星期四"
         }  else if (val == '5') {
             return "星期五"
         }  else if (val == '6') {
             return "星期六"
         }  else if (val == '7') {
             return "星期日"
         }
         return ''
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
      getTeacher() {
          const  params = {
              tid: sessionStorage.getItem("teacher_id"),
          }

          this.ccs.post('/getTeacher', params).then(rs=>{
              if(rs.code!='ccs'){
                  this.faculty = rs.loginTeacher.faculty;
              }else{
                  this.$store.dispatch('LogMessage', "查询成功!")
              }
          }).catch(err=>{
              this.$store.dispatch('LogMessage', "查询失败!")
              console.log(err)
          })
      },

    //上传图片数组
    handlePictureCardPreview(file){
      console.log(file)
    },
    //自定义上传，解决跨域问题
    myUpload(content) {
      console.log('file:'+content)
        let formData = new FormData();
        formData.append('file',content.file); // 'file[]' 代表数组 其中`file`是可变的
        this.ccs.post('upload', formData).then(rs=>{
         console.log(rs.id)
         this.uoloadFileListCcs.push(rs.id)
         console.log(this.uoloadFileListCcs)
        }).catch(err=>{
          this.$store.dispatch('LogMessage', "上传失败!")
          console.log(err)
        })
      },
      //自定义上传详情图，解决跨域问题
    myUploadDetail(content) {
      console.log('file:'+content)
        let formData = new FormData();
        formData.append('file',content.file); // 'file[]' 代表数组 其中`file`是可变的
        this.ccs.post('upload', formData).then(rs=>{
         console.log(rs.id)
         this.uoloadDetailFileListCcs.push(rs.id)
         console.log(this.uoloadDetailFileListCcs)
        }).catch(err=>{
          this.$store.dispatch('LogMessage', "上传失败!")
          console.log(err)
        })
      },
   //删除图片
   removeUpload(file,fileList){
    this.uploadFileList=[]
    this.uoloadFileListCcs=[]
   },
   //删除详情图片
   removeDetailUpload(file,fileList){
    this.uploadDetailFileList=[]
    this.this.uoloadDetailFileListCcs=[]
   },
    // 查询
    handleSearch() {
      this.table.currentPage = 1;
      this.table.start = 0;
      this.getTableInfo();
    },
    handleSearchClear() {
      this.searchForm.faculty = "";
      this.searchForm.weekday = "";
    },
    // 商品分类--新增初始化
    handleAdd() {
     this.editAddForm={
            id:'',    // 功课id
            am2:'',   //功课名称
            am8:'',   //
            weekday:'',  //
            pm2:'', //
            pm4:'',  //
            faculty:''//院系

     },

      this.editAddDialog.title = "新增功课";
      this.editAddDialog.type = "add";
      this.editAddDialog.visible = true;

    },
    // 修改
    handleEdit(index, row) {
      this.editAddForm.id =row.id;
      this.editAddForm.am8 = row.am8;
      this.editAddForm.weekday = row.weekday;
      this.editAddForm.am10 = row.am10;
      this.editAddForm.pm2 = row.pm2,
      this.editAddForm.pm4 = row.pm4,
      this.editAddForm.faculty = row.faculty,  //院系

      this.editAddDialog.title = "修改功课";
      this.editAddDialog.type = "edit";
      this.editAddDialog.visible = true;


      // this.$nextTick(() => {
      //   this.$refs["editAddForm"].clearValidate();
      // });
    },
    getTableInfo() {
      this.table.tableLoading = true;
       const params = {
          weekday: this.searchForm.weekday,
          faculty: this.searchForm.faculty,
          start: this.table.start,
          limit: this.table.limit,
        }

    this.ccs.post('/getTeacherTimetablePage', params).then(rs=>{
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
    // 删除会员等级
    handleRemove(el, row) {
      console.log("删除id" + row.id);
      this.$confirm(`是否删除?`, "提示", {
        cancelButtonText: "取消",
        confirmButtonText: "确定",
        type: "warning",
      }).then(() => {
          this.ccs.get('/removeTimetable?id='+row.id)
          .then((response) => {
            if(response.code=='ccs') {
              this.$message.error(response.msg);
            } else {
              this.$message({
                message: "删除成功",
                type: "success",
              });
              this.getTableInfo();
            }
          })
          .catch((err) => {
            console.error(err);
          });
      });
    },
    // 查看详情
    handleDtl(el, rows) {
      this.getDtl(rows.lvl_id);
    },
    getDtl(lvl_id) {
      rowDtl({
        params: {
          lvl_id: lvl_id,
        },
      }).then((resp) => {
        if (!resp.header.msg) {
          this.detail = resp.body.rows;
          this.dialogTableVisible = true;
        } else {
          this.$message({
            type: "warning",
            message: resp.header.msg,
          });
        }
      });
    },
    // 提交角色新增或编辑
    handleSubmit() {
      this.$refs["editAddForm"].validate((valid) => {
        if (valid) {
          if (this.editAddDialog.type === "add") {
            // 添加
            this.ccs.post('/addTimetable', this.editAddForm).then(rs=>{
            if(rs.code!='ccs'){
              this.$message({
                    message: "添加功课成功",
                    type: "success",
                  });
                  this.editAddDialog.visible = false;
                  this.getTableInfo();
            }else{
               this.$store.dispatch('LogMessage', "添加功课失败!")
            }
        }).catch(err=>{
          this.$store.dispatch('LogMessage', "添加医生失败!")
          console.log(err)
        })
       } else {
            // 修改
            this.ccs.post('/editTimetable', this.editAddForm).then(rs=>{
            if(rs.code!='ccs'){
              this.$message({
                    message: "编辑成功",
                    type: "success",
                  });
                  this.editAddDialog.visible = false;
                  this.getTableInfo();
            }else{
               this.$store.dispatch('LogMessage', "编辑失败!")
            }
        }).catch(err=>{
          this.$store.dispatch('LogMessage', "编辑失败!")
          console.log(err)
        })
         }
       }

      });
    },
    handleGrant(index, row) {
      // 查询选择权益列表信息
      // console.log("数据是：",row)

      this.grantTable.lvl_id = row.lvl_id;
      const grants = row.u_id ? row.u_id.split(",") : [];
      this.grantDialog.visible = true;
      this.oldSelectedList = [];
      this.$nextTick(() => {
        this.$refs.grantTable.clearSelection();
        for (let i = 0; i < grants.length; i++) {
          for (let j = 0; j < this.grantTable.list.length; j++) {
            if (grants[i] === this.grantTable.list[j].u_id) {
              this.oldSelectedList.push(this.grantTable.list[j]);
              this.$refs.grantTable.toggleRowSelection(this.grantTable.list[j]);
              break;
            }
          }
        }
      });
    },
    handleGrantSubmit() {
      // 提交授权
      const del_id_arr = [];
      const add_id_arr = [];
      const same_arr = [];

      this.grantTable.multipleSelection.forEach((item) => {
        let had = false;
        this.oldSelectedList.forEach((item2) => {
          if (item2.u_id === item.u_id) {
            same_arr.push(item.u_id);
            console.log("保存后的多选框是:", item.u_id);
            had = true;
          }
        });
        if (!had) {
          add_id_arr.push(item.u_id);
          console.log("增加的勾选是:", add_id_arr);
        }
      });

      if (same_arr.length === 0) {
        this.oldSelectedList.forEach((ele) => {
          del_id_arr.push(ele.u_id);
        });
      } else {
        this.oldSelectedList.forEach((ele) => {
          if (!same_arr.includes(ele.u_id)) {
            del_id_arr.push(ele.u_id);
          }
        });
      }

      // 首先获取等级编号Id 和 权益编号的集合
      const params = {
        equity_del_ids: del_id_arr.join(","), // 需要删除的等级权益数据
        equity_add_ids: add_id_arr.join(","), // 需要提那家的等级权益数据
        lvl_id: this.grantTable.lvl_id, // 等级编号Id
        // u_id: add_id_arr.join(',')
      };
      console.log("需要删除的集合是:", del_id_arr);
      console.log("需要添加的集合是:", add_id_arr);
      console.log("等级编号id是:", this.grantTable.lvl_id);

      saveGrant({
        params: params,
      })
        .then((response) => {
          this.grantDialog.visible = false;
          if (response.header.retcode !== "1") {
            this.$message.error({
              message: response.header.msg,
            });
          } else {
            this.$message({
              message: "授权保存成功",
              type: "success",
            });
            this.getTableInfo();
          }
        })
        .catch((err) => {
          this.grantDialog.visible = false;
          console.error(err);
        });
    },

    // 权益列表查询--请求
    getRightsTableInfo() {
      return new Promise((resolve, reject) => {
        const params = {
          equity_nm: "",
          cycle_type: "",
          equity_type: "",
          u_id: "",
          start: this.table.start,
          limit: this.table.limit,
        };
        console.log("getRightsTableInfo params: ", params);
        // debugger
        getRightsTableInfo({
          params: params,
        })
          .then((response) => {
            if (response.header.msg) {
              this.$alert(response.header.msg, "提示", {
                confirmButtonText: "确定",
              });
            } else {
              const data = response.body;
              console.log("getRoleTableInfo data: ", data);
              // debugger
              const rowsList = data.rows;
              if (data.rows && Array.isArray(data.rows)) {
                for (let index = 0; index < rowsList.length; index++) {
                  const element = rowsList[index];
                  this.grantTable.list.push({
                    u_id: element.u_id,
                    equity_nm: element.equity_nm,
                    cycle_type: element.cycle_type,
                    equity_cycle: element.equity_cycle,
                    equity_type: element.equity_type,
                    equity_source: element.equity_source,
                    img_url: element.img_url,
                  });
                }
                if (this.grantTable.list.length < data.totalCount) {
                  // this.getRoleTableInfo(start + limit)
                } else {
                  resolve();
                }
              }
            }
          })
          .catch((err) => {
            console.error(err);
            reject(err);
          });
      });
    },
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`)
      this.table.currentPage = 1;
      this.table.start = 0;
      this.table.limit = val;
      this.getTableInfo();
    },
    // 会员等级列表--页码
    handleCurrentChange(val) {
      this.table.start = (val - 1) * this.table.limit;
      this.getTableInfo();
      // console.log(`当前页: ${val}`)
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map((v) =>
        filterVal.map((j) => {
          return v[j];
        })
      );
    },
    // 权益多选框 handleSelectionChange
    handleGrantSelectionChange(val) {
      this.grantTable.multipleSelection = val;
    },
    onLogoChange(filelist) {
      this.logolist = filelist;
    },
    handleRemoveLogo(file) {
      this.logoWaitUpFlag = true;
      this.editAddForm.lvl_img_url = "";
      if (file.status === "success") {
        this.form.del_id.push(file.u_id);
      }
    },
    uploadLogoSuccess(uploadFile) {
      if (uploadFile) {
        // uploadFile为数组，如上传多张图片，含有多个元素
        this.editAddForm.lvl_img_url = uploadFile[0]; // 新上传的logo记录
        this.$notify({
          message: "上传图片成功",
          type: "success",
        });
        this.allImageDisable = false;
        this.$nextTick(() => {
          // todo:添加上传图片成功代码
          this.submitLogoSuccess();
        });
      }
    },
    submitAddEdit() {
     this.handleSubmit();
    },
    // 上传图片到后台
    submitLogo() {
      this.$refs.logoRef.submit();
    },
    // 上传图片成功处理
    submitLogoSuccess() {
      this.handleSubmit();
    },
  },
};
</script>

