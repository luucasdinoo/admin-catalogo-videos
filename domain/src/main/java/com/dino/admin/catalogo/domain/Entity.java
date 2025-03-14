package com.dino.admin.catalogo.domain;

import com.dino.admin.catalogo.domain.event.DomainEvent;
import com.dino.admin.catalogo.domain.event.DomainEventPublisher;
import com.dino.admin.catalogo.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class Entity<ID extends Identifier> {

    protected final ID id;
    private final List<DomainEvent> domainEvents;

    public Entity(final ID id, List<DomainEvent> domainEvents) {
        Objects.requireNonNull(id, "'id' should not be null");
        this.id = id;
        this.domainEvents = new ArrayList<>(domainEvents == null ? Collections.emptyList() : domainEvents);
    }

    public abstract void validate(ValidationHandler handler);

    public ID getId() {
        return id;
    }

    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void publishDomainEvent(final DomainEventPublisher publisher){
        if (publisher == null){
            return;
        }
        getDomainEvents().forEach(publisher::publish);
        this.domainEvents.clear();
    }

    public void registerEvent(final DomainEvent event){
        if (event != null){
            this.domainEvents.add(event);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(getId(), entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
