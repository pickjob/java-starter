package basic.net;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @author pickjob@126.com
 * @time 2019-05-05
 */
public class HttpClientShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(HttpClientShowCase.class);

    @Override
    public void showSomething() {
        logger.info("jdk自带HttpClient使用");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create("http://openjdk.java.net/"))
                                         .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
              .thenApply(HttpResponse::body)
              .thenAccept(logger::info)
              .join();
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
