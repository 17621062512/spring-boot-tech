package com.ray.tech.service.observer.subjects;

import com.ray.tech.service.observer.Observer;
import com.ray.tech.service.observer.Subject;
import com.ray.tech.service.observer.obs.Customer;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReportSubject implements Subject {
    private String name;
    private List<Observer> obs = new ArrayList<>();

    @Override
    public void register(Observer ob) {
        obs.add(ob);
    }

    @Override
    public void removeOb(String name) {
        Observer observer = obs.stream()
                .filter(ob -> ob.getName().equals(name))
                .findFirst().orElse(new Customer(name));
        obs.remove(observer);
    }

    @Override
    public void notifyOb(String message) {
        for (Observer ob : obs) {
            ob.update(message);
        }
    }

    public ReportSubject() {
    }

    public ReportSubject(String name) {
        this.name = name;
    }

    @Override
    public void event(String message) {
        notifyOb(message);
    }
}
