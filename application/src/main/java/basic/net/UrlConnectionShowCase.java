package basic.net;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * @author pickjob@126.com
 * @time 2020-07-10
 */
public class UrlConnectionShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(UrlConnectionShowCase.class);
    @Override
    public void saySomething() {
        logger.info("UrlConnection用法");
    }

    @Override
    public void showSomething() {
        try {
            URL url = new URL("https://www.baidu.com");
            // obtain urlConnection
            URLConnection urlConnection = url.openConnection();
            // set request properties
            urlConnection.setReadTimeout(5000);
            // POST Data
//            urlConnection.setDoOutput(true);
//            PrintWriter writer = new PrintWriter(urlConnection.getOutputStream(), true, StandardCharsets.UTF_8);
//            writer.println();
            // connect
            urlConnection.connect();
            // query infomation
            Map<String, List<String>> headers = urlConnection.getHeaderFields();
            logger.info("headers: {}", headers);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                logger.info("content: {}", line);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
