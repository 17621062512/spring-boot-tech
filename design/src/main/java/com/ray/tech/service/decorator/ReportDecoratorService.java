package com.ray.tech.service.decorator;

import com.ray.tech.service.decorator.decorator.Call;
import com.ray.tech.service.decorator.decorator.Nets;
import com.ray.tech.service.decorator.report.MiFengReport;
import com.ray.tech.service.decorator.report.MiGuanReport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportDecoratorService {

    public Report getReport(String reportType, List<String> types) {
        Report miFengReport;
        switch (reportType) {
            case "mifeng":
                miFengReport = new MiFengReport();
                break;
            case "miguan":
                miFengReport = new MiGuanReport();
                break;
            default:
                miFengReport = new MiFengReport();
                break;
        }
        for (String type : types) {
            decorate(miFengReport, type);
        }
        return miFengReport;
    }

    private void decorate(Report miFengReport, String type) {
        switch (type) {
            case "call":
                new Call(miFengReport);
                break;
            case "nets":
                new Nets(miFengReport);
                break;
            default:
        }
    }
}
