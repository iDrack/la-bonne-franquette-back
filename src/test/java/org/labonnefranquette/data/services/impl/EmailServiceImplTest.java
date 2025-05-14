package org.labonnefranquette.data.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.utils.PDFTools;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {

    @Mock
    private JavaMailSender emailSender;

    @Mock
    private PDFTools pdfTools;

    @InjectMocks
    private MailServiceImpl mailService;

    @BeforeEach
    public void setUp() {
        mailService = new MailServiceImpl(emailSender);
        mailService.setMailUser("youremail@example.com");
    }

    @Test
    public void sendMailToSuccessfully() {
        String to = "recipient@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        mailService.sendMail(to, subject, body);

        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    public void sendMailWithAttachmentSuccessfully() throws MessagingException {
        String to = "recipient@example.com";
        String subject = "Test Subject";
        String body = "Test Body";
        String attachmentPath = "path/to/attachment";
        String filename = "attachment.txt";

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);

        mailService.sendTemplatedMail(to, subject, body, attachmentPath, filename);

        verify(emailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    public void sendMailWithAttachmentThrowsMessagingException() {
        String to = "recipient@example.com";
        String subject = "Test Subject";
        String body = "Test Body";
        String attachmentPath = "path/to/attachment";
        String filename = "attachment.txt";

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
        doThrow(new RuntimeException("Simulated exception")).when(emailSender).send(any(MimeMessage.class));

        assertThrows(RuntimeException.class, () -> {
            mailService.sendTemplatedMail(to, subject, body, attachmentPath, filename);
        });
    }
}