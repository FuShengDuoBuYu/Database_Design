# 数据库设计
## Lab1数据库应用接口

---
### 要求:
#### 一、实验目标

掌握高级语言操作数据库的方法



#### 二、实验内容

用自己熟悉的高级语言编写一个数据库初始化程序，将多种来源的外部数据导入MySql（或Oracle, SqlServer）

#### 三、实验环境

运行环境：MySql（Oracle或SqlServer）

开发工具：自选

#### 四、实验步骤

1、根据自己熟悉的高级语言（例如Java）开发环境，学习数据库接口（例如JDBC）的使用方法。

2、通过高级语言提供的接口读取csv格式数据表的数据。

3、通过JDBC或ODBC的原始API在MySql（或Oracle、SqlServer）中手工建立2中对应的数据表结构。（建议创建数据表的SQL语句用保存在文件中，由程序读入、执行）

4、将2中读取的数据，通过程序，插入到3中建立的相应数据表里。（建议由程序读取表结构，动态生成插入数据的SQL语句，使得你的程序可以支持不同的表结构）

（csv测试数据可从“文件”中下载）

#### 五、检查要求说明

本实验2周完成。

每人独立完成。

应当使用数据库访问接口调用create table、select、insert等语句完成数据库操作。

不使用已有的数据持久化框架（如MyBatis）。

#### 六、思考

如果外部数据（原始数据表）数据不完整（例如某个不应该为空的字段缺失数据）或不一致（例如本应有外键关系的数据并没有保持引用完整性），有哪些方法可以处理？你能总结出哪些处理原始数据的原则？

####七、注意事项

MySql驱动（Oracle或SqlServer也可）在对应的官网上有，请自行下载。

---
### 实验报告:

#### 实验使用语言与数据库

- java
- mysql(使用远程服务器,非本地)

#### 文件目录结构
```bash

├─.idea
│
├─lib # 第三方依赖库
│      mysql-connector-java-8.0.30.jar # mysql驱动
│
└─src
    └─database
        │  Entry.java # 入口文件
        │  FileIOUtil.java  # 处理文件IO的工具类,从csv里获取要插入的数据
        │  SQLExecute.java  # 执行sql操作的类,目前包括执行insert和create table
        │
        ├─MysqlSettings
        │      MysqlSettings.java # 配置mysql的类,包括mysql的用户名密码以及获取连接等
        │
        ├─sqlFile
        │  └─lab1
        │          CreateTableSql.java # 创建table
        │          createTableSql.sql  # 创建table的sql语句的文件
        │          room.csv # room数据文件
        │          student.csv # student数据文件
        │
        └─sqlUtils
                DataHandle.java # 处理文件数据,如去除双引号等
                TableInfo.java  # 获取表的相关信息,如列的名字和类型等

```
#### 表结构说明
- room表
  - 主键为考点号,考场号和考次号
```room
    kdno 考点号 int
    kcno 考场号  int
    ccno 场次号  int
    kdname 考点名称   varchar(255)
    exptime 预计开考时间    varchar(255)
    papername 指定试卷号   varchar(255)
    
    primary key(kdno,kcno,ccno)
```

- student表
    - 主键为考号
```student
    registno 考号 varchar(255)
    name 姓名 varchar(255)
    kdno 考点号 int
    kcno 考场号  int
    ccno 场次号  int
    seat 指定座位，0表示不指定座位 int
    
    priamry key(registno)
```
#### 实现思路

1.首先创建出两张表(student,room)

2.之后获取表的字段结构和数据类型

3.读取csv中的数据并根据获取到的字段结构的类型进行处理

4.利用prepareStatement插入数据

#### 实现效果

- 我们最终成功创建了两张表,如图
- ![img_4.png](https://github.com/FuShengDuoBuYu/Database_Design/raw/lab1/readme_image/img_4.png)
- 我们最后在room中插入了47条数据
![img_3.png](https://github.com/FuShengDuoBuYu/Database_Design/raw/lab1/readme_image/img_3.png)
- 在student中插入了1775条数据(其中有部分重复数据,故少于文件中的行数)
![img.png](https://github.com/FuShengDuoBuYu/Database_Design/raw/lab1/readme_image/img.png)

#### 遇到的问题和解决方案

##### 问题一:
###### 给出的两个文件的格式不同
- 其中一个字符集是UTF8,另一个则是GBK,使用java的Scanner
读取文件内容时需要指定字符集格式,否则会无法正确获取文件内容
- 其中一个文件有双引号,另一个没有

###### 解决方案:
- 在FileIOUtil类中写一个识别文件字符集的函数getFileCharset(),用来判断文件的字符集然后传递参数
- 在获取每行的文件内容之前,先把每行的双引号去掉

##### 问题二:
###### 两个表中的主键不太明确
###### 解决方案:
根据给出的数据内容等,猜测设置room表的主键为考点号,考场号和考次号,student的表主键为考号

##### 问题三:
###### student表中存在多行数据重复
- 在student表中,有多行数据是重复的,插入是会报主键重复的错误
###### 解决方案:
在prepareStatement执行时添加try catch,保证将主键不重复的数据全部插入,而主键重复的数据打印出对应的信息

#### 思考题回答:

##### 如果外部数据（原始数据表）数据不完整（例如某个不应该为空的字段缺失数据）或不一致（例如本应有外键关系的数据并没有保持引用完整性），有哪些方法可以处理？

##### 答:处理的方法可以有如下方式:

- 不允许不符合完整性要求的数据插入数据库
- 为不符合完整性要求的数据添加默认值
  - 如某个不该为空的字段缺值,那么就给它赋一个默认值,保证数据的插入成功
  - 如某个外键关系的数据未保持引用完整性,那么给这个字段设置一个默认的符合外键约束的值

##### 你能总结出哪些处理原始数据的原则？

##### 答:原则如下

- 对于不符合规则的原始数据,如果其他字段的内容需要保留,那么就给予不符合规则的字段一个原始值
- 如果其他字段不需要保留,那么就给编程者提示插入失败后舍弃其他字段的信息即可
