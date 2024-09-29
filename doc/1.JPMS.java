/**
 * JPMS: Java Platform Module System
 *      module _modulename_: 模块定义
 *
 *      exports _package to  modulename_: 公开包
 *      opens _package to  modulename_: 编译器不可访问, 运行时可反射
 *
 *      requires _modulename_: 依赖模块
 *      requires transitive _modulename_: 传递依赖
 *
 *      provides _SPI Implement with SPI Interface_: SPI Server
 *      uses _SPI Interface_: SPI Client
 *
 *      META-INF/MANIFEST.MF
 *          Automatic-Module-Name: _module name_
 *
 */