/**
 * 动态查询
 *      em.createQuery() / em.createNamedQuery
 *      @NamedQuery
 *      查询中命名参数: ":paraName"
 *      查询中位置参数: "?"
 *      存储过程: @NamedStoredProcedureQuery
 * Criteria
 *      元模型: **_
 *          @StaticMetamodel
 *          Metamodel m = em.getMetamodel();
 *          EntityType<Pet> Pet_ = m.getity(Pet.class);
 *          Root<Pet> pet cq.from(Pet.class);
 *          EntityType<Pet> Pet_ = pet.getModel
 *          // 示例
 *          CriteriaBuilder cb = em.getCriteriaBuilder();
 *          CriteriaQuery<Pet> cq = cb.createQuery(Pet.class);
 *          Root<Pet> pet = cq.from(Pet.class);
 *          cq.select(pet);
 *          TypedQuery<Pet> q = em.createQuery(cq);
 *          List<Pet> allPet = q.getResultList();
 *      创建
 *          CriteriaBuilder:
 *              Criteria查询
 *                  createQuery指定查询对象
 *              选择
 *              表达式
 *              谓词
 *              排序
 *              em.getCriteriaBuilder()
 *      查询根(指定查询所在范围)(Root)
 *          CriteriaQuery.form()
 *          Root<Pet> root = cq.from(Pet.class);
 *          Metamodel m = em.getMetamodel();
 *          EntityType<Pet> Pet_ = m.entity(Pet.class);
 *          Root<Pet> root = cq.from(Pet_)
 *      连接
 *          Root<Pet> pet = cq.from(Pet.class);
 *          Join<Pet,Owner> owner = pet.join(Pet_.owners);
 *          Join<Owner,Address> address = pet.join(Pet_.owners).join(Owner_.address);
 *      路径导航
 *          cq.select(pet.get(Pet_.name))
 *      限制查询结果(Predicate)
 *          CriteriaQuery.where
 *              isNull
 *              isNotNull
 *              in
 *          cq.where(pet.get(Pet_.color).isNull())
 *          CriteriaBuilder
 *              equal
 *              notEqual
 *              gt
 *              ge
 *              lt
 *              le
 *              between
 *              like
 *
 *              and
 *              or
 *              not
 *          cq.where(cb.equal(pet.get(Pet_.name),"Jack"))
 *      排序
 *          CriteriaQuery.orderBy CriteriaQuery.groupBy
 *          cq.orderBy(cb.desc(pet.get(Pet_.birthday)))
 *          cq.groupBy(pet.get(Pet_.color));
 *          cq.having(cb.in(pet.get(Pet_.color)).value("brown").value("blonde"));
 *      执行查询(TypedQuery)
 *          em.createQuery
 *          TypedQuery<T>.getSingleResult
 *          TypedQuery<T>.getResultList
 */