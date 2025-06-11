package net.proselyte.eventproducer;

import net.proselyte.avro.Event;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;

@SpringBootApplication
@EnableScheduling
public class EventProducerApplication implements CommandLineRunner {

    private final KafkaTemplate<String, Event> kafkaTemplate;

    @Value("${event.topic}")
    private String topic;

    public EventProducerApplication(KafkaTemplate<String, Event> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(EventProducerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Avro Event producer started");
    }

    @Scheduled(fixedRateString = "${event.generation.interval-ms}")
    public void sendEvent() {
        Event event = Event.newBuilder()
                .setUid(UUID.randomUUID().toString())
                .setSubject("subject")
                .setDescription("description")
                .build();

        kafkaTemplate.send(new ProducerRecord<>(topic, event.getUid(), event));
        System.out.println("Sent: " + event);
    }
}
