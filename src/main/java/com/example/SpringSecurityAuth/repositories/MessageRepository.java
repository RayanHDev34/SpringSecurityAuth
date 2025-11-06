package com.example.SpringSecurityAuth.repositories;

import com.example.SpringSecurityAuth.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRentalId(Long rentalId);
    List<Message> findByUserId(Long userId);
}
