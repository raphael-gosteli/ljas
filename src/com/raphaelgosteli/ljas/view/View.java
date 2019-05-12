package com.raphaelgosteli.ljas.view;

import com.raphaelgosteli.ljas.handler.EventHandler;

import java.util.HashSet;
import java.util.Set;

public abstract class View {
    private String content;
    private Set<EventHandler> eventHandlers = new HashSet<>();

    /**
     * Gets content.
     *
     * @return Value of content.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets new content.
     *
     * @param content New value of content.
     */
    public void setContent(String content) {
        this.content = content;
    }

    public Set<EventHandler> getEventHandlers() {
        return eventHandlers;
    }

    public void setEventHandlers(Set<EventHandler> eventHandlers) {
        this.eventHandlers = eventHandlers;
    }

    public void addEventHandler(EventHandler eventHandler) {
        this.eventHandlers.add(eventHandler);
    }
}
