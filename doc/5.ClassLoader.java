/**
 * ClassLoader学习
 *      系统级
 *          Bootstrap ClassLoader : 启动类加载器
 *              JAVA_HOME\lib
 *          Extension ClassLoader : 扩展类加载器
 *              JAVA_HOME\lib\ext
 *          Application ClassLoader : 应用程序类加载器
 *      自定义
 *          覆写findClass(String name)
 *          继承URLClassLoader
 *      JVM类加载过程
 *          加载(Loading)
 *          验证(Verification)
 *          准备(Preparation)
 *          解析(resolution)
 *          初始化(Initialization)
 *          使用(Using)
 *          卸载(Unloading)
 *      一些东西
 *          双亲委托(代理) : 解决重复加载类的问题, 利用父加载器加载类
 *          SPI问题 : SPI接口为Bootstrap class loader, 加载不了接口实现, 使用Thread.getContextClassLoader(), 获得线程上下class loader, 没有，返回父线程class loader
 */