package com.todolist.todolist.email;



import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService{
    
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("phanhoangduchuyy@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // Set the body as HTML content

            mailSender.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String buildEmailConfirm(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "    <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p>" +
                "    <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Thank you for registering. Please click on the below link to activate your account:</p>" +
                "    <blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\">" +
                "        <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"><a href=\"" + link + "\">Activate Now</a></p>" +
                "    </blockquote>\n" +
                "    <p>Link will expire in 15 minutes.</p>" +
                "    <p>See you soon</p>" +
                "</div>";
    }

    @Override
    public String buildEmailResetPassword(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
        "    <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p>" +
        "    <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">You have requested to reset your password. Please click on the link below to reset your password:</p>" +
        "    <blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\">" +
        "        <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"><a href=\"" + link + "\">Reset Password</a></p>" +
        "    </blockquote>\n" +
        "    <p>Link will expire in 15 minutes.</p>" +
        "    <p>See you soon</p>" +
        "</div>";
    }
}
