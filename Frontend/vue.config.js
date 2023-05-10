const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  // devServer: {
  //   proxy: {
  //     '/api': {
  //       target: 'http://localhost:9090',
  //       // 忽略前缀路径 它是一个对象 里面是key value
  //       // 这个正则意思是 如果前缀是/api,那么会替换成空字符
  //       pathRewrite:{'^/api':''}
  //
  //       // ws: true,
  //       // changeOrigin: true
  //
  //     },
  //     // '/foo': {target: '<other_url>'}
  //   }
  // }
})
