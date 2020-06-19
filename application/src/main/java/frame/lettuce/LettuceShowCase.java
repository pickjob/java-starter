package frame.lettuce;

import app.common.IShowCase;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @time 2019-06-20
 */
public class LettuceShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(LettuceShowCase.class);
    private static final String CHANNEL = "channel";

    @Override
    public void saySomething() {
        logger.info("Lettuce Redis客户端");
    }

    @Override
    public void showSomething() {
        // redis :// [[username :] password@] host [: port] [/ database][? [timeout=timeout[d|h|m|s|ms|us|ns]] [&_database=database_]]
        RedisClient redisClient = RedisClient.create("redis://redis");
        publishSubscribe(redisClient);
        try {
            Thread.sleep(1000);
            syncShowCase(redisClient);
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        redisClient.shutdown();
    }

    private void syncShowCase(RedisClient redisClient) {
        RedisCommands<String, String> syncCommands = redisClient.connect().sync();
        syncCommands.publish(CHANNEL, "hello");
    }
    
    private void asyncShowCase(RedisClient redisClient) {
        RedisAsyncCommands<String, String> asyncCommands = redisClient.connect().async();
    }
    
    private void reactiveShowCase(RedisClient redisClient) {
        RedisReactiveCommands<String, String> reactiveCommands = redisClient.connect().reactive();
    }

    private void publishSubscribe(RedisClient redisClient) {
        StatefulRedisPubSubConnection<String, String> connection = redisClient.connectPubSub();
        connection.addListener(new RedisPubSubListener<String, String>() {
            @Override
            public void message(String channel, String message) {
                logger.info("channel: {}, message: {}", channel, message);
            }
            @Override
            public void message(String pattern, String channel, String message) {
                logger.info("pattern: {}, channel: {}, message: {}", pattern, channel, message);
            }
            @Override
            public void subscribed(String channel, long count) {
                logger.info("channel: {}, count: {}", channel, count);
            }
            @Override
            public void psubscribed(String pattern, long count) {
                logger.info("pattern: {}, count: {}", pattern, count);
            }
            @Override
            public void unsubscribed(String channel, long count) {
                logger.info("channel: {}, count: {}", channel, count);
            }
            @Override
            public void punsubscribed(String pattern, long count) {
                logger.info("pattern: {}, count: {}", pattern, count);
            }
        });

        RedisPubSubCommands<String, String> sync = connection.sync();
        sync.subscribe(CHANNEL);
    }

}
