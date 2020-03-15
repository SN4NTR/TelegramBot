package com.company.bot.model.schedule;

import lombok.Data;

@Data
public class StudentGroup {

    Integer id;

    String name;

    Integer facultyId;

    String facultyName;

    Integer specialityDepartmentEducationFormId;

    String specialityName;

    Integer course;

    String calendarId;
}
