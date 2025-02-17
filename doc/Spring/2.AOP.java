/**
 * AOP:
 *		AOP的核心概念:
 *          Advice: 通知(an object which includes API invocations, 程序调用通知对象)
 *          JoinPoint: 连接点(a candidate point in the Program Execution of the application, 程序执行触机)
 *          JoinCut: 切点(define at what joinpoints, the associated Advice should be applied, 连接点定义信息)
 * 		org.aopalliance.aop.Advice: 标志接口(Tag interface)
 *      	- Interceptor: 拦截器, 标志接口
 *          	- MethodInterceptor: invoke
 *          	- ConstructorInterceptor: construct
 *      	- BeforeAdvice: 标志接口
 *          	- MethodBeforeAdvice: before
 *      	- AfterAdvice: 标志接口
 *          	- AfterReturningAdvice: afterreturn
 *          - ThrowsAdvice: 标志接口
 *      	- DynamicIntroductionAdvice
 *		org.aopalliance.intercept.Joinpoint:
 *          - Invocation: 代表程序调用
 *              - ConstructorInvocation: 构造器调用
 *              - MethodInvocation: 方法调用
 *                  - ProxyMethodInvocation: 对象代理方法调用接口
 *                      - ReflectiveMethodInvocation: 实现类
 *      org.springframework.aop.Advisor: Spring AOP主要保存Advice代理信息
 *      	- PointcutAdvisor
 *              - StaticMethodMatcherPointcutAdvisor: 按静态方法PointcutAdvisor类
 *              - InstantiationModelAwarePointcutAdvisor: 是否懒加载初始化接口
 *          - IntroductionAdvisor
 *		org.springframework.aop.framework.ProxyConfig: 代理类创建配置
 *      	- AdvisedSupport: 实际并未创建代理, 便于维护Advices、Advisors
 *          	- ProxyFactory: 编程方式创建代理, 大部分实际代理使用此类创建
 *          	- ProxyFactoryBean: FactoryBean创建代理
 *         		- AspectJProxyFactory: AspectJ代理创建
 *      	- AbstractSingletonProxyFactoryBean: 创建单例代理类(ProxyFactory)
 *          	- CacheProxyFactoryBean:  缓存代理创建类, ProxyFactory + CacheInterceptor
 *          	- TransactionProxyFactoryBean: 事务代理创建类, ProxyFactory + TransactionInterceptor, 最主要的配置 transactionManager + target + transactionAttributes
 *      	- ScopedProxyFactoryBean: Scoped Object代理创建类
 *      	- ProxyProcessorSupport: 代理类创建基础, 本质是都BeanPostProcessor实现功能
 *          - AbstractAdvisingBeanPostProcessor: 通过Advisor匹配对象生成代理类
 *              - AbstractBeanFactoryAwareAdvisingPostProcessor: 预先生成 ProxyFactory
 *                  - MethodValidationPostProcessor: DefaultPointcutAdvisor(AnnotationMatchingPointcut + MethodValidationInterceptor)
 *                  - AsyncAnnotationBeanPostProcessor: AsyncAnnotationAdvisor
 *                  - PersistenceExceptionTranslationPostProcessor: PersistenceExceptionTranslationAdvisor
 *          - AbstractAutoProxyCreator: 自动代理同一类(ProxyFactory)
 *              - AbstractAdvisorAutoProxyCreator: 自动代理Advisor匹配的类
 *                  - DefaultAdvisorAutoProxyCreator: 代理匹配Advisor(PointcutAdvisor、IntroductionAdvisor)类
 *                  - InfrastructureAdvisorAutoProxyCreator: 代理@Role(BeanDefinition.ROLE_INFRASTRUCTURE)对象
 *                  - AspectJAwareAdvisorAutoProxyCreator / AnnotationAwareAspectJAutoProxyCreator: 默认代理匹配Advisor(PointcutAdvisor、IntroductionAdvisor)类, 可配置pattern
 *			public @interface EnableAspectJAutoProxy {
 *         		// 代理目标对象类
 *         		boolean proxyTargetClass() default false;
 *         		// 暴露代理类
 *         		boolean exposeProxy() default false;
 *     		}
 */