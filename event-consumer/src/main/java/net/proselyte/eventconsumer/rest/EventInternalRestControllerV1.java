package net.proselyte.eventconsumer.rest;

import net.proselyte.eventconsumer.model.EventEntity;
import net.proselyte.eventconsumer.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/internal/api/v1/events")
public class EventInternalRestControllerV1 {

    private static final Logger log = LoggerFactory.getLogger(EventInternalRestControllerV1.class);

    private final EventRepository repository;

    public EventInternalRestControllerV1(EventRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{uid}")
    public ResponseEntity<EventEntity> getByUid(@PathVariable String uid) {
        MDC.put("uid", uid);
        try {
            log.info("Fetching event from DB");

            Optional<EventEntity> entity = repository.findById(uid);

            return entity
                    .map(e -> {
                        MDC.put("subject", e.getSubject());
                        MDC.put("description", e.getDescription());

                        log.info("Found event in DB");
                        return ResponseEntity.ok(e);
                    })
                    .orElseGet(() -> {
                        log.warn("Event not found");
                        return ResponseEntity.notFound().build();
                    });
        } finally {
            MDC.clear();
        }
    }
}
