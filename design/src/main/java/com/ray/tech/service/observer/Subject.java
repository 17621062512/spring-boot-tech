package com.ray.tech.service.observer;

public interface Subject {
    void register(Observer ob);

    void removeOb(String ob);

    void notifyOb(String message);

    void event(String message);
}
