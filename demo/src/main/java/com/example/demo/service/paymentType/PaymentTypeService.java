package com.example.demo.service.paymentType;

import com.example.demo.entity.Income;
import com.example.demo.entity.PaymentType;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface PaymentTypeService {
  List<PaymentType> getAllPaymentTypes();

  Page<PaymentType> getpaymentTypesByPage(int page, int size);

  PaymentType getPaymentTypeById(UUID id);

  void savePaymentType(PaymentType paymentType);

  void deletePaymentType(UUID id);
}
