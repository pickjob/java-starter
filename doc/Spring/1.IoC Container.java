/**
 * IoC Container
 *      IoC (Inversion of Control)
 *      DI (dependency injection)
 *      核心接口:
 *           public interface BeanFactory {
 *              ...
 *              // 获得Bean方法
 *              T getBean(args...);
 *              ...
 *          }
 *          继承BeanFactory的几个接口:
 *              HierarchicalBeanFactory: 表示可继承(getParentBeanFactory)
 *              ListableBeanFactory: 表示可被遍历(getBeanXXX: String[])
 *              AutowireCapableBeanFactory: 表示可被注入(创建对象, 解决依赖, 完成BeanPostProcessor操作)
 *                  public interface AutowireCapableBeanFactory extends BeanFactory {
 *                      // Autowire策略
 *                      int AUTOWIRE_NO = 0;
 *                      int AUTOWIRE_BY_NAME = 1;
 *                      int AUTOWIRE_BY_TYPE = 2;
 *                      int AUTOWIRE_CONSTRUCTOR = 3;
 *                      @Deprecated int AUTOWIRE_AUTODETECT = 4;
 *                      // 实例化对象并解决依赖
 *                      ...
 *                      // 完成BeanPostProcessor操作
 *                      Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;
 *                      Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
 *                  }
 *              SingletonBeanRegistry: 单例注册对象
 *              ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry
 *              ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory
 *      Spring IoC核心扩展:
 *          BeanFactoryPostProcessor: 注册Configruation类 ...
 *              public interface BeanFactoryPostProcessor {
 *                  void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
 *              }
 *          BeanPostProcessor: Bean增强, 代理, Bean初始化前, 初始化后调用
 *              public interface BeanPostProcessor {
 *                  default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
 *                      return bean;
 *                  }
 *                  default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
 *                      return bean;
 *                  }
 *              }
 *              DestructionAwareBeanPostProcessor: Bean销毁前调用
 *              MergedBeanDefinitionPostProcessor: 合并Bean Definition调用
 *      applicationContext接口使用Spring IoC:
 *          org.springframework.context.ApplicationContext extends EnvironmentCapable, ListableBeanFactory, HierarchicalBeanFactory, MessageSource, ApplicationEventPublisher, ResourcePatternResolver
 *
 *          org.springframework.context.support.FileSystemXmlApplicationContext
 *          org.springframework.context.support.ClassPathXmlApplicationContext
 *          org.springframework.context.annotation.AnnotationConfigApplicationContext
 *          org.springframework.web.context.support.XmlWebApplicationContext
 *          org.springframework.web.context.support.AnnotationConfigWebApplicationContext
 *      生命周期管理: Aware
 *          初始化:
 *              BeanNameAware
 *              BeanClassLoaderAware
 *              BeanFactoryAware
 *              EnvironmentAware
 *              EmbeddedValueResolverAware
 *              ResourceLoaderAware
 *              ApplicationEventPublisherAware
 *              MessageSourceAware
 *              ApplicationContextAware
 *              ServletContextAware
 *              BeanPostProcessor.postProcessBeforeInitialization()
 *              InitializingBean's afterPropertiesSet
 *              custom init-method definition
 *              BeanPostProcessor.postProcessAfterInitialization()
 *          销毁:
 *              postProcessBeforeDestruction methods of DestructionAwareBeanPostProcessors
 *              DisposableBean's destroy
 *              custom destroy-method definition
 *          其他:
 *              LoadTimeWeaverAware
 *              NotificationPublisherAware
 *              ServletConfigAware
 *              SchedulerContextAware
 *              ImportAware
 *      注解:
 *          配置信息类注解:
 *              @Import: 配合 @Configuration, ImportSelector, ImportBeanDefinitionRegistrar (引入Configuration类)
 *              @Configuration
 *          bean:
 *              @Bean
 *              @Component
 *                  @Repository
 *                  @Service
 *                  @Controller
 *          inject:
 *               @Autowired(Spring平台, required, by type) + @Qualifier(Spring平台, by name)
 *               @Inject(JSR, by type) + @Named(JSR, by name)(继承自 JSR Qualifier) (JSR330 依赖注入规范)
 *               @Resource(JSR, by name)(JSR250 通用注解规范, Servlet容器环境)
 *      启动流程:
 *          AbstractApplicationContext.java
 *              public void refresh() throws BeansException, IllegalStateException {
 *                  synchronized (this.startupShutdownMonitor) {
 *                  // 上下文环境准备(PropertySource、Environment)
 *                  prepareRefresh();
 *                  // 实际使用 DefaultListableBeanFactory
 *                  ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
 *                  // 准备BeanFactory
 *                  //      设置  BeanClassLoader ...
 *                  //      注册BeanPostProcessor
 *                  //          ApplicationContextAwareProcessor
 *                  //          ApplicationListenerDetector
 *                  prepareBeanFactory(beanFactory);
 *                  try {
 *                      // 子类处理 postProcessBeanFactory
 *                      postProcessBeanFactory(beanFactory);
 *                      // 调用 BeanFactoryPostProcessor, 区分处理 Bean Definition Registry Post Processor}
 *                      //      BeanDefinitionRegistryPostProcessor: ConfigurationClassPostProcessor ... 处理@Configuration、@Import、@Bean...
 *                      invokeBeanFactoryPostProcessors(beanFactory);
 *                      // 注册 BeanPostProcessor
 *                      registerBeanPostProcessors(beanFactory);
 *                      // 初始化MessageSource
 *                      initMessageSource();
 *                      // 初始化ApplicationEventMulticaster
 *                      initApplicationEventMulticaster();
 *                      // 额外特殊bean初始化
 *                      onRefresh();
 *                      // 注册Listener
 *                      registerListeners();
 *                      // 结束BeanFactory初始化
 *                      // 冻结BeanFacotry配置 beanFactory.freezeConfiguration()
 *                      // 始化单例Bean beanFactory.preInstantiateSingletons()
 *                      finishBeanFactoryInitialization(beanFactory);
 *                      // 结束刷新操作, 发布corresponding event
 *                      finishRefresh();
 *                  } catch (BeansException ex) {
 *                      // 销毁beans
 *                      destroyBeans();
 *                      cancelRefresh(ex);
 *                      // 抛出异常
 *                      throw ex;
 *                  } finally {
 *                      // 重置缓存, 单例注入信息不再需要了(已经初始好了)
 *                      resetCommonCaches();
 *                  }
 *              }
 */