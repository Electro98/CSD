package webapp.JMS;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;

public class Consumer {
    @JmsListener(destination = "sample.queue")
    public void receive(@Payload String string) {
        System.out.println("Received: " + string);
    }
}
