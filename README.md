## 个人博客的Java后台

###项目结构

```
    sql 数据库中的表
    src/main
       |- java
           |- cn.tenyear.blog
                |--- aspect aop切面，部分数据及时跟新，添加数据到redis
                |--- config 配置类
                |--- controller
                        |-- admin 处理后台页面的请求
                        |-- site  处理前台页面、登录页面 的请求
                |--- exception    异常工具
                |--- interceptor  jwt拦截器
                |--- mapper       mapper类(或dao类)
                |--- modle
                        |--- entity     实体类
                        |--- vo         网页中需要的数据类
                |--- service
                        |--- impl
                |--- utils
                |--- WebApplication.java
       |- resources
          |--- mapper
          |--- application.yaml
          |--- application-dev.yaml
```

mybatis-plus + mysql + jwt(权限验证)  + redis + aop

swagger3 默认关闭

```
gitee   
github  https://github.com/Mlisten/blog-springboot
```