package com.pickupluck.ecogging.util.mail;

import com.pickupluck.ecogging.domain.user.dto.EmailAuthNumberConfirmRequest;
import com.pickupluck.ecogging.util.InMemoryKeyValueStore;
import com.pickupluck.ecogging.util.SecurityUtil;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final InMemoryKeyValueStore keyValueStore;

    private static final String AUTH_MAIL_SENDER = "no_reply@ecogging.com";
    private static final String AUTH_MAIL_TITLE = "[ecogging] 회원가입 인증 메일입니다.";

    @Transactional
    public boolean sendSignUpAuthMail(String to, String content) {
        keyValueStore.put(to, content);
        return sendEmail(AUTH_MAIL_SENDER, to, AUTH_MAIL_TITLE, content);
    }

    @Transactional
    public boolean sendEmail(String from, String to, String title, String content) {
        // send mail
        MimeMessage mimeMailMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, true, "UTF-8");
            helper.setFrom("qaws2051@gmail.com");
            helper.setTo(to);
            helper.setSubject(title);
            String text = """
                <html>
                <body>
                    <h3>회원가입 이메일 인증번호입니다. </h3>
                    <hr />
                    <br />
                    <h1 style="color: #00f";">%s</h1>
                    <br />
                    <hr />
                </body>
                </html>
                """.formatted(content);
            helper.setText(text, true);
            mailSender.send(mimeMailMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean confirmEmail(EmailAuthNumberConfirmRequest emailAuthConfirmRequest) {
        String userEmail = emailAuthConfirmRequest.getEmail();
        String userAuthNumber = emailAuthConfirmRequest.getAuthNumber();

        String answer = keyValueStore.get(userEmail);

        return userAuthNumber.equals(answer);
    }
}
