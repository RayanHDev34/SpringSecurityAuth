package com.example.SpringSecurityAuth.mapper;

import com.example.SpringSecurityAuth.dto.RentalRequest;
import com.example.SpringSecurityAuth.dto.RentalResponse;
import com.example.SpringSecurityAuth.entities.Rental;

public class RentalMapper {

    public static RentalResponse toDto(Rental rental) {
        if (rental == null) return null;

        return new RentalResponse(
                rental.getId(),
                rental.getOwnerId(),
                rental.getName(),
                rental.getDescription(),
                rental.getPrice(),
                rental.getSurface(),
                rental.getPicture(),
                rental.getCreatedAt(),
                rental.getUpdatedAt()
        );
    }

    public static Rental toEntity(RentalResponse dto) {
        if (dto == null) return null;

        Rental rental = new Rental();
        rental.setId(dto.getId());
        rental.setOwnerId(dto.getOwnerId());
        rental.setName(dto.getName());
        rental.setDescription(dto.getDescription());
        rental.setPrice(dto.getPrice());
        rental.setSurface(dto.getSurface());
        rental.setPicture(dto.getPicture());
        rental.setCreatedAt(dto.getCreatedAt());
        rental.setUpdatedAt(dto.getUpdatedAt());

        return rental;
    }
    public static Rental fromRequest(RentalRequest dto) {
        if (dto == null) return null;

        Rental rental = new Rental();
        rental.setName(dto.getName());
        rental.setDescription(dto.getDescription());
        rental.setPrice(dto.getPrice());
        rental.setSurface(dto.getSurface());
        return rental;
    }
    public static void updateEntityFromRequest(Rental rental, RentalRequest dto) {
        if (rental == null || dto == null) return;
        if (dto.getName() != null && !dto.getName().isBlank() && !dto.getName().equals(rental.getName())) {
            rental.setName(dto.getName());
        }
        if (dto.getDescription() != null && !dto.getDescription().isBlank() && !dto.getDescription().equals(rental.getDescription())) {
            rental.setDescription(dto.getDescription());
        }
        if (dto.getPrice() > 0 && dto.getPrice() != rental.getPrice()) {
            rental.setPrice(dto.getPrice());
        }
        if (dto.getSurface() > 0 && dto.getSurface() != rental.getSurface()) {
            rental.setSurface(dto.getSurface());
        }
    }
}
