package com.company.bot.constant;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class BsuirConstant {

    public static final String GET_WEEK_NUMBER = "https://journal.bsuir.by/api/v1/week";

    public static final String GET_GROUP_SCHEDULE = "https://journal.bsuir.by/api/v1/studentGroup/schedule?studentGroup=";
}
