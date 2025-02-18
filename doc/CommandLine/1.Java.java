/**
 * 命令格式
 *      java [options] mainclass [args]: 启动Java进程, Class File
 *      java [options] -m / --module module[/mainclass] [args ...]: 启动Java进程, Module Class
 *      java [options] -jar jarfile [args ...]: 启动Java进程, Jar File
 * 环境变量
 *      JDK_JAVA_OPTIONS
 * 选项配置
 *      布尔值
 *          -XX:-OptionName: 关闭
 *          -XX:+OptionName: 打开
 *      数值
 *          空格、冒号、等于号分割选项名和值
 * 选项类型
 *      标准选项
 *          代理选项
 *              -agentlib:libname[=options]
 *              -agentpath:pathname[=options]
 *              -javaagent:jarpath[=options]
 *          类路径选项
 *              --class-path classpath, -classpath classpath, or -cp classpath
 *          模块选项
 *              --list-modules
 *              -d module_name or --describe-module module_name
 *              --validate-modules
 *              --show-module-resolution
 *              --module-path modulepath... or -p modulepath
 *          预览特性
 *              --enable-preview
 *          JVM系统属性
 *              -Dproperty=value
 *          详情
 *              -verbose:class: 展示类加载信息
 *              -verbose:gc: 展示GC事件信息
 *              -verbose:jni: 展示JNI信息
 *              -verbose:module: 展示模块信息
 *      扩展选项
 *          -XX:VMOptionsFile=filename: 指定VM选项文件
 *          GC相关配置
 *              内存配置
 *                  -Xms size | -XX:MinHeapSize=size: 最小堆内存大小
 *                  -Xmx size | -XX:MaxHeapSize=size: 最大堆内存大小
 *                  -XX:InitialHeapSize=size: 初始化堆大小
 *                  -XX:-ShrinkHeapInSteps: 分步收缩堆内存
 *                  -XX:MinHeapFreeRatio=percent: 最小堆空闲, 小于则扩容, 默认40%
 *                  -XX:MaxHeapFreeRatio=percent: 最大堆空心啊, 大于则收缩, 默认70%
 *                  -XX:InitialRAMPercentage=percent: 初始内存占比, 默认1.5625%
 *                  -XX:MaxRAM: 最大RAM占RAM大小, 默认128G
 *                  -XX:MinRAMPercentage=percent: 最小堆内存占RAM百分比
 *                  -XX:MaxRAMPercentage=percent: 最大堆内存占RAM百分比, 默认25%
 *                  -XX:MaxDirectMemorySize=size: 最大直接内存大小
 *                  -XX:MetaspaceSize=size: 元数据内存大小
 *                  -XX:MaxMetaspaceSize=size: 最大元数据内存大小
 *                  -Xss size | -XX:ThreadStackSize=size: 线程栈内存大小, 默认1M
 *                  -XX:TLABSize=size: thread local buffer: 线程本地栈大小
 *                  -XX:+AggressiveHeap: 激进堆内存优化
 *                  -XX:ObjectAlignmentInBytes=alignment: 内存对齐, 默认8 bytes
 *                  分代
 *                      -Xmn size = -XX:NewSize=size + -XX:MaxNewSize=size: 年轻代初始化、最大化内存, G1推荐不设置
 *                      -XX:NewSize=size: 新生代大小
 *                      -XX:NewRatio=ratio: 新生代 / 老年代 比值, 默认 2
 *                      -XX:InitialSurvivorRatio=ratio: 初始新生代 Edge 和 Survivor 区域比 (Survivor) = (Young) / (Radio + 2), 默认为8
 *                      -XX:SurvivorRatio=ratio: 新生代 Edge 和 Survivor 区域比 (Survivor) = (Young) / (Radio + 2), 默认为8
 *                      -XX:TargetSurvivorRatio=percent: 新生代目标存活代占, 默认50%
 *                      -XX:MaxTenuringThreshold=threshold: 新生代到老年代经历次数, 默认15(最大15)
 *                  大页内存
 *                      -XX:+UseLargePages: 使用大页内存
 *                      -XX:+UseTransparentHugePages
 *                      -XX:LargePageSizeInBytes=size: 最大大页内存, 默认为0(默认大小)
 *                  其他
 *                      -XX:+UseAdaptiveSizePolicy: 使用自适应设置内存大小
 *                      -XX:NativeMemoryTracking=mode: 追踪Java本地内存 off / summary /detail
 *                      -XX:+UseStringDeduplication: 字符串去重, 必须开启G1
 *                      -XX:StringDeduplicationAgeThreshold=threshold: 字符串必须达到多少次才去重, 默认3
 *                      -XX:+UseNUMA: 使用非统一内存
 *                  垃圾回收器配置
 *                      -XX:ParallelGCThreads=threads: STW扫描线程数
 *                      -XX:+ScavengeBeforeFullGC: FullGC 前 YoungGC
 *                      -XX:+UseGCOverheadLimit: 限制GC时长, 默认98%GC抛OOM
 *              其他
 *                  -XX:+DisableExplicitGC: 关闭System.gc()方式调用GC
 *                  -XX:+ExplicitGCInvokesConcurrent: System.gc()调用并发GC
 *                  -Xnoclassgc: 取消Class GC
 *                  -XX:+AlwaysPreTouch: 将堆页面提交到内存中有助于减少延迟打嗝
 *                  -XX:SoftRefLRUPolicyMSPerMB=time: 软引用存活时间
 *              JIT编译
 *                  -XX:+TieredCompilation: 分层编译
 *                      -Xint: 只是用解释执行(C1编译器), 默认关闭
 *                  -Xmixed: C1、C2混合, 默认开启
 *                  -Xbatch / -XX:-BackgroundCompilation: 关闭后台任务编译, 必须前台线程编译完执行
 *                  -XX:CICompilerCount=threads: 编译线程数
 *                  -XX:+UseDynamicNumberOfCompilerThreads: 动态编译线程数, 默认开启
 *                  -XX:CompileOnly=methods: 仅编译指定方法
 *                  -XX:CompileCommand=command,method[,option]: 编译命令
 *                      command
 *                          break: 设置断点
 *                          compileonly: 只编译指定方法
 *                          dontinline: 不内联方法
 *                          exclude: 排除指定方法编译
 *                          inline: 尝试内联指定方法
 *                          log: 排除指定方法编译日志
 *                          print
 *                          quite
 *                  -XX:CompileCommandFile=filename: 编译命令配置文件
 *                  -XX:CompileThresholdScaling=scale: 小于1更容易编译
 *                  优化
 *                      -XX:+DoEscapeAnalysis: 逃逸性分析
 *                  内联
 *                      -XX:+Inline
 *                      -XX:FreqInlineSize=size
 *                      -XX:InlineSmallCode=size
 *                      -XX:MaxInlineSize=size: 默认35 Bytes
 *                      -XX:C1MaxInlineSize=size: 默认35 Bytes
 *                      -XX:MaxTrivialSize=size: 默认6 Bytes
 *                      -XX:C1MaxTrivialSize=size: 默认6 Bytes
 *                  缓存
 *                      -XX:ReservedCodeCacheSize=size: JIT缓存大小
 *                      -XX:+SegmentedCodeCache: 指定代码段缓存
 *                      -XX:+UseCodeCacheFlushing: 缓存刷洗
 *                  指令
 *                      -XX:UseSSE=version
 *                      -XX:UseAVX=version
 *                      -XX:+UseAES
 *                      -XX:+UseAESIntrinsics
 *                      -XX:+UseAESCTRIntrinsics
 *                      -XX:+UseGHASHIntrinsics
 *                      -XX:+UseBASE64Intrinsics
 *                      -XX:+UseAdler32Intrinsics
 *                      -XX:+UseCRC32Intrinsics
 *                      -XX:+UseCRC32CIntrinsics
 *                      -XX:+UseSHA
 *                      -XX:+UseSHA1Intrinsics
 *                      -XX:+UseSHA256Intrinsics
 *                      -XX:+UseSHA512Intrinsics
 *                  其他
 *                      -XX:StartAggressiveSweepingAt=percent: 前置删除未使用方法, 默认10%
 *                      -XX:InitialCodeCacheSize=size: 代码缓存大小
 *                      -XX:AllocateInstancePrefetchLines=lines: 默认为1
 *                      -XX:AllocatePrefetchDistance=size: 默认为-1
 *                      -XX:AllocatePrefetchInstr=instruction: 默认为0
 *                      -XX:AllocatePrefetchLines=lines: 默认为1
 *                      -XX:AllocatePrefetchStepSize=size: 默认16 Bytes
 *                      -XX:AllocatePrefetchStyle=style
 *                          0: 不生成预读指令
 *                          1: 执行预取指令, 默认
 *                          2: TLAB(Thread-local allocation block)
 *                          3: Per Cache line prefetch one
 *                      -XX:+OptimizeStringConcat: 优化字串拼接
 *              JFR配置
 *                  -XX:FlightRecorderOptions=parameter=value | -XX:FlightRecorderOptions:parameter=value
 *                      memorysize=size | numglobalbuffers| globalbuffersize=size: 数据使用内存(10MB)
 *                      maxchunksize=size: 最大块大小
 *                      old-object-queue-size=number-of-objects: 最大老对象追踪(256)
 *                      repository=path: 临时文件目录路径
 *                      retransform={true|false}: 是否转未 JVMTI 支持(true)
 *                      samplethreads={true|false}: 是否简单线程(true)
 *                      stackdepth=depth: 栈深度(64 - 2048)
 *                      threadbuffersize=size: 线程本地缓存
 *                  -XX:StartFlightRecording=parameter=value: JFR启动参数
 *                      delay=time
 *                      disk={true|false}
 *                      dumponexit={true|false}
 *                      duration=time
 *                      filename=path
 *                      name=identifier
 *                      maxage=time
 *                      path-to-gc-roots={true|false}
 *                      settings=path
 *              CDS(Class Data Sharing)配置
 *                  -Xshare:mode
 *                      auto
 *                      on
 *                      off
 *                  -XX:SharedArchiveFile=path: 指定CDS文件
 *                  -XX:SharedArchiveConfigFile=shared_config_file: 指定CDS配置
 *                  -XX:SharedClassListFile=file_name
 *              日志
 *                  -Xlog[:[what][:[output][:[decorators][:output-options[,...]]]]]
 *                      what: tag1[+tag2...][*][=level][,...]
 *                          tag
 *                          all: 所有日志
 *                          gc: GC相关日志
 *                              gc+ergo: 自适应策略
 *                              gc+task*: GC任务时间
 *                          safepoint: 安全点(并发、停顿)
 *                          class: 类相关
 *                              class+load: 类加载
 *                              class+init: 类初始化
 *                              class+unload: 类卸载
 *                          level
 *                              off / trace / debug / info / warning / error
 *                      output: 输出格式, 默认STDOUT
 *                          stdout
 *                          stderr
 *                          file=filename(%p:PID %t:startTimestamp)
 *                      decorators: 配置输出格式
 *                          time / t: 当前日期时间(ISO-8601)
 *                          utctime / utc: UTC时间
 *                          uptime or u: 启动后时间
 *                          timemillis or tm: 时间戳, System.currentTimeMillis()
 *                          uptimemillis or um: 启动后时间戳
 *                          timenanos or tn: 时间戳, System.nanoTime()
 *                          uptimenanos or un: 启动后时间戳
 *                          hostname or hn: HOSTNAME
 *                          pid or p: PID
 *                          tid or ti: TID
 *                          level or l: LEVEL
 *                          tags or tg: TAG
 *                      output-options: 输出选项
 *                          filecount=file-count filesize=file
 *                          默认: filesize=10M, filecount=5
 *                          默认格式: -Xlog:all=warning:stdout:uptime,level,tags
 *                  -Xlog:directive
 *                      directory: 子命令、配置
 *                         -Xlog:help: 帮助文档
 *                         -Xlog:disable: 关闭日志
 *                         -Xlog:async: 异步输出
 *                         -XX:LogFile=path
 *                  -XX:AsyncLogBufferSize=N: 异步写缓存
 *              模块选项
 *                  --add-reads module=target-module(,target-module)*: 指定模块读取其他目标模块, 目标模块能读取所有未命名模块
 *                  --add-exports module/package=target-module(,target-module)*: 导出指定模块下包给其他目标模块, 目标模块能读取所有未命名模块
 *                  --add-opens module/package=target-module(,target-module)*: 导出指定模块下包给其他目标模块, 目标模块能读取所有未命名模块
 *                  --limit-modules module[,module...]
 *                  --patch-module module=file(;file)*
 *              错误(%p: PID)
 *                  -XX:ErrorFile=filename: 遇到Error退出日志
 *                      -XX:ErrorFile=./hs_err_pid%p.log
 *                  -XX:OnError=string: 遇到Error执行命令
 *                  -XX:OnOutOfMemoryError=string: 遇到OOM命令
 *                  -XX:+ExtensiveErrorReports: 更多Error错误信息
 *                  -XX:+ShowCodeDetailsInExceptionMessages: 异常信息详情
 *                  -XX:+HeapDumpOnOutOfMemoryError: 堆DUMP
 *                  -XX:HeapDumpPath=path: 堆DUMP路径
 *                      -XX:HeapDumpPath=./java_pid%p.hprof
 *              硬件资源配置
 *                  -XX:-UseContainerSupport: 容器支持
 *                  -XX:ActiveProcessorCount=x: 指定使用CUP个数
 *                  -XX:AllocateHeapAt=path: 指定堆创建使用设备路径
 *              帮助
 *                  -X: 扩展选项帮助
 *                  -XshowSettings | -XX:+PrintCommandLineFlags: 打印所有配置选项
 *                  -XshowSettings:category: 按类别打印所有配置选项
 *                      all
 *                      locale
 *                      properties
 *                      vm
 *                      system
 *              其他
 *                  -Xbootclasspath/a:directories|zip|JAR-files: 添加目录、zip、jar文件到Bootstrap Class Path
 *                  -Xcheck:jni: JNI额外检查
 *                  -Xrs: 减少系统信号的使用
 *                  -XX:+UnlockExperimentalVMOptions: 开启实验特性
 *                  -XX:-CompactStrings: 关闭压缩字符串
 *                      默认开启, 单字节字符串减少一倍(ISO-8859-1 与 UTF-16)
 *                  -XX:-UseCompressedOops: 关闭指针压缩
 *                  -XX:+AllowUserSignalHandlers: 允许程序使用信号处理
 *                  -XX:+DisableAttachMechanism: 关闭附着机制
 *                  -XX:+UsePerfData: 开启Perfdata
 *                  -XX:+UseBiasedLocking: 使用偏向锁
 */