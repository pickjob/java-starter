/**
 * Spring Boot自动配置
 *       @SpringApplicatioh Boot自动配置
 *           引入:
 *              ComponentScan
 *              EnableAutoConfiguration: 自动配置类
 *              **AutoConfigurationImportSelector**: 完成自动配置必要实现
 *                   public class AutoConfigurationImportSelector implements DeferredImportSelector, BeanClassLoaderAware,
 * 							ResourceLoaderAware, BeanFactoryAware, EnvironmentAware, Ordered {
 * 		                // 核心实现:
 * 		                //		SpringFactoriesLoader:  加载META-INF/spring.factories中自动配置类
 * 	                }
 * 	         自动注入情况判断注解:
 * 	            Class Conditions
 * 	                @ConditionalOnClass
 * 	                @ConditionalOnMissingClass
 * 	            Bean Conditions
 * 	                @ConditionalOnBean
 *                  @ConditionalOnMissingBean
 *              Property Conditions
 *                  @ConditionalOnProperty
 *              Resource Conditions
 *                  @ConditionalOnResource
 *              Web Application Conditions
 *                  @ConditionalOnWebApplication
 *                  @ConditionalOnNotWebApplication
 *              SpEL Expression Conditions
 *                  @ConditionalOnExpression
 *              Java Vesion
 *                  @ConditionalOnJava
 *              Other
 *                  @ConditionalOnCloudPlatform
 *                  @ConditionalOnJndi
 *                  @ConditionalOnSingleCandidate
 *      AutoConfigurationImportSelector
 *           加载 META-INF/spring.factories 中 EnableAutoConfiguration 中 AutoConfiguration 类
 *           public ConfigurableApplicationContext run(String... args) {
 * 		        ...
 * 		        try {
 *                  // arguments、environment、banner
 * 			        ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
 * 			        ConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments);
 * 			        configureIgnoreBeanInfo(environment);
 * 			        Banner printedBanner = printBanner(environment);
 *                  // org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext
 * 			        context = createApplicationContext();
 *                  // 初始化上下文环境 environment ...
 * 			        prepareContext(context, environment, listeners, applicationArguments, printedBanner);
 *                  // 刷新 beanFactory.refresh 加载bean
 * 			        refreshContext(context);
 *                  // 空实现
 * 			        afterRefresh(context, applicationArguments);
 * 			        ...
 *                  // 调用 ApplicationRunner / CommandLineRunner 接口
 * 			        callRunners(context, applicationArguments);
 * 		        } catch (Throwable ex) {
 * 			        handleRunFailure(context, ex, exceptionReporters, listeners);
 * 			        throw new IllegalStateException(ex);
 *              }
 *              ...
 * 		        return context;
 * 		    }
 */