/**
 * DispatcherServlet
 *      @EnableWebMvc: 支持WebMvc注解
 *          自定义配置继承
 *              WebMvcConfigurer
 *              WebMvcConfigurationSupport
 *              DelegatingWebMvcConfiguration: 使用组合多个WebMvcConfigurer
 *          WebMvcConfigurationSupport: 注册关键bean
 *              HandlerMappings
 *                  requestMappingHandlerMapping: mapping requests to annotated controller methods(order 0)
 *                      = ContentNegotiationManager + FormattingConversionService + ResourceUrlProvider + PathMatchConfigurer
 *                  viewControllerHandlerMapping: mapping URL paths directly to view names(order 1 )
 *                      = PathMatcher + UrlPathHelper + FormattingConversionService + ResourceUrlProvider
 *                  beanNameHandlerMapping: mapping URL paths to controller bean names(orderer 2)
 *                      = FormattingConversionService + ResourceUrlProvider
 *                  routerFunctionMapping
 *                      = FormattingConversionService + ResourceUrlProvider
 *                  resourceHandlerMapping: serve static resource requests(order Integer.MAX_VALUE-1)
 *                      = UrlPathHelper + PathMatcher + ContentNegotiationManager + FormattingConversionService + ResourceUrlProvider
 *                  defaultServletHandlerMapping: forward requests to the default servlet(ordere Integer.MAX_VALUE)
 *              HandlerAdapters
 *                  requestMappingHandlerAdapter: processing requests with annotated controller methods
 *                      = ContentNegotiationManager + FormattingConversionService + Validator
 *                  httpRequestHandlerAdapter: processing requests with HttpRequestHandlers
 *                  simpleControllerHandlerAdapter: processing requests with interface-based Controllers
 *              HandlerExceptionResolverComposite
 *                  ExceptionHandlerExceptionResolver: handling exceptions through ExceptionHandler methods
 *                  ResponseStatusExceptionResolver: for exceptions annotated with ResponseStatus
 *                  DefaultHandlerExceptionResolver: for resolving known Spring exception types
 *              AntPathMatcher / UrlPathHelper (PathMatchConfigurer)
 *              ContentNegotiationManager
 *              DefaultFormattingConversionService
 *              OptionalValidatorFactoryBean
 *              a range of HttpMessageConverters
 *          Spring MVC 核心Servlet: DispatcherServlet
 */