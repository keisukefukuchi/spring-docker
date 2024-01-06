package com.example.demo.service.paymentType;

// PaymentTypeService.java
import com.example.demo.entity.PaymentType;

import java.util.List;
import java.util.UUID;

public interface PaymentTypeService {
    List<PaymentType> getAllPaymentTypes();

    PaymentType getPaymentTypeById(UUID id);

    void savePaymentType(PaymentType paymentType);

    void deletePaymentType(UUID id);
}
