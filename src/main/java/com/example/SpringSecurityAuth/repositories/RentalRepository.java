package com.example.SpringSecurityAuth.repositories;

import com.example.SpringSecurityAuth.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {}
