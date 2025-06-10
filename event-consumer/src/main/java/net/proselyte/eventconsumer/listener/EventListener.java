package net.proselyte.eventconsumer.listener;

import net.proselyte.avro.Event;
import net.proselyte.eventconsumer.model.EventEntity;
import net.proselyte.eventconsumer.repository.EventRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventListener {

    private static final Logger log = LoggerFactory.getLogger(EventListener.class);

    private final EventRepository repository;

    public EventListener(EventRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "events", groupId = "event-group")
    public void listen(Event event) {
        try {
            // Добавляем поля в MDC для структурированных логов
            MDC.put("uid", event.getUid());
            MDC.put("subject", event.getSubject());
            MDC.put("description", event.getDescription());

            log.info("Received event");

            EventEntity entity = new EventEntity();
            entity.setUid(event.getUid());
            entity.setSubject(event.getSubject());
            entity.setDescription(event.getDescription());

            repository.save(entity);

            log.info("Saved entity");
        } finally {
            MDC.clear(); // Важно очищать MDC после логирования
        }
    }
}
