package com.example.demo.service.paymentType;

import com.example.demo.entity.PaymentType;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface PaymentTypeService {
  List<PaymentType> getAllPaymentTypes();

  Page<PaymentType> getPaymentTypesByPage(int page, int size);

  PaymentType getPaymentTypeById(UUID id);

  void savePaymentType(PaymentType paymentType);

  void deletePaymentType(UUID id);
}
