/**
 * Java Function
 *      Consumer 无返回值, 一个参数
 *      Supplier 有返回值, 无参数
 *      Function 有返回值, 一个参数
 *      Predicate 返回boolean, 一个参数
 *      UnaryOperator 参数与返回值同一类型
 *      Consumer类(无返回值)
 *          java.util.function.Consumer(一个参数)
 *          java.util.function.BiConsumer(两个参数)
 *          XXXComsumer(使用XXX作为参数)
 *      Supplier类(有返回值,无参数)
 *          XXXSupplier(产生XXX)
 *      Function类(有返回值)
 *          java.util.function.Function(一个参数)
 *          java.util.function.BiFunction(两个参数)
 *          XXXFunction(使用XXX作为参数)
 *          UnaryOperator(参数与返回值同一类型)
 *              java.util.function.UnaryOperator
 *          Predicate类(返回boolean)
 *              java.util.function.Predicate(一个参数)
 *              java.util.function.BiPredicate(两个参数)
 *              XXXPredicate(使用XXX作为参数)
 *      lambda函数表示
 *          () -> {}
 *          方法引用
 *              静态方法引用: ClassName::methodName
 *              实例上的实例方法引用: instanceReference::methodName
 *              超类上的实例方法引用: super::methodName
 *              类型上的实例方法引用: ClassName::methodName
 *              构造方法引用: Class::new
 *              数组构造方法引用: TypeName[]::new
 */