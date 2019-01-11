package com.lsm;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

/**
 * kafka消费者
 */
public class Constmer {
    private static String TOPIC_LSM = "lsm";
    public static void main(String[] args) {
        Properties properties = new Properties();

        //设置kafka的服务信息 host:post
        properties.put("bootstrap.servers", "172.16.169.149:9092");
        //设置序列化器
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        //设置群组ID
        properties.put("group.id","lsmGroup");

        //通过订阅主题来读取消息
//        readMsg(properties);

        //通过自己分配分区来读取消息（不用考虑再分配，和消费者群组）
        readMsgByCustomerPart(properties);
    }

    public static void readMsgByCustomerPart(Properties properties){
        KafkaConsumer<String,String> consumer = new KafkaConsumer(properties);
        List<PartitionInfo> PartitionInfos =  consumer.partitionsFor("lsm");

        List<TopicPartition> topicPartitions = new ArrayList<>();
        if(PartitionInfos != null && PartitionInfos.size() > 0){
            for(PartitionInfo it : PartitionInfos){
                topicPartitions.add(new TopicPartition(TOPIC_LSM,it.partition()));
            }
        }
        //给消费者分配可用分区
        consumer.assign(topicPartitions);
        commonLoop(consumer,"CustomerPart");
    }

    /**
     * 轮询读取消息
     */
    public static void readMsg(Properties properties){
        KafkaConsumer<String,String> consumer = new KafkaConsumer(properties);
        consumer.subscribe(Collections.singleton("lsm"));
        commonLoop(consumer,"normal");
    }

    /**
     * 公共循环体
     * @param consumer
     * @param source
     */
    public static void commonLoop(KafkaConsumer<String,String> consumer,String source){
        while (true){
            ConsumerRecords<String, String> records = consumer.poll(100);

            /*从指定偏移量位置读取消息
            Set<TopicPartition> partitions = consumer.assignment();
            for(TopicPartition item : partitions){
                consumer.seekToBeginning(Collections.singleton(item)); // 从指定分区的开始出开始读
                consumer.seekToEnd(Collections.singleton(item));    //从指定的分区尾部开始读
                System.out.println("the position is : " + consumer.position(item));
                System.out.println("the end position is : " + consumer.endOffsets(Collections.singleton(item)));
            }*/

            if(records != null && records.count() != 0){
                for(ConsumerRecord<String,String> it : records){
                    System.out.println("the topic from " + source + " is : " + it.topic());
                    System.out.println("    the part " + source + " is : " + it.partition());
                    System.out.println("          the msg " + source + " is : " + it.value());
                    System.out.println("             the offset " + source + " is : " + it.offset());
                    System.out.println("                  the header " + source + " is : " + it.headers().toString());


                }
            }
            consumer.commitAsync(new OffsetCommitCallback() {
                @Override
                public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {
                    System.out.println(source + "commit offset success ...");
                }
            });
        }
    }
}
