package com.company.bot.model.schedule;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class Semester {

    String employee;

    @JsonAlias("studentGroup")
    StudentGroup studentGroup;

    List<Schedule> schedules;
}
