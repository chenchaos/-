module.exports = {
    publicPath: "/admin-vue/",
    // 关闭线上源码
    productionSourceMap: false,
    chainWebpack: (config) => {
        const svgRule = config.module.rule('svg');

        svgRule.uses.clear();

        svgRule
          .use('babel-loader')
          .loader('babel-loader')
          .end()
          .use('vue-svg-loader')
          .loader('vue-svg-loader');
      },
       //2.配置跨域
       devServer: {
           disableHostCheck: true,
           open: true,
        host: 'localhost',
        port: 8080,
        https: false,
        hotOnly: false,
        // http 代理配置
        proxy: {
          "/api": {
            target: "http://127.0.0.1:8088",// 真正要请求的路径，即你要请求的后端数据所在的服务器地址
            changeOrigin: true,  //上面的参数列表中有一个changeOrigin参数, 是一个布尔值, 设置为true, 本地就会虚拟一个服务器接收你的请求并代你发送该请求
            ws: true,            //是否代理websocket
            pathRewrite: {       //重写路径  需要设置重写的话，要在后面的调用接口前加上/api 来代替target
                '^/api':'/',
            },
          }
        }
      },
}
