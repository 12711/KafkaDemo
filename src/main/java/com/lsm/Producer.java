package com.lsm;


import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Producer {

    public static void main(String[] args) {
        Properties properties = new Properties();
        //设置kafka的服务信息 host:post
        properties.put("bootstrap.servers", "172.16.169.149:9092");
        //设置序列化器
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //多少个broker确认消息发送成功了才代表消息发送成功
        properties.put("acks","all");

        //自定义分区
        properties.put("partitioner.class", "com.lsm.PartitionerCustmer");

        org.apache.kafka.clients.producer.Producer producer = new KafkaProducer<String, String>(properties);

        ProducerRecord<String, String> record = new ProducerRecord("lsm", "what are you 弄啥呢 Roman");
        try {
            producer.send(record, new KafkaCallback()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    public static class KafkaCallback implements Callback {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            System.out.println("回调中 ：" + recordMetadata.toString());
            if (e != null) {
                System.out.println("异常了");
                e.printStackTrace();
            }
        }
    }
}
