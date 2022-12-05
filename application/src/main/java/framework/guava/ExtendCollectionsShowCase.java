package framework.guava;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Guava集合扩展:
 *      不可变类型: ImmutableXXX
 *      工具类: XXXs
 *      新类型:
 *          Multiset: like Map<Key, Integer> (key, count)键值对
 *              entrySet(): Set<Multiset.Entry<E>>
 *              count(Key)
 *              add(E)
 *              size()
 *          Multimap: like Map<Key, List<Value>> (key, List<E>)键值对
 *              put(Key, Value)
 *              remove(Key, Value)
 *              entries(): Collection<Map.Entry<K, V>>
 *          Table: Table<R, C, V> 多个key映射 Value
 *              rowMap(): Map<R, Map<C, V>>
 *              cellSet(): Table.Cell<R, C, V>
 *
 * @author: pickjob@126.com
 * @date: 2022-12-05
 */
public class ExtendCollectionsShowCase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        String row = "row1";
        String col = "col1";
        Table<String, String, String> table = ImmutableTable.of(row, col, "val1");
        logger.info(table.get(row, col));
    }
}
