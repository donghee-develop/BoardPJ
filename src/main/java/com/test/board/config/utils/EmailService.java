package com.test.board.config.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import com.test.board.config.error.CustomException;
import com.test.board.config.error.ErrorCode;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${host_email}")
    private String fromEmail;

    public void sendTemporaryPasswordEmail(String toEmail, String tempPassword) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("임시 비밀번호");
            message.setText("임시 비밀번호는 다음과 같습니다: " + tempPassword);
            mailSender.send(message);
        } catch (MailException ex) {
            // 로그 남기기
            log.error("이메일 전송 실패: {}", ex.getMessage(), ex);
            // 사용자 정의 예외 던지기
            throw new CustomException(ErrorCode.EMAIL_SEND_FAILED);
        }
    }
}
