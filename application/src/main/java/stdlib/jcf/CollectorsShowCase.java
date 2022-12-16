package stdlib.jcf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author pickjob@126.com
 * @date 2022-12-16
 */
public class CollectorsShowCase {
    private static final Logger logger = LogManager.getLogger(CollectorsShowCase.class);

    public static void main(String[] args) {
        logger.info("Collectors使用示例");
        List<Integer> arrayList = new ArrayList<>();;
        for (int i = 0; i < 10000; i++) {
            arrayList.add(i);
        }
        Map<Integer, List<Integer>> map = arrayList.stream()
                                        .collect(Collectors.groupingBy(x -> {
                                            return (int) (x / 10);
                                        }));
        logger.info(map);
    }
}
