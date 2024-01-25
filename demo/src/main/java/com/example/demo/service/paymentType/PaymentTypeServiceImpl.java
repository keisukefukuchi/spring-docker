package com.example.demo.service.paymentType;

import com.example.demo.entity.PaymentType;
import com.example.demo.repository.PaymentTypeRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentTypeServiceImpl implements PaymentTypeService {

  private final PaymentTypeRepository paymentTypeRepository;

  @Autowired
  public PaymentTypeServiceImpl(PaymentTypeRepository paymentTypeRepository) {
    this.paymentTypeRepository = paymentTypeRepository;
  }

  @Override
  public List<PaymentType> getAllPaymentTypes() {
    return paymentTypeRepository.findAll();
  }

  @Override
  public PaymentType getPaymentTypeById(UUID id) {
    return paymentTypeRepository.findById(id).orElse(null);
  }

  @Override
  public void savePaymentType(PaymentType paymentType) {
    paymentType.setCreatedAt(LocalDate.now());
    paymentType.setUpdatedAt(LocalDate.now());

    paymentTypeRepository.save(paymentType);
  }

  @Override
  public void deletePaymentType(UUID id) {
    paymentTypeRepository.deleteById(id);
  }
}
