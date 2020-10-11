package basic.jcf;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import app.utils.StringSupplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author pickjob@126.com
 * @time 2019-04-23
 */
public class CollectorsShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(CollectorsShowCase.class);

    @Override
    public void showSomething() {
        logger.info("Collectors使用示例");
        List<String> arrayList = new ArrayList<>();
        StringSupplier supplier = new StringSupplier();
        for (int i = 0; i < 10; i++) {
            arrayList.add(supplier.get());
        }
        Map<String, List<String>> map = arrayList.stream()
                                        .collect(Collectors.groupingBy( x -> {
                                            return x;
                                        }));
        logger.info(map);
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
