package com.example.sj.service_implement;

import com.example.sj.entity.Payment;
import com.example.sj.repository.PaymentRepository;
import com.example.sj.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImplement implements PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Override
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }
    
    @Override
    public Optional<Payment> findById(Integer id) {
        return paymentRepository.findById(id);
    }
    
    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }
    
    @Override
    public Payment update(Integer id, Payment payment) {
        return paymentRepository.findById(id).map(existingPayment -> {
            Payment updatedPayment = Payment.builder()
                    .id(existingPayment.getId())
                    .order(payment.getOrder())
                    .paymentMethod(payment.getPaymentMethod())
                    .paymentStatus(payment.getPaymentStatus())
                    .transactionId(payment.getTransactionId())
                    .build();
            return paymentRepository.save(updatedPayment);
        }).orElseThrow(() -> new RuntimeException("Payment not found"));
    }
    
    @Override
    public void delete(Integer id) {
        paymentRepository.deleteById(id);
    }
}
