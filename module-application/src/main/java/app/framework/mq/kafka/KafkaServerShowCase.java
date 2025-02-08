//package app.framework.mq.kafka;
//
//import app.common.IShowCase;
//import app.utils.StringSupplier;
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.Producer;
//import org.apache.kafka.clients.producer.ProducerRecord;
//
//import java.util.Properties;
//
///**
// * @author: pickjob@126.com
// * @date: 2020-10-09
// */
//public class KafkaServerShowCase implements IShowCase {
//
//    @Override
//    public void showSomething() {
//        publisher();
//    }
//
////    @Override
////    public boolean isShow() {
////        return true;
////    }
////
////    @Override
////    public int order() {
////        return 0;
////    }
//
//    private void publisher() {
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "wsl2:9092");
//        props.put("acks", "0");
//        props.put("retries", 0);
//        props.put("batch.size", 16384);
//        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//
//        long lastTimestamp = System.currentTimeMillis();
//
//        Producer<String, String> producer = new KafkaProducer<>(props);
//        StringSupplier stringSupplier = new StringSupplier();
//        while (true) {
//            producer.send(new ProducerRecord<String, String>("KafkaTopic", System.currentTimeMillis() + "", stringSupplier.get()));
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if (System.currentTimeMillis() - lastTimestamp > 60000) {
//                break;
//            }
//        }
//        producer.close();
//    }
//}
