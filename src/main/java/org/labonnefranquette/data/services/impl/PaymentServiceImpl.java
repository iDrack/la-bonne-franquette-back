package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.model.Order;
import org.labonnefranquette.data.model.Payment;
import org.labonnefranquette.data.model.PaymentType;
import org.labonnefranquette.data.repository.OrderRepository;
import org.labonnefranquette.data.repository.PaymentRepository;
import org.labonnefranquette.data.repository.PaymentTypeRepository;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.services.OrderService;
import org.labonnefranquette.data.services.PaymentService;
import org.labonnefranquette.data.utils.PDFTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    OrderService orderService;
    @Autowired
    RestaurantServiceImpl restaurantService;
    @Autowired
    private PaymentTypeRepository paymentTypeRepository;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment getById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Le Paiement n'existe pas."));
    }

    @Override
    public Payment create(Long orderId, Payment payment) throws RuntimeException {
        Order order = orderService.getById(orderId);
        payment.setOrder(order);
        payment.setRestaurant(order.getRestaurant());
        paymentRepository.save(payment);
        int paid = 0;
        String type = order.getPaymentType();

        for (Payment currentPaiement : order.getPayments()) {
            if (!Objects.equals(type, "AUCUN") && !Objects.equals(currentPaiement.getType(), type)) {
                order.setPaymentType("MIX");
            } else  {
                order.setPaymentType(currentPaiement.getType());
            }
            paid += currentPaiement.getPrice();
        }
        if (order.getTotalPrice() == paid) {
            order.setPaid(true);
        }
        orderRepository.save(order);
        return payment;
    }

    @Override
    public List<Payment> getAllByOrder(Long orderId) throws RuntimeException {
        return paymentRepository.findByOrderId(orderId).orElseThrow(() -> new RuntimeException("Aucun paiement n'éxiste pour cette commande."));
    }

    @Override
    public List<PaymentType> getAllPaymentType() {
        return paymentTypeRepository.findAll();
    }


    @Override
    public Path generatePDF(Payment payment) throws IOException {
        PDFTools pdfTools = PDFTools.getInstance();
        return pdfTools.toPDF(payment, "tmp.pdf", true);
    }
}
