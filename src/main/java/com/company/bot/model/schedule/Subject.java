package com.company.bot.model.schedule;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class Subject {

    @JsonAlias("subject")
    String name;

    @JsonAlias("auditory")
    List<String> audiences;

    @JsonAlias("lessonType")
    String lessonType;

    @JsonAlias("lessonTime")
    String lessonTime;

    @JsonAlias("weekNumber")
    List<Integer> weekNumbers;

    @JsonAlias("numSubgroup")
    Integer subgroupNumber;

    @JsonAlias("studentGroup")
    List<String> studentGroups;

    @JsonAlias("studentGroupInformation")
    List<String> studentGroupInformation;

    @JsonAlias("startLessonTime")
    String lessonStarts;

    @JsonAlias("endLessonTime")
    String lessonEnds;

    @JsonAlias("note")
    String note;

    @JsonAlias("zaoch")
    Boolean educationForm;

    @JsonAlias("studentGroupModelList")
    String studentGroupModelList;

    @JsonAlias("gradebookLessonlist")
    String gradebookLessonlist;

    @JsonAlias("employee")
    List<Employee> employee;
}
