package com.example.SpringSecurityAuth.mapper;

import com.example.SpringSecurityAuth.dto.MessageRequest;
import com.example.SpringSecurityAuth.entities.Message;

public class MessageMapper {

    public static Message fromRequest(MessageRequest dto) {
        if (dto == null) return null;

        Message message = new Message();
        message.setRentalId(dto.getRental_id());
        message.setUserId(dto.getUser_id());
        message.setMessage(dto.getMessage());

        return message;
    }
}
