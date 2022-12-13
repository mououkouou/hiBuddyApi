package com.example.hibuddy.api.application;

import com.example.hibuddy.api.domain.support.MailType;
import com.example.hibuddy.api.domain.support.EmailRedisKey;
import com.example.hibuddy.api.common.RedisService;
import com.example.hibuddy.api.interfaces.request.EmailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final RedisService redisService;

    @Transactional
    public void sendEmail(EmailRequest request) {
        MailType type = request.getType();
        String value = sendEmail(request, type);

        redisService.setValues(EmailRedisKey.getByType(type).getPrefix() + request.getEmail(), value);
    }


    private String sendEmail(EmailRequest request, MailType mailType) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            String value = Integer.toString((int)(Math.random() * (99999 - 10000 + 1)) + 10000);

            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            messageHelper.setTo(request.getEmail());
            messageHelper.setSubject(mailType.getText());
            messageHelper.setText(value);

            javaMailSender.send(mimeMessage);

            return value;
        } catch (MessagingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public boolean verifyEmailCode(String email, String code, MailType type) {
        boolean verified = redisService.getValues(EmailRedisKey.EMAIL_CONFIRM.getPrefix() + email).equals(code);

        if (verified) {
            redisService.deleteValues(EmailRedisKey.getByType(type).getPrefix() + email);
        }

        return verified;
    }
}
