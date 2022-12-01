package features.jdk15;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author: pickjob@126.com
 * @date: 2022-12-01
 */
public class TextBlocksShowCase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        String textBlocks = """
                <html>
                    <head>
                        <title>Hello World</title>
                    </head>
                    <body>
                        <h1>你好，世界</h1>
                    </body>
                </html>
                """;
        logger.info("{}", textBlocks);
    }
}
