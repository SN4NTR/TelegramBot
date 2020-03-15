package com.company.bot.model.schedule;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class Employee {

    @JsonAlias("id")
    Integer id;

    @JsonAlias("firstName")
    String firstName;

    @JsonAlias("middleName")
    String secondName;

    @JsonAlias("lastName")
    String lastName;

    @JsonAlias("fio")
    String fullName;

    @JsonAlias("rank")
    String rank;

    @JsonAlias("photoLink")
    String photoLink;

    @JsonAlias("calendarId")
    String calendarId;

    @JsonAlias("academicDepartment")
    List<String> academicDepartment;
}
