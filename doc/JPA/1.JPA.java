/**
 *      @Entity
 *          实体
 *          @Id
 *          Serializable接口
 *          实体中可嵌入
 *              @Embeddable标识实体可嵌入
 *              @Embedded嵌入实体类
 *      @Transient
 *      @Column
 *      @Temporal
 *      @ElementCollection
 *          target:集合类型
 *          fetch:LAZY/ENGER
 *      Map元素表示关系
 *           Map键或值是基本Java类型、可嵌入类型或一个实体
 *           Map值是一个可嵌入类或基本类型，@ElementCollection注解
 *           Map值是一个实体类，使用@OneToMany、@ManyToMany
 *           只在双向关系的一段使用Map类型
 *           Map键的类型是一个Java基本类型,可以使用@MapKeyColumn
 *           Map键的类型是一个实体类型,可以使用@MapKeyJoinColumns、@MapKeyJoinColumn、@MapKeyClass
 *           关系字段、属性没有使用泛型,@MapKeyClass
 *           Map键是Map值实体类字段、属性,@MapKey(@MapKeyClass不能同时使用)
 *           单向一对多: @JoinColumn
 *              @JoinColumn(
 *                  name = "", //维护关系(多的一边)的字段名
 *                  referencedColumn = "" //被参考(少的一边)的字段名
 *              )
 *      双向一对多/多对一:@OneToMany,@ManyToMany
 *      主键
 *          @Id
 *          @EmbeddedId / @IdClass
 *          生成策略:
 *              @GeneratedValue
 *              @TableGenerator
 *      实体类间关系
 *          一对一:@OneToOne
 *          一对多:@OneToMany
 *          多对一:@ManyToMany
 *          多对多:@ManyToMany
 *          双向关系
 *              接收方通过注解mappedBy指示其所属方
 *              多对一关系中多方不能定义mappedBy,多方往往是所属方
 *              一对一关系,所属方应包含相应外键的一方
 *              多对多关系,双方都可以是所属方
 *          单向关系(实体一个关系字段或属性指向另一个实体)
 *          层叠
 *              CascadeType
 *      实体继承
 *          @MappedSuperclass
 *          @Inherritance
 *          @Discriminator
 *      实体监听器
 *          @PostLoad
 *          @PrePersist
 *          @PostPersist
 *          @PreUpdate
 *          @PostUpdate
 *          @PreRemove
 *          @PostRemove
 *          @EntityListener
 *      管理
 *          EntityManager
 *          @PersistenceContext EntityManager
 *          @PersistenceUnit EntityManagerFactory
 *          CRUD:
 *              em.find()
 *              em.persist()
 *              em.remove()
 *              em.flush()
 *          持久化单元(persistence.xml)
 *              位置
 *                  war: WEB-INF/classes/META-INF/
 *                  jar: WEB-INF/lib/jar -> META-INF/
 *      锁
 *          乐观
 *              @Version : int Integer long Long short Short Timestamp
 *          锁模式
 *              em.lock
 *              em.find
 *              em.refresh
 *              q.setLockMode
 *              @NamedQuery lockMode
 *          一级缓存
 *              持久化上下文
 *          二级缓存
 *              缓存模式
 *                  ALL NONE ENABLE_SELECTIVE(@Cacheable) DISABLE_SELECTIVE UNSPECIFIED
 *              @Cacheable
 *              属性
 *                  javax.persistence.cache.retrieveMode USE BYPASS
 *                  javax.persistence.cache.storeMode USE BYPASS REFRESH
 *                  em.setProperty
 *              @Cache
 *                  Cache cache = em.getEntityManagerFactory().getCache();
 *                  if(cache.contains(Person.class,personPK)){...}
 *                  cache.evict(Person.class,personPK);
 *                  cache.evictAll();
 */