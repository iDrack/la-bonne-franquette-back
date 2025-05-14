package org.labonnefranquette.data.services;

import jakarta.mail.MessagingException;
import org.labonnefranquette.data.model.Payment;

import java.io.IOException;

public interface MailService {
    void sendMail(String to, String subject, String body);

    void sendTemplatedMail(String to, String subject, String body, String attachementPath, String filename) throws MessagingException;

    void sendMailReceipt(String to, Payment paiement, boolean seeDetails) throws IOException, MessagingException;

    void setMailUser(String mailUser);
}
