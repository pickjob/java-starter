package framework.lombok;

import common.IShowCase;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LombokShowCase implements IShowCase {
    private static Logger logger = LogManager.getLogger(LombokShowCase.class);

    @Override
    public void saySomething() {
        logger.info("展示lombok对代码Entity的简洁");
    }

    @Override
    public void showSomething() {
        LomboEntity entity = new LomboEntity();
        entity.setId(13l);
        entity.setName("Name");
        entity.setTitle("Title");
        entity.setSex("Sex");
        logger.info("{}", entity);
    }
}

@Data
class LomboEntity {
    private Long id;
    private String name;
    private String title;
    private String sex;
}