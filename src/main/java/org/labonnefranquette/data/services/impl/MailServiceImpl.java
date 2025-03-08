package org.labonnefranquette.data.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.services.MailService;
import org.labonnefranquette.data.utils.PDFTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String mailUser;

    @Value("${spring.mail.password}")
    private String password;

    public MailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void setMailUser(String mailUser) {
        this.mailUser = mailUser;
    }

    @Override
    public void sendMailTo(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailUser);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
    }

    @Override
    public void sendMailWithAttachment(String to, String subject, String body, String attachementPath, String filename) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(mailUser);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);
        FileSystemResource file = new FileSystemResource(new File(attachementPath));
        helper.addAttachment(filename, file);
        emailSender.send(message);
    }

    @Override
    public void sendMailReceipt(String to, Paiement paiement, boolean seeDetails) throws IOException, MessagingException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
        String formattedDate = formatter.format(paiement.getDate());
        PDFTools pdfTools = PDFTools.getInstance();
        String filename = String.format("Commande_%d_%d.pdf", paiement.getId(), paiement.getCommande().getNumero());
        Path path = pdfTools.toPDF(paiement, filename, seeDetails);
        sendMailWithAttachment(to, "Votre facture du " + formattedDate + ".", "Votre facture du " + formattedDate + ".", path.toString(), filename);
        Files.delete(path);
    }
}
