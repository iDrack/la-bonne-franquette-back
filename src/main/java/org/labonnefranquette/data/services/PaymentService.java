package org.labonnefranquette.data.services;

import org.labonnefranquette.data.model.Payment;
import org.labonnefranquette.data.model.PaymentType;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface PaymentService {
    List<Payment> getAll();

    Payment getById(Long id);

    Payment create(Long orderId, Payment payment) throws RuntimeException;

    List<Payment> getAllByOrder(Long orderId) throws RuntimeException;

    Path generatePDF(Payment payment) throws IOException;

    List<PaymentType> getAllPaymentType();
}
