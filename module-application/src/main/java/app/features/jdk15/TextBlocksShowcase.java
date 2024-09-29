package app.features.jdk15;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * TextBlock
 *
 * @author: pickjob@126.com
 * @date: 2024-09-02
 */
public class TextBlocksShowcase {
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