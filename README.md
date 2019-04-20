# docker
项目名称：Docker管理容器web化

实现功能：通过浏览器实现docker容器和镜像的管理

         1 创建、删除、暂停、重启容器
         
         2 查看容器ip、端口和运行status
         
         3 查看docker服务器中镜像列表
         
         4 管理员登录、注销等操作
         
所用技术

         1 前端：jsp+jstl渲染前台页面，利用bootstrap和layerui构建部分界面
         
         2 后端：传统servlet和自己搭建的mvc框架完成后台逻辑部分
         
         3 数据库：mysql+dbutils构建数据库
         
         3 与docker服务器通信：利用daemon完成与远程docker服务器通信
