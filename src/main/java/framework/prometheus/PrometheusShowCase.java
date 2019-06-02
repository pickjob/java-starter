package framework.prometheus;

import io.prometheus.client.Counter;
import io.prometheus.client.exporter.HTTPServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * @author pickjob@126.com
 * @time 2019-05-28
 **/
public class PrometheusShowCase {
    private static final Logger logger = LogManager.getLogger(PrometheusShowCase.class);
    private static final Counter counter = Counter.build()
            .name("label_total")
            .help("test label")
            .register();

    public static void main(String[] args) {
        try {
            HTTPServer server = new HTTPServer(1234);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
