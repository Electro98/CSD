package webapp.JMS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    private final JmsTemplate jmsTemplate;

    @Autowired
    public Producer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(String string) {
        jmsTemplate.convertAndSend(string);
        System.out.println("Message sent to the Queue");
    }
}
