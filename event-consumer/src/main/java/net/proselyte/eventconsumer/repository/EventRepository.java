package net.proselyte.eventconsumer.repository;

import net.proselyte.eventconsumer.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
}