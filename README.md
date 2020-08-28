# ProxyDemo
java动态代理、切面编程（类似于Spring IOC、AOP）

正常java main方法启动

启动入口：MainApplication.java

初始化、基础配置类：com.init.Init.java
*工程跟路径 ROOT_PAC 配置
*组件扫描包 COM_SCAN_PAC 配置
*切面扫描包 ASPECT_SCAN_PAC 配置
*程序执行入口类 MAIN_CLASS_PATH 配置

组件、切面扫描包多个配置，使用“;”分割