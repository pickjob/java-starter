/**
 * Spring Data
 *      核心接口:
 *          public interface Repository<T, ID> {}
 *          public interface CrudRepository<T, ID> extends Repository<T, ID> {}
 *          public interface PagingAndSortingRepository<T, ID> extends CrudRepository<T, ID> {}
 *          public interface JpaRepository<T, ID> extends PagingAndSortingRepository<T, ID>, QueryByExampleExecutor<T>{}
 *      常见注解
 *          @NoRepositoryBean: 用于不实现的父类Repository
 *          @EnableJpaRepositories: JPA Java Configuration
 *          @EnableMongoRepositories: Mongo Java Configuration
 *          @Query: 编写sql操作
 *          @Modifying: 有影响操作
 */