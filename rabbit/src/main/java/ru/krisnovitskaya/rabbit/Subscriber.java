package ru.krisnovitskaya.rabbit;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Subscriber {
    private final static String BLOG_EXCHANGE_NAME = "IT_BLOG";

    private Map<String, String> keysAndConsumerTags;
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private DeliverCallback deliverCallback;

    public Subscriber() throws Exception {

        keysAndConsumerTags = new HashMap<>();
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(BLOG_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

    }

    public void addNewKey(String key) throws IOException {

        if (!keysAndConsumerTags.containsKey(key)) {
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, BLOG_EXCHANGE_NAME, key);
            String tag = channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });
            keysAndConsumerTags.put(key, tag);
            System.out.println("Subscribed to " + key);
        } else {
            System.out.println("this key already exist");
        }
    }

    public void deleteKey(String key) throws IOException {
        if (keysAndConsumerTags.containsKey(key)) {
            channel.basicCancel(keysAndConsumerTags.get(key));
            System.out.println("Unsubscribed from " + key);
        } else {
            System.out.println("wrong key");
        }
    }


    public static void main(String[] argv) throws Exception {
        Subscriber subscriber = new Subscriber();
        Thread readConsole = new Thread(new Runnable() {
            @Override
            public void run() {
                String command = null;
                System.out.println("Welcome. Enter routing keys like a /add key or /del key");

                Scanner scanner = new Scanner(System.in);

                try {
                    while (true) {
                        command = scanner.nextLine();
                        //System.out.println(command);
                        if (command.startsWith("add")) {
                            String[] addingKey = command.split(" ");
                            subscriber.addNewKey(addingKey[1]);
                        }
                        if (command.startsWith("del")) {
                            String[] delKey = command.split(" ");
                            subscriber.deleteKey(delKey[1]);
                        }
                        if (command.equals("exit")) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        readConsole.setDaemon(true);
        readConsole.start();

    }
}

