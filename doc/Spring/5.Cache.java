/**
 * Cache:
 *      public interface Cache {
 *         // Return the cache name.
 *         String getName();
 *         // Return the underlying native cache provider.
 *         Object getNativeCache();
 *         ValueWrapper get(Object key);
 *         <T> T get(Object key, @Nullable Class<T> type);
 *         <T> T get(Object key, Callable<T> valueLoader);
 *         void put(Object key, @Nullable Object value);
 *         default ValueWrapper putIfAbsent(Object key, @Nullable Object value) {}
 *         void evict(Object key);
 *         default boolean evictIfPresent(Object key) {}
 *         void clear();
 *         default boolean invalidate() {}
 *     }
 *     CacheManager: 核心SPI
 *          public interface CacheManager {
 *              // Get the cache associated with the given name.
 *              Cache getCache(String name);
 *              // Get a collection of the cache names known by this manager.
 *              Collection<String> getCacheNames();
 *          }
 *      常见组合:
 *          ConcurrentMapCache + SimpleCacheManager: ConcurrentHashMap
 *          CaffeineCache + CaffeineCacheManager: guava Caffeine
 *          EhCacheCache + EhCacheCacheManager: Ehcache
 *      声明式注解:
 *          @Cacheable / @CacheResult: Triggers cache population.
 *              KeyGenerator: key生成策略
 *              CacheResolver: Cache查询策略
 *              condition
 *              spEl: #root.methodName #root.method.name #root.target #root.targetClass #root.args[0] #root.caches[0].name #result
 *           @CacheEvict / @CacheRemove / @CacheRemoveAll: Triggers cache eviction.
 *           @CachePut: Updates the cache without interfering with the method execution.
 *           @Caching: Regroups multiple cache operations to be applied on a method.
 *           @CacheConfig / @CacheDefaults: Shares some common cache-related settings at class-level.
 *      @EnableCaching: 支持Caching配置
 *           CachingConfigurer: 自定义 CacheManager CacheResolver KeyGenerator CacheErrorHandler
 *           CachingConfigurationSelector
 *               PROXY
 *                  AutoProxyRegistrar: ProxyCreator
 *                  ProxyCachingConfiguration: BeanFactoryCacheOperationSourceAdvisor: CacheOperationSourcePointcut(Pointcut) + CacheInterceptor(MethodInteceptor) + CacheOperationSource(配置)
 *              ASPECT
 *                  AspectJCachingConfiguration
 */