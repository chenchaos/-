import Vue from 'vue'
import VueRouter from 'vue-router'

import Layout from '@/layout' //布局页



Vue.use(VueRouter)

// 通用页面, 这里的配置不需要权限
export const constRouter = [
  {
      path: '/login',
      component: () => import('@/views/login/Login'),
      hidden: true //导航菜单忽略选项
  },
  {
      path: '',
      component: Layout, //应用布局页
      redirect: '/index',
      hidden: true,
  },
  {
      path: '/index',
      component: Layout, //应用布局页
      name: 'index',
      meta:{
          title: "首页", //导航菜单项标题
          icon: 'el-icon-s-home' //导航菜单图标
      },
      children: [
        {
          path: '',
          component: () => import('@/views/index/index.vue'),
          name: 'indexs',
          meta: {
            title: "工作台",
            icon: 'el-icon-s-home',
            roles: ['admin','jerry']
          }
        }
      ]
  }
]

// 动态路由 communication
export const asyncRoutes = [

  {
    path: '/goods',
    component: Layout,
    redirect: '/goods/index',
    meta:{
      title: "产品管理",
      icon: 'el-icon-s-goods',
      hidden: false,
    },
    children: [
      {
        path: 'fromgood',
        component: () => import('@/views/goods/FromGood.vue'),
        name: 'fromgood',
        meta: {
          title: "添加(编辑产品)",
          icon: 'el-icon-tickets',
          hidden: true,
          roles: ['admin','jerry'],

        }
      },
      {
        path: 'onindex',
        component: () => import('@/views/goods/merchandise.vue'),
        // component: () => import('@/views/goods/OnIndex.vue'),
        name: 'onindex',
        meta: {
          title: "生活团购产品",
          icon: 'el-icon-tickets',
          hidden: false,
          roles: ['admin','jerry']
        }
      },

      {
        path: 'sort',
        component: () => import('@/views/goods/SortNew.vue'),
        name: 'sort',
        meta: {
          title: "产品分类",
          icon: 'el-icon-tickets',
          hidden: false,
          roles: ['admin','jerry']
        }
      },
    ]
  },
  {
    path: '/control',
    component: Layout,
    redirect: '/control/keycode',
    meta:{
      title: "用户/订单管理",
      icon: 'el-icon-s-platform',
      hidden: false,
    },
    children: [
      {
        path: 'offindex',
        component: () => import('@/views/goods/OffIndex.vue'),
        name: 'offindex',
        meta: {
          title: "用户订单管理",
          icon: 'el-icon-tickets',
          hidden: false,
          roles: ['admin','jerry']
        }
      },
      {
        path: 'user',
        component: () => import('@/views/goods/User.vue'),
        name: 'user',
        meta: {
          title: "用户管理",
          icon: 'el-icon-tickets',
          hidden: false,
          roles: ['admin','jerry']
        }
      }
    ]
  },
  {
    path: '/doctorManage',
    component: Layout,
    redirect: '/doctorManage/index',
    meta:{
      title: "教师管理",
      icon: 'el-icon-s-goods',
      hidden: false,
    },
    children: [
      {
        path: 'fromgood',
        component: () => import('@/views/doctorManage/FromGood.vue'),
        name: 'fromgood',
        meta: {
          title: "添加(编辑产品)",
          icon: 'el-icon-tickets',
          hidden: true,
          roles: ['admin','jerry'],

        }
      },
      {
        path: 'onindex',
        component: () => import('@/views/doctorManage/merchandise.vue'),
        // component: () => import('@/views/goods/OnIndex.vue'),
        name: 'onindex',
        meta: {
          title: "教师管理",
          icon: 'el-icon-tickets',
          hidden: false,
          roles: ['admin','jerry']
        }
      },

    ]
  },

  {
    path: '/teacherManage',
    component: Layout,
    meta:{
      title: "教师管理",
      icon: 'el-icon-s-platform',
      hidden: false,
    },
    children: [
      // {
      //   path: 'offindex',
      //   component: () => import('@/views/doctorManage/OffIndex.vue'),
      //   name: 'offindex',
      //   meta: {
      //     title: "用户订单管理",
      //     icon: 'el-icon-tickets',
      //     hidden: false,
      //     roles: ['admin','jerry']
      //   }
      // },
      {
        path: 'student',
        component: () => import('@/views/teacherManage/student.vue'),
        name: 'student',
        meta: {
          title: "学生管理",
          icon: 'el-icon-tickets',
          hidden: false,
          roles: ['admin', 'jerry']
        }
      },
      {
        path: 'course',
        component: () => import('@/views/teacherManage/course.vue'),
        name: 'course',
        meta: {
          title: "课程管理",
          icon: 'el-icon-tickets',
          hidden: false,
          roles: ['admin','jerry']
        }
      }
    ]
  },

  {
    path: '/doctor',
    component: Layout,
    redirect: '/doctor/keycode',
    meta:{
      title: "病人管理",
      icon: 'el-icon-s-platform',
      hidden: false,
    },
    children: [
      {
        path: 'offindex',
        component: () => import('@/views/doctorManage/OffIndex.vue'),
        name: 'offindex',
        meta: {
          title: "用户订单管理",
          icon: 'el-icon-tickets',
          hidden: false,
          roles: ['admin','jerry']
        }
      },
      {
        path: 'user',
        component: () => import('@/views/doctorManage/UserOrder.vue'),
        name: 'user',
        meta: {
          title: "病患列表",
          icon: 'el-icon-tickets',
          hidden: false,
          roles: ['admin','jerry']
        }
      }
    ]
  },
]

const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes: constRouter
})

export default router

