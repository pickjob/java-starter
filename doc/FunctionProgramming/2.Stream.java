/**
 * java.util.stream.Stream: 对集合操作什么而不是如何(what, not how)
 *      中间操作
 *          filter(Predicate<? super T> predicate): Stream<T>
 *          map(Function<? super T, ? extends R> mapper): Stream<T>
 *          flatMap(Function<? super T, ? extends Stream<? extends R>> mapper): Stream<T>
 *          distinct(): Stream<T>
 *          sorted(): Stream<T>
 *          sorted(Comparator<? super T> comparator): Stream<T>
 *          peek(Consumer<? super T> action): Stream<T>
 *          limit(long maxSize): Stream<T>
 *          skip(long n): Stream<T>
 *      终止操作
 *          forEach(Consumer<? super T> action): void
 *          forEachOrdered(Consumer<? super T> action): void
 *          reduce(T identity, BinaryOperator<T> accumulator): T
 *          reduce(BinaryOperator<T> accumulator): Optional<T>
 *          reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner): U
 *          anyMatch(Predicate<? super T> predicate): boolean
 *          allMatch(Predicate<? super T> predicate): boolean
 *          noneMatch(Predicate<? super T> predicate): boolean
 *          findFirst(): Optional<T>
 *          findAny(): Optional<T>
 *          toArray(): Object[]
 *          min(Comparator<? super T> comparator): Optional<T>
 *          max(Comparator<? super T> comparator): Optional<T>
 *          count(): long
 *          collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner): R
 *          collect(Collector<? super T, A, R> collector): R
 * Stream生成
 *      Strem工厂方法
 *          of(T ...)
 *          empyt()
 *          generate(Supplier<? extends T> s)
 *          iterate(T seed, [Predicate<? super T> hasNext, ]UnaryOperator<T> next)
 *      Collection.stream() / parallelStream()
 *      Arrays.stream(Object[])
 *      IO
 *          BufferedReader.lines()
 *          Files
 *          JarFile.stream()
 *      Others
 *          Random.ints()
 *          BitSet.stream()
 *          Pattern.splitAsStream(CharSequence)
 * Collector / Collectors
 *      Collectors工具方法
 *          转为集合( Stream -> JCF)
 *              toList() / toSet() / toCollection()
 *              toMap()
 *          分组( Stream -> Map<key, List>)
 *              groupingBy()
 *          过滤
 *              filtering()
 *          合并
 *              reducing()
 *          字符串拼接
 *              joining()
 *          计数
 *              counting()
 *          最大最小
 *              maxBy()
 *              minBy()
 *          映射
 *              mapping()
 *              flatMapping()
 *          分区
 *              partitioningBy()
 */