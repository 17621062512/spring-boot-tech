package com.ray.tech.service.observer;

import com.ray.tech.service.observer.obs.Customer;
import com.ray.tech.service.observer.subjects.ReportSubject;
import org.springframework.stereotype.Service;

@Service
public class ObService {
    private static Subject subject = new ReportSubject("聚信立报告通知");

    public void notifyObs(String message) {
        subject.event(message);
    }

    public void register(String orgName) {
        subject.register(new Customer(orgName));
    }

    public void unregister(String orgName) {
        subject.removeOb(orgName);
    }
}
