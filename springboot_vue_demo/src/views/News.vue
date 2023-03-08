<template>
  <div style="padding: 10px">
    <!--    搜索区-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="输入作者关键字" style="width: 20%" clearable/>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
    <!--    功能区-->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增</el-button>
      <el-button type="primary">导入</el-button>
      <el-button type="primary">导出</el-button>
    </div>
    <!--    数据展示区-->
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column fixed prop="id" label="ID" sortable/>
      <el-table-column prop="title" label="新闻标题"/>
      <el-table-column prop="author" label="新闻作者"/>
      <el-table-column prop="time" label="发布时间"/>
      <el-table-column prop="adminTname" label="发布的管理"/>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="detailS(scope.row)">新闻详情</el-button>
          <el-button link type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除吗?" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button link type="danger" size="small">删除</el-button>
            </template>
          </el-popconfirm>
             </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 0">
      <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 20]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
    </div>

    <el-dialog v-model="dialogVisible" title="提示" width="50%">
      <el-form :model="form" label-width="120px" :rules="rules" ref="FormRef">
        <el-form-item label="新闻标题">
          <el-input v-model="form.title"/>
        </el-form-item>
        <el-form-item label="新闻内容">
          <div id="div1" style="border: 1px solid #ccc">
<!--            <Toolbar-->
<!--                style="border-bottom: 1px solid #ccc"-->
<!--                :editor="editorRef"-->
<!--                :defaultConfig="toolbarConfig"-->
<!--                :mode="mode"-->
<!--            />-->
<!--            <Editor-->
<!--                style="height: 500px; overflow-y: hidden;"-->
<!--                v-model="valueHtml"-->
<!--                :defaultConfig="editorConfig"-->
<!--                :mode="mode"-->
<!--                @onCreated="handleCreated"-->
<!--            />-->
          </div>

<!--          <el-input v-model="form.content"/>-->
        </el-form-item>

        <el-form-item label="新闻作者">
          <el-input v-model="form.author"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="save()">
            确定
          </el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog v-model="vis" title="新闻详情" width="50%">
      <el-card>
        <div v-html="details.content"></div>
      </el-card>
    </el-dialog>
  </div>
</template>

<script>
import {ref} from 'vue';
import request from "@/utils/request";

import {Plus} from "@element-plus/icons";


import E from 'wangeditor';

let editor;

export default {
  name: "News",
  components: {Plus},

  data() {
    return {
      form: {
      },
      dialogVisible: false,
      currentPage: 1,
      pageSize: 10,
      total: 0,
      search: ref(''),
      tableData: [],
      details: {},
      vis: false,
      rules: {},
      filesUploadUrl: "http://" + window.server.filesUploadUrl + ":9090/files/upload",
    }
  },
  created() {
    editor = ''
    this.load()
  },
  methods: {
    detailS(row) {
      this.details = row
      this.vis = true
    },
    load() {
      request.get("/news", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    add() {
      this.dialogVisible = true
      this.form = {}
      this.$nextTick(() => {
        // 关联弹窗里面的div，new一个editor对象 富文本对象
        if (!editor){
          editor = new E('#div1')
          editor.config.uploadImgServer = this.filesUploadUrl
          editor.config.uploadFileName = "file"  // 设置上传参数名称
          editor.create()
        }
        editor.txt.html("")
      })
    },
    save() {
      this.form.content = editor.txt.html()  //获取到富文本编辑器里面的值，然后赋值给实体类
      if (this.form.id) {  //如果有id 就更新  没有就新增
        request.put("/news", this.form).then(res => {
          console.log(res)
          this.$message({
            type: res.code,
            message: res.msg
          })
          this.load()  // 刷新表格
          this.dialogVisible = false  //关闭弹窗
        })
      } else {
        let adminStr = localStorage.getItem("admin") || {}
        let admin = JSON.parse(adminStr)
        this.form.adminTname = admin.adminTname
        request.post("/news", this.form).then(res => {
          console.log(res)
          this.$message({
            type: res.code,
            message: res.msg
          })
          this.load()  // 刷新表格
          this.dialogVisible = false  //关闭弹窗
        })
      }
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
      this.$nextTick(() => {
        // 关联弹窗里面的div，new一个editor对象 富文本对象
        if (!editor){
          editor = new E('#div1')
          editor.config.uploadImgServer = this.filesUploadUrl
          editor.config.uploadFileName = "file"  // 设置上传参数名称
          editor.create()
        }
        editor.txt.html(row.content)
      })
    },
    handleDelete(id) {
      request.delete("/news/" + id).then(res => {
        this.$message({
          type: res.code,
          message: res.msg
        })
        this.load()  // 刷新表格
      })
    },
    handleSizeChange(pageSize) {   // 改变当前每页的个数
      // this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {  //改变当前页码触发
      // this.currentPage = pageNum
      this.load()
    }
  },

}
</script>

<style scoped>

</style>