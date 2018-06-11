package com.ljyhust.pattern;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发布订阅模式实现
 * 频道与客户端的关系通过字典即map来维护
 * <p>伪代码实现</p>
 * <code>
 * def subscribe(client, channel):
 *     for channel in server.channels:
 *         server.channels[channel].append(client)
 *
 * def publish(channel, message):
 *     for client in server.channels[channel]:
 *         send_message(client, message)
 * </code>
 * @author ljyhust
 */
public class PublicSubscribePattern {

    private Map<ChannelTopic, List<Client>> channels = new HashMap<>();

    /**
     * 创建主题/频道
     * @param topicName
     * @return
     */
    public ChannelTopic createChannelTopic(String topicName) {
        ChannelTopic topic = new ChannelTopic();
        topic.topicName = topicName;
        topic.topicId = System.currentTimeMillis();
        if (!channels.containsKey(topic)) {
            channels.put(topic, new ArrayList<Client>());
        }
        return topic;
    }

    /**
     * 订阅主题
     * @param client
     * @param topic
     * @return
     */
    public boolean doSubscribe(Client client, ChannelTopic topic) {
        if (!channels.containsKey(topic)) {
            return false;
        }
        List<Client> clients = channels.get(topic);
        clients.add(client);
        return true;
    }

    /**
     * 发布消息
     * @param topic
     * @param message
     */
    public void publishMessage(ChannelTopic topic, Message message) {
        List<Client> clients = channels.get(topic);
        for (Client client: clients) {
            sendMessage(client, message);
        }
    }

    /**
     * 给某个客户端发送消息
     * @param client
     * @param message
     */
    public void sendMessage(Client client, Message message) {
        System.err.println(">>>>> send to " + client.name + "  message:  " + message.messageName);
    }

    /**
     * 定义客户端类，客户端名称、ip:port、创建时间等
     */
    class Client {
        String name;
        String ip;
        int port;
        String createTime;

    }

    /**
     * 订阅主题：频道
     */
    class ChannelTopic {
        String topicName;

        long topicId;

        public boolean equals(ChannelTopic obj) {
            return this.topicName.equals(obj.topicName);
        }
    }

    /**
     * 消息类
     */
    class Message {
        String messageName;
        Object obj;
    }

    @Test
    public void testMain() {
        ChannelTopic topic1 = createChannelTopic("topic_test_1");
        ChannelTopic topic2 = createChannelTopic("topic_test_2");
        Client client1 = new Client();
        client1.name = "client_1";

        Client client2 = new Client();
        client2.name = "client_2";

        Client client3 = new Client();
        client3.name = "client_3";

        doSubscribe(client1, topic1);
        doSubscribe(client2, topic1);
        doSubscribe(client3, topic2);

        Message message1 = new Message();
        message1.messageName = "message_test_publish_topic1";

        Message message2 = new Message();
        message2.messageName = "message_test_publish_topic2";

        publishMessage(topic1, message1);
        publishMessage(topic2, message2);
    }
}
