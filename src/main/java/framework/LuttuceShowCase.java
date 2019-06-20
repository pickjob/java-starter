package framework;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @time 2019-06-20
 */
public class LuttuceShowCase {
    private static final Logger logger = LogManager.getLogger(LuttuceShowCase.class);

    public static void main(String[] args) {
        RedisClient redisClient = RedisClient.create("redis://@172.16.10.101/0");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();

        syncCommands.set("key", "Hello, Redis!");

        connection.close();
        redisClient.shutdown();
    }
}
