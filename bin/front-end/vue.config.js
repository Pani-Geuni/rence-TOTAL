module.exports = {
  transpileDependencies: true,
  lintOnSave: false,
  outputDir: '../src/main/resources/static',
  publicPath: './',
  assetsDir: './',
  devServer: {
    proxy: 'http://localhost:8080',
  },
  chainWebpack: (config) => {
    config.performance
      .maxEntrypointSize(400000)
      .maxAssetSize(400000);
  },
};
