//package app.framework.mq.kafka;
//
//import app.common.IShowCase;
//import app.utils.StringSupplier;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.Producer;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.time.Duration;
//import java.time.temporal.TemporalUnit;
//import java.util.List;
//import java.util.Properties;
//import java.util.concurrent.TimeUnit;
//
///**
// * @Author pickjob@126.com
// * @Date 2020-10-09
// */
//public class KafkaClientShowCase implements IShowCase {
//    private static final Logger logger = LogManager.getLogger(KafkaClientShowCase.class);
//
//    @Override
//    public void showSomething() {
//        consumer();
//    }
//
////    @Override
////    public boolean isShow() {
////        return true;
////    }
////
////    @Override
////    public int order() {
////        return 1;
////    }
//
//    private void consumer() {
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "wsl2:9092");
//        props.put("group.id", "1");
//        props.put("enable.auto.commit", "true");
//        props.put("auto.commit.interval.ms", "1000");
//        props.put("session.timeout.ms", "30000");
//        props.put("max.poll.records", 1000);
//        props.put("auto.offset.reset", "earliest");
//        props.put("key.deserializer", StringDeserializer.class.getName());
//        props.put("value.deserializer", StringDeserializer.class.getName());
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
//        consumer.subscribe(List.of("KafkaTopic"));
//        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
//        for (ConsumerRecord<String, String> record : records) {
//            logger.info("key: {}, value: {}", record.key(), record.value());
//        }
//        consumer.close();
//    }
//}
