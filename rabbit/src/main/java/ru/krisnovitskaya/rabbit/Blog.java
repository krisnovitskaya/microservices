package ru.krisnovitskaya.rabbit;


import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

// Домашнее задание:
// 1. Сделайте два консольных приложения (не Спринг):
//   а. IT-блог, который публикует статьи по языкам программирования
//   б. подписчик, которого интересуют статьи по определенным языкам
// 2. Сделайте возможность публиковать "статьи" по темам
// 3. * Сделайте возможность клиентов подписываться и отписываться от статей по темам
public class Blog {
    private final static String BLOG_EXCHANGE_NAME = "IT_BLOG";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){

            channel.exchangeDeclare(BLOG_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            for (Map.Entry<String, String> entry : articles.entrySet()) {
                Thread.sleep(4000);
                channel.basicPublish(BLOG_EXCHANGE_NAME, entry.getKey(), null, entry.getValue().getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + entry.getKey() + "':'" + entry.getValue() + "'");
            }
        }
    }

    private static Map<String, String> articles = new HashMap<String, String>(){{
        put("java.hibernate", "i love Hibernate");
        put("java.nio", "i love nio");
        put("c++.true coding", "like a Bill Gates");
        put("python.big data", "my data is big");
        put("c#.coding", "java programmers wear glasses cus they don`t C#");
        put("java.spring", "i love Spring");
        put("java.simple start", "Begin Java Programming");
        put("python.super big data", "my data is very big");
        put("php.fast start", "begin PHP");
        put("pascal.turbo pascal","don`t forget");

    }};
}

