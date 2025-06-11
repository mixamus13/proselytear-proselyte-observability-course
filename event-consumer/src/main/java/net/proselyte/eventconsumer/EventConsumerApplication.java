package net.proselyte.eventconsumer;

import net.proselyte.avro.Event;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventConsumerApplication.class, args);
    }
}