package com.company.bot.model.schedule;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class Schedule {

    String weekDay;

    @JsonAlias("schedule")
    List<Subject> subjects;
}
