const { createProxyMiddleware } = require("http-proxy-middleware")

module.exports = app => {
    app.use(
        createProxyMiddleware('/authenticate',
        {
            target: 'http://localhost:8080/authenticate',
            changeOrigin: true
        })
    )
}