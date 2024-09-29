package app.features.jdk11;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * JDK自带 HTTP Client, 支持Http1.1 Http2.0
 *      HttpRequest: 构建请求
 *      HttpResponse:
 *          BodyHandler: 响应处理
 *              BodyHandlers.ofString(Charset): 字符串
 *              BodyHandlers.ofFile(Path): 文件
 *              BodyHandlers.ofFileDownload(Path, OpenOption): 文件下载
 *              BodyHandlers.discarding(): 丢弃
 *      HttpClient: 请求主要类
 *          同步: send
 *          异步: sendAsync
 *
 * @author: pickjob@126.com
 * @date: 2024-09-02
 */
public class HttpClientShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient
                .newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .build();
        URI uri = URI.create("https://httpbin.org/get");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .header("Content-Type", "application/json")
                .build();
        // Synchronous
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        logger.info("Synchronous response status: {}", response.statusCode());
        logger.info("Synchronous response body: {}", response.body());
        // Asynchronous
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
                .thenAccept(res -> {
                    logger.info("Asynchronous response status: {}", res.statusCode());
                    logger.info("Asynchronous response body: {}", res.body());
                });
    }
}