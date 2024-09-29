package app.standard.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * URLConnection: JDK自带http支持, 支持 http、https 1.0 1.1
 *
 * @author pickjob@126.com
 * @date: 2024-09-08
 */
public class UrlConnectionShowCase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        URL url = new URI("https://www.baidu.com").toURL();
        URLConnection urlConnection = url.openConnection();
        urlConnection.setReadTimeout(5000);
        // POST Data
        // urlConnection.setDoOutput(true);
        // PrintWriter writer = new PrintWriter(urlConnection.getOutputStream(), true, StandardCharsets.UTF_8);
        // writer.println();
        urlConnection.connect();
        Map<String, List<String>> headers = urlConnection.getHeaderFields();
        logger.info("headers: {}", headers);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            logger.info("content: {}", line);
        }
    }
}