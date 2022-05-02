## 社区

##项目部署
###安装依赖
- Git
- JDK
- Maven
- MySQL
###步骤
1、更新依赖版本
> yum
> 
> yum update

> yum install git
> 
> mkdir /App
> 
> cd /App
> 
> git clone xxx.git
> 
> cd [项目名]

下载和配置
> yum install maven
>
> mvn clean compile package #[打包命令]
> 
> more xx/xx/application.properties
> 
> cp xx/xx/application.properties xx/xx/application-prod.properties
> 
> vim xx/xx/application-prod.properties
> 
> maven package

启动项目
> java -jar -Dspring.profiles.active=prod target/xxx.jar
> 
> ps -aux | grep java [检查当前进程是否存在]
> 
> git仓库修改之后：
> 
> git pull
> 
> maven package



## 资料
spring文档
## 工具
git、GitHub
Bootstrap
okhttp
H2
## 脚本
- 删除数据库：rm ~/community.*
- sql执行语句 mvn flyway:migrate、mvn flyway:repair[修复]
- mybatis generator执行语句生成代码 mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
- 不同环境的配置方式：mvn clean compile flyway:migrate -Pdev [启动dev环境数据库]

##常见问题
###git：
1、当commit之后，还未push，再次修改文件
> git add [修改之后，要上传的文件]
> 
> git commit --amend --no-edit
> 
> git push

2、当在远程仓库修改内容，本地push失败
>git pull
>>Esc:x(退出并保存)
>
> git push
> 
###maven
1、pom.xml文件防止泄漏
> 找到maven中的setting.xml文件，在profiles标签配置信息