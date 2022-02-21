# CommandSocket

## 说明

Socket指令交互组件  
[socket-command](https://github.com/Verlif/socket-command) 是基于 [socket-core](https://github.com/socket-core) 与 [loader-jar](https://github.com/loader-jar) 的组合组件。
通过类似于加载模组的方式，通过加载jar文件的方式来加载指令，然后通过socket的方式来执行指令。

* 一个服务端可以连接任意数量客户端
* 开放式指令包，5分钟完成指令包创建
* 方便拓展与修改，定制自己的指令流程

从这里可以找到 [指令包仓库](https://github.com/topics/socket-command-extend)

------

## 指令包

指令包指的是包含有`SocketCommand`实现类的`jar包`。一个指令包可以包含数个指令。  
指令包的制作流程：
1. 新建`maven`项目
2. 添加本组件作为依赖
3. 实现`SocketCommand`接口
4. 打包成`jar`包（若有其他依赖，请将依赖添加至jar包中）

------

## 预期

最初开发的目的只是为了写一个socket组件，方便未来可能会用到的socket工具。
后来因为写了 [loader-jar](https://github.com/loader-jar) ，所以就想着干脆把这俩和在一起，做一个工具框架。
于是就有了现在的 [socket-command](https://github.com/Verlif/socket-command) 。

不过因为个人能力有限，本组件框架的拓展性只能随着版本迭代慢慢提高。
有任何使用上的问题或是对本组件有任何的建议都可以提`issue`。
