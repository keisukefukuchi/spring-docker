package com.example.demo.service.user;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public User getUserById(UUID id) {
    return userRepository.findById(id).orElse(null);
  }

  @Override
  public void saveUser(User user) {
    user.setCreatedAt(LocalDate.now());
    user.setUpdatedAt(LocalDate.now());

    userRepository.save(user);
  }

  @Override
  public void deleteUser(UUID id) {
    userRepository.deleteById(id);
  }
}
