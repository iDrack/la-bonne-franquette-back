package org.labonnefranquette.data.services;

import jakarta.mail.MessagingException;
import org.labonnefranquette.data.model.Paiement;

import java.io.IOException;

public interface MailService {
    void sendMailTo(String to, String subject, String body);

    void sendMailWithAttachment(String to, String subject, String body, String attachementPath, String filename) throws MessagingException;

    void sendMailReceipt(String to, Paiement paiement) throws IOException, MessagingException;

    void setMailUser(String mailUser);
}
