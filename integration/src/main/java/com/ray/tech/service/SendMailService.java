package com.ray.tech.service;

import com.ray.tech.config.AsyncConfig;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import java.util.Objects;

@Service
public class SendMailService {

    @Resource
    private JavaMailSenderImpl mailSender;

    @Async(AsyncConfig.PUSH_ASYNC_EXECUTOR)
    public void sendSimpleTextMail(String subject, String text, String... sendTo) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(Objects.requireNonNull(mailSender.getUsername()));
        msg.setSubject(subject);
        msg.setTo(sendTo);
        msg.setText(text);
        this.mailSender.send(msg);
    }

    @Async(AsyncConfig.PUSH_ASYNC_EXECUTOR)
    public void sendHtmlMail(String subject, String html, String... sendTo) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(Objects.requireNonNull(mailSender.getUsername()));
        helper.setSubject(subject);
        helper.setTo(sendTo);
        helper.setText(html, true);
        mailSender.send(message);
    }

}
