package webapp.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

import javax.jms.Queue;

@Configuration
@EnableJms
public class JmsConfig {
    static private final String BROKER_URL = "tcp://localhost:8161";
    static private final String BROKER_USERNAME = "admin";
    static private final String BROKER_PASSWORD = "admin";

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        var connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(BROKER_URL);
        connectionFactory.setUserName(BROKER_USERNAME);
        connectionFactory.setPassword(BROKER_PASSWORD);
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        var template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setDefaultDestinationName("sample.queue");
        return template;
    }

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("sample.queue");
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        return new MappingJackson2MessageConverter();
    }
}