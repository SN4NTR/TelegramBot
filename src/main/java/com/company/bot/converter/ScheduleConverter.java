package com.company.bot.converter;

import com.company.bot.model.schedule.Semester;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ScheduleConverter {

    private final ObjectMapper objectMapper;

    public Semester fromJson(String json) {
        try {
            return objectMapper.readValue(json, Semester.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
