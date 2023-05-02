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
                <el-form-item label="学生姓名" prop="phone">
                  <el-input v-model="searchForm.phone" maxlength="60" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row class="param-row"> </el-row>
          </el-col>
          <el-col :span="6">
            <el-row type="flex" justify="center">
              <el-button type="primary" @click="handleSearch">查询</el-button>
              <el-button @click="handleSearchClear">清空</el-button>
            </el-row>
          </el-col>
        </el-row>
      </el-form>
      <div class="content">
        <el-alert title type="info" class="alert" :closable="false">
          <div class="left">用户列表</div>
        </el-alert>
        <!-- 角色列表 开始 -->
        <el-table
          v-loading="table.tableLoading"
          :data="table.list"
          class="table"
          border
        >
          <!--会员等级展示的列表列名称-->
          <!-- <el-table-column label="会员等级编号" min-width="80">
            <template slot-scope="scope">
              <span>{{ scope.row.lvl_id }}</span>
            </template>
          </el-table-column> -->
          <el-table-column label="学生id" min-width="80">
            <template slot-scope="scope">
              <span>{{ scope.row.sid }}</span>
            </template>
          </el-table-column>

          <el-table-column label="学生性别" min-width="80">
            <template slot-scope="scope">
              <span>{{ scope.row.sex }}</span>
            </template>
          </el-table-column>

          <el-table-column label="学生名字" min-width="80">
            <template slot-scope="scope">
              <span>{{ scope.row.name }}</span>
            </template>
          </el-table-column>

          <el-table-column label="学生学号" min-width="80">
            <template slot-scope="scope">
              <span>{{ scope.row.sno }}</span>
            </template>
          </el-table-column>

          <el-table-column label="院系" min-width="80">
            <template slot-scope="scope">
              <span>{{ scope.row.faculty }}</span>
            </template>
          </el-table-column>

        <el-table-column
            label="学生头像"
            min-width="120"
          >
            <template slot-scope="scope">
              <el-image 
                style="width: 150px; height: 75px"
                :src="scope.row.img"
              />
            </template>
          </el-table-column>

          <el-table-column fixed="right" label="操作" width="360">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                @click="handleDtl(scope.$index, scope.row)"
                >课程评分</el-button
              >
              <!-- <el-button
                size="mini"
                type="danger"
                @click="handleDtl2(scope.$index, scope.row)"
                >开药</el-button
              > -->
              <el-button
                size="mini"
                type="danger"
                @click="handleDtl3(scope.$index, scope.row)"
                >沟通</el-button
              >
              <!-- <el-button
                v-if="scope.row.status === 1"
                size="mini"
                type="primary"
                @click="freeze(scope.$index, scope.row)"
                >解除冻结</el-button
              > -->
              <!-- <el-button
                size="mini"
                type="primary"
                @click="handleRemove(scope.$index, scope.row)"
              >删除</el-button> -->
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
          :rules="dialogRules"
        >
          <el-form-item label="用户充值金额" prop="name">
            <el-input
              v-model="editAddForm.amount"
              maxlength="5"
              style="width: 60%"
            />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-row type="flex" justify="center">
            <el-button @click="editAddDialog.visible = false">取 消</el-button>
            <el-button type="primary" @click="recharge">充 值</el-button>
          </el-row>
        </div>
      </el-dialog>

      <!-- 详情-->
      <el-dialog
        title="开药列表"
        :visible.sync="dialogTableVisible"
        width="70%"
      >
        <template>
          <el-table :data="detail.drugOrderList" style="width: 100%">
            <el-table-column prop="name" label="药品名称" width="180">
            </el-table-column>
            <el-table-column prop="price" label="药品价格" width="180">
            </el-table-column>
          </el-table>
        </template>
      </el-dialog>

      <!-- 开药-->
      <el-dialog
        title="开出药单"
        :visible.sync="dialogTableVisible2"
        width="70%"
      >
        <el-transfer
          filterable
          filter-placeholder="请输入药品"
          v-model="value"
          :data="drugData"
        >
        </el-transfer>
        <el-button size="mini" type="danger" @click="addDrug()">开药</el-button>
      </el-dialog>

      <!-- 聊天-->
      <el-dialog
        title="病患聊天"
        :visible.sync="dialogTableVisible3"
        width="70%"
      >
        <div class="drugList" id="chat">
          <div v-for="item in chatList" style="margin-bottom:10px" :key="item.index">
            <div>
            <div  style="font-size:15px;display:inline-block;margin-right:15px;font-weight:600"  >
             {{ item.name }}
            </div>
              <el-tag>{{ item.text }}</el-tag>
          </div>
            <div style="font-size:6px">
              {{ item.create_time }}
            </div>
          </div>
        </div>
        <div style="margin-top: 15px">
          <el-input placeholder="请输入内容" maxlength="25" v-model="sendText">
            <el-button slot="append" @click="sendMsg">发送</el-button>
          </el-input>
        </div>
      </el-dialog>
    </el-main>
  </el-container>
</template>
<script>
import { Result } from "element-ui";
// import {
//   getTableInfo,
//   removeRow,
//   addRole,
//   editRole,f
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
      //聊天
      listCcs: [],
      chatList: [],
      //聊天输入框
      sendText: "",
      //药品
      drugData: [],
      //选择药品
      value: [],
      //上传过的文件
      uploadFileList: [
        {
          url: "https://img-blog.csdnimg.cn/f358b00e2d5f44eca9c424864bd943cf.png",
        },
      ],
      // 初始化旧的所选数据集合
      oldSelectedList: [],
      // 查看等级线下的权益详情

      detail: {},
      dialogTableVisible: false,
      dialogTableVisible2: false,
      dialogTableVisible3: false,

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
        name: "",
      },
      dialog02: {
        title: "",
        type: "",
        user_id: "",
        dialogFormVisible: false,
      },
      dialogForm02: {
        phone: "", // 手机号
        order_id: "", // 订单号
        is_see: "", //
        detail: "", //
      },
      // dialogForm02: {
      //   lvl_id: '',
      //   lvl_num: '',
      //   lvl_type: '',
      //   lvl_name: '',
      //   lvl_limit: '',
      //   limit_type: '',
      //   lvl_img_url: '',
      //   lvl_validity: ''
      // },
      // mcd商品分类
      editAddForm: {
        amount: 0, //充值金额
        name: "", //分类名称
        order: "", //分类排序
        id: "", //分类id
      },
      editAddDialog: {
        title: "",
        type: "",
        visible: false,
      },
      dialogRules: {
        amount: [
          { required: true, message: "请输入充值金额", trigger: "blur" },
        ],
      },
      productDialog: {
        visible: false,
      },
      downloadLoading: false,
      roles: [],
      uoloadFileListCcs: [],
    };
  },
  created() {
    this.initDrug();
    this.getTableInfo();

    // this.grantTable.list = [];
    // this.getRightsTableInfo();
  },
  mounted() {
    this.scrollToBottom();
  },
  updated() {
      // 在接收到新消息的时候触发方法将滚动条定位到底部
    this.scrollToBottom();
  },

  // 加载方法初始化
  methods: {
    //定时获取消息
    initCall(){
    setInterval(() => {
      let time = null;
      //  获取最后一次发送消息时间
      if (sessionStorage.getItem("call_time")) {
        time = sessionStorage.getItem("call_time");
      }
      //获取聊天最新消息
      const params = {
        order_id: sessionStorage.getItem("call_order_id"),
        create_time: time,
      };
      this.ccs.post("getChatList", params).then((rs) => {
        let lent = rs.chatList.length;
        if (lent < 1) {
          return;
        }
        sessionStorage.setItem("call_time", rs.chatList[lent - 1].create_time);
        let le = rs.chatList;
        for (let item of le) {
          this.chatList.push(item);
        }
      });
    }, 1000);
    },
    //发送聊天消息
    sendMsg() {
      console.log("发送");
      const params = {
        name: "医生:" + sessionStorage.getItem("doctor_name"),
        text: this.sendText,
        order_id: sessionStorage.getItem("call_order_id"),
      };
      this.ccs.post("addChat", params).then((rs) => {
        // this.$message({
        //   message: rs.msg,
        //   type: "success",
        // });
        this.sendText =''
      });
    },
    //聊天
    loadMore() {
      this.busy = true;
      // setTimeout(() => {
      //   this.drugData.push({name: 'this.count++' })
      //   console.log(this.drugData)
      //   this.busy = false
      // }, 1000)
    },
    // 定义将滚动条定位在底部的方法
    scrollToBottom() {
      this.$nextTick(() => {
        let chat = this.$el.querySelector("#chat");
        chat.scrollTop = chat.scrollHeight;
      });
    },

    //初始化药品
    initDrug() {
      this.ccs.post("getAllDrug").then((rs) => {
        for (var i = 0; i < rs.rows.length; i++) {
          this.drugData.push({
            label: rs.rows[i].name + "-" + rs.rows[i].price + "元",
            key: rs.rows[i].name + "-" + rs.rows[i].price + "元",
          });
          // this.drugData.push((rs.rows[i].name)+"-"+(rs.rows[i].price)+"元")
          // this.drugData.push((rs.rows[i].name))
        }
      });
    },

    //开药
    addDrug() {
      console.log("开药---" + this.detail.order_id);
      console.log("开药---" + this.value);
      let drugListz = [];
      let order_idz = this.detail.order_id;
      let list1 = this.value;
      if (list1.length < 1) {
        this.$message({
          message: "请先添加药品",
          type: "error",
        });
        return;
      }
      for (var i = 0; i < list1.length; i++) {
        var drug = list1[i];
        let drugobj = { name: drug.split("-")[0], price: drug.split("-")[1] };
        drugListz.push(drugobj);
      }
      const params = {
        drugList: drugListz,
        order_id: order_idz,
      };
      this.ccs.post("insertDrug", params).then((rs) => {
        this.$message({
          message: rs.msg,
          type: "success",
        });
        this.dialogTableVisible2 = false;
        this.getTableInfo();
      });
    },
    //数据转换
    statusFormatter(row, column) {
      const status = row.status;
      if (status == 0) {
        return "正常";
      } else if (status == 1) {
        return "冻结";
      }
    },
    //上传图片数组
    handlePictureCardPreview(file) {
      console.log(file);
    },
    //充值
    recharge() {
      const params = {
        phone: this.editAddForm.phone,
        amount: this.editAddForm.amount,
      };
      this.ccs.post("chnl/rechargeUserAmount", params).then((rs) => {
        this.$message({
          message: rs.msg,
          type: "success",
        });
        this.editAddDialog.visible = false;
        this.getTableInfo();
      });
    },
    //冻结/解冻
    freeze(index, row) {
      const params = {
        userPhone: row.phone,
      };
      this.ccs.post("freeze", params).then((rs) => {
        this.$message({
          message: rs.msg,
          type: "success",
        });
        this.getTableInfo();
      });
    },
    //自定义上传，解决跨域问题
    myUpload(content) {
      console.log("file:" + content);
      let formData = new FormData();
      formData.append("file", content.file); // 'file[]' 代表数组 其中`file`是可变的
      this.ccs
        .post("upload", formData)
        .then((rs) => {
          console.log(rs.id);
          this.uoloadFileListCcs.push(rs.id);
          console.log(this.uoloadFileListCcs);
        })
        .catch((err) => {
          this.$message({
            type: "error",
            message: "上传失败！",
          });
          console.log(err);
        });
    },
    //删除图片
    removeUpload(file, fileList) {
      this.uploadFileList = [];
      this.uoloadFileListCcs = [];
    },
    // 查询
    handleSearch() {
      this.table.currentPage = 1;
      this.table.start = 0;
      this.getTableInfo();
    },
    handleSearchClear() {
      this.searchForm.lvl_num = "";
      this.searchForm.lvl_name = "";
      this.searchForm.lvl_type = "";
    },

    // 充值
    handleEdit(index, row) {
      this.editAddForm.phone = row.phone;
      this.editAddDialog.type = "edit";
      this.editAddDialog.visible = true;
      console.log(row.lvl_img_url);
      // if (this.$refs.logoRef) {
      //   this.$refs.logoRef.clearFiles()
      // }
      // this.logofilelist = [{ url: row.lvl_img_url }]
      this.$nextTick(() => {
        this.$refs["editAddForm"].clearValidate();
      });
    },
    getTableInfo() {
      this.table.tableLoading = true;
      // url: 'NTMBizMember/iwap.ctrl',
      const params = {
        // 分类名称
        phone: this.searchForm.phone,
        start: this.table.start,
        limit: this.table.limit,
        doctor_id: sessionStorage.getItem("doctor_id"),
      };

      this.ccs
        .post("/getUserFallPage", params)
        .then((rs) => {
          if (rs.code != "ccs" && rs.rows && Array.isArray(rs.rows)) {
            this.table.tableLoading = false;
            this.table.list = rs.rows;
            this.table.total = rs.total || 0;
          } else {
            this.$message({
              type: "error",
              message: "查询失败",
            });
          }
        })
        .catch((err) => {
          this.$message({
            type: "error",
            message: "查询失败",
          });
          console.log(err);
        });
      // .then((response) => {
      //   this.table.tableLoading = false;
      //   if (response.header.msg) {
      //     this.$notify({
      //       title: "提示",
      //       message: response.header.msg,
      //       type: "warning",
      //     });
      //   } else {
      //     const data = response.body;
      //     if (data.rows && Array.isArray(data.rows)) {
      //       this.table.list = data.rows;
      //       this.table.total = data.total || 0;
      //     }
      //   }
      // })
      // .catch((err) => {
      //   console.error(err);
      // });
    },
    // 删除会员等级
    handleRemove(el, row) {
      this.$confirm(`是否将会员等级删除${row.lvl_id}?`, "提示", {
        cancelButtonText: "取消",
        confirmButtonText: "确定",
        type: "warning",
      }).then(() => {
        removeRow({
          // url: 'NTMBizMember/iwap.ctrl',
          params: {
            lvl_id: row.lvl_id,
          },
        })
          .then((response) => {
            if (response.header.msg) {
              this.$message.error(response.header.msg);
            } else {
              this.$message({
                message: "会员等级删除成功",
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
      this.detail = rows;
      this.dialogTableVisible = true;
      // this.getDtl(rows.lvl_id);
    },
    //开药
    handleDtl2(el, rows) {
      this.detail = rows;
      this.dialogTableVisible2 = true;
      // this.getDtl(rows.lvl_id);
    },
    //沟通
    handleDtl3(el, rows) {
      this.detail = rows;
      this.chatList=[]
      sessionStorage.removeItem("call_time");
      sessionStorage.setItem("call_order_id", rows.order_id);
      this.dialogTableVisible3 = true;
      this.loadMore();
      this.initCall();
      // this.getDtl(rows.lvl_id);
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
            // 新增
            const params = {
              name: this.editAddForm.name,
              order_num: this.editAddForm.order_num,
              img_id: this.uoloadFileListCcs[0],
            };
            this.ccs
              .post("/addClassify", params)
              .then((rs) => {
                if (rs.code != "ccs") {
                  this.$message({
                    message: "保存成功",
                    type: "success",
                  });
                  this.editAddDialog.visible = false;
                  this.getTableInfo();
                } else {
                  this.$message({
                    type: "error",
                    message: "保存失败",
                  });
                }
              })
              .catch((err) => {
                this.$message({
                  type: "error",
                  message: "保存失败",
                });
                console.log(err);
              });
          } else {
            // 修改
            const params = {
              name: this.editAddForm.name,
              order_num: this.editAddForm.order_num,
              img_id: this.uoloadFileListCcs[0],
              id: this.editAddForm.id,
            };
            this.ccs
              .post("/editClassify", params)
              .then((rs) => {
                if (rs.code != "ccs") {
                  this.$message({
                    message: "编辑成功",
                    type: "success",
                  });
                  this.editAddDialog.visible = false;
                  this.getTableInfo();
                } else {
                  this.$message({
                    type: "error",
                    message: "编辑失败",
                  });
                }
              })
              .catch((err) => {
                this.$message({
                  type: "error",
                  message: "编辑失败",
                });
                console.log(err);
              });
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
                    order_id: element.order_id,
                    fall_time: element.fall_time,
                    is_see: element.is_see,
                    detail: element.detail,
                    phone: element.phone,
                    create_time: element.create_time,
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
<style scoped>
.drugList {
  overflow-y: scroll;
  height: 200px;
}
</style>

