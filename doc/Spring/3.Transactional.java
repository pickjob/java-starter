/**
 * Transactional
 *      @EnableTransactionManagement引入配置类: TransactionManagementConfigurationSelector
 *          public class TransactionManagementConfigurationSelector extends AdviceModeImportSelector<EnableTransactionManagement> {
 *              protected String[] selectImports(AdviceMode adviceMode) {
 *                  // 根据注解mode配置相应Bean
 *                  switch (adviceMode) {
 *                      case PROXY:
 *                          // 通过Advisor拦截方法
 *                          //      AutoProxyRegistrar: ProxyCreator
 *                          //           InfrastructureAdvisorAutoProxyCreator
 *                          //           AspectJAwareAdvisorAutoProxyCreator
 *                          //           AnnotationAwareAspectJAutoProxyCreator
 *                          //      ProxyTransactionManagementConfiguration
 *                          //           TransactionAttributeSource: AnnotationTransactionAttributeSource
 *                          //           TransactionInterceptor: TransactionInterceptor(用于拦截)
 *                          //           BeanFactoryTransactionAttributeSourceAdvisor: TransactionAttributeSourcePointcut(Pointcut) + TransactionInterceptor(methodInterceptor) + TransactionAttributeSource(配置)
 *                          return new String[] {AutoProxyRegistrar.class.getName(), ProxyTransactionManagementConfiguration.class.getName()};
 *                      case ASPECTJ:
 *                          // 注册AspectJ代理类, 通过Aspect拦截方法
 *                          return new String[] {determineTransactionAspectClass()};
 *                      default:
 *                          return null;
 *                  }
 *              }
 *          }
 *          TransactionAspectSupport implements BeanFactoryAware, InitializingBean 实现@tTransactional 代理实现
 *      PlatformTransactionManager
 *          AbstractPlatformTransactionManager
 *              DataSourceTransactionManager
 *              JpaTransactionManager
 *              HibernateTransactionManager
 *              JtaTransactionManager
 *              JmsTransactionManager
 */