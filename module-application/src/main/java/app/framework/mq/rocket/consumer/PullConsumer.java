///*
// * Licensed to the Apache Software Foundation (ASF) under one or more
// * contributor license agreements.  See the NOTICE file distributed with
// * this work for additional information regarding copyright ownership.
// * The ASF licenses this file to You under the Apache License, Version 2.0
// * (the "License"); you may not use this file except in compliance with
// * the License.  You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package app.framework.mq.rocket.consumer;
//
//import app.common.IShowCase;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
//import org.apache.rocketmq.client.consumer.PullResult;
//import org.apache.rocketmq.common.message.MessageQueue;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
//public class PullConsumer implements IShowCase {
//    private static final Logger logger = LogManager.getLogger(PullConsumer.class);
//
//    @Override
//    public void showSomething() {
//        try {
//            DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("group_name");
//            consumer.setNamesrvAddr("wsl2:9876");
//            consumer.start();
//
//            Map<MessageQueue, Long> offsetTable = new HashMap<MessageQueue, Long>();
//            Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("TopicTest");
//            for (MessageQueue mq : mqs) {
//                logger.info("Consume from the queue: {}", mq);
//                SINGLE_MQ:
//                while (true) {
//                    Long offset = offsetTable.get(mq);
//                    if (offset == null) offset = 0l;
//                    PullResult pullResult = consumer.pullBlockIfNotFound(mq, null, offset, 32);
//                    logger.info("%s", pullResult);
//                    offsetTable.put(mq, pullResult.getNextBeginOffset());
//                    switch (pullResult.getPullStatus()) {
//                        case FOUND:
//                            break;
//                        case NO_MATCHED_MSG:
//                            break;
//                        case NO_NEW_MSG:
//                            break SINGLE_MQ;
//                        case OFFSET_ILLEGAL:
//                            break;
//                        default:
//                            break;
//                    }
//                }
//            }
//            consumer.shutdown();
//        } catch (Exception e) {
//            logger.error(e);
//        }
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
//}
