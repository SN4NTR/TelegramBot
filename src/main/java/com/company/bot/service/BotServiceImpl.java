package com.company.bot.service;

import com.company.bot.constant.IncomingMessageConstant;
import com.company.bot.constant.OutgoingMessageConstant;
import com.company.bot.converter.ScheduleConverter;
import com.company.bot.model.schedule.Employee;
import com.company.bot.model.schedule.Subject;
import com.company.bot.model.schedule.Schedule;
import com.company.bot.model.schedule.Semester;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.company.bot.constant.BsuirConstant.GET_GROUP_SCHEDULE;
import static com.company.bot.constant.BsuirConstant.GET_WEEK_NUMBER;
import static org.telegram.telegrambots.meta.api.methods.ActionType.TYPING;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BotServiceImpl implements BotService {

    private final RestTemplate restTemplate;
    private final ScheduleConverter scheduleConverter;

    @Override
    public Optional<Long> getChatId(Update update) {
        validateUpdate(update);

        Message message = update.getMessage();
        Long chatId = message.getChatId();
        return Optional.of(chatId);
    }

    @Override
    public Optional<String> getIncomingMessage(Update update) {
        validateUpdate(update);

        Message message = update.getMessage();

        validateUpdateMessage(message);

        String messageText = message.getText();
        return Optional.of(messageText);
    }

    @Override
    public SendMessage createOutgoingMessage(Long chatId, String incomingMessage) {
        String outgoingMessage = generateAnswer(incomingMessage);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(outgoingMessage);

        return message;
    }

    @Override
    public SendChatAction createChatAction(Long chatId) {
        SendChatAction chatAction = new SendChatAction();
        chatAction.setChatId(chatId);
        chatAction.setAction(TYPING);
        return chatAction;
    }

    @Override
    public ReplyKeyboardMarkup getKeyBoard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow row = new KeyboardRow();
        row.add("Get schedule");
        row.add("Get week number");
        List<KeyboardRow> keyboard = new ArrayList<>(List.of(row));
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        return keyboardMarkup;
    }

    private String generateAnswer(String incomingMessage) {
        switch (incomingMessage.toLowerCase()) {
            case IncomingMessageConstant.HELLO:
                return OutgoingMessageConstant.HELLO;

            case IncomingMessageConstant.WEEK_NUMBER:
                return getWeekNumber().toString();

            case IncomingMessageConstant.SCHEDULE:
                return getScheduleForWeek();

            default:
                return OutgoingMessageConstant.DEFAULT_MESSAGE;
        }
    }

    // TODO delete duplicates

    private String getScheduleForWeek() {
        int currentWeekNumber = getWeekNumber();
        String jsonSchedule = getSchedule();
        Semester semester = scheduleConverter.fromJson(jsonSchedule);
        StringBuilder result = new StringBuilder("Day of week: ");

        List<Schedule> schedules = semester.getSchedules();
        for (Schedule schedule : schedules) {
            List<Subject> subjects = schedule.getSubjects();
            String weekDay = schedule.getWeekDay();
            result.append(weekDay).append("\n");

            for (Subject subject : subjects) {
                List<Integer> weekNumbers = subject.getWeekNumbers();

                for (int weekNumber : weekNumbers) {
                    if (weekNumber == currentWeekNumber) {
                        String subjectName = subject.getName();
                        String lessonTime = subject.getLessonTime();
                        String lessonType = subject.getLessonType();
                        List<Employee> employees = subject.getEmployee();
                        String employeeName = employees.isEmpty() ? " " : employees.get(0).getFullName();

                        result.append("Subject: ").append(subjectName).append("\n")
                                .append("Time: ").append(lessonTime).append("\n")
                                .append("Type: ").append(lessonType).append("\n")
                                .append("Employee: ").append(employeeName).append("\n\n");

                        break;
                    }
                }
            }
        }

        return result.toString();
    }

    private String getFullSchedule() {
        String jsonSchedule = getSchedule();
        Semester semester = scheduleConverter.fromJson(jsonSchedule);
        return createMessageWithSchedule(semester);
    }

    private String getSchedule() {
        String urlWithGroup = GET_GROUP_SCHEDULE + "750701";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(urlWithGroup, String.class);
        return responseEntity.getBody();
    }

    private String createMessageWithSchedule(Semester semester) {
        List<Schedule> schedules = semester.getSchedules();
        StringBuilder result = new StringBuilder("Day of week: ");

        for (Schedule schedule : schedules) {
            String weekDay = schedule.getWeekDay();
            result.append(weekDay).append("\n");

            List<Subject> subjects = schedule.getSubjects();
            for (Subject subject : subjects) {
                String subjectName = subject.getName();
                String lessonTime = subject.getLessonTime();
                String lessonType = subject.getLessonType();
                List<Employee> employees = subject.getEmployee();
                String employeeName = employees.isEmpty() ? " " : employees.get(0).getFullName();
                List<Integer> weekNumbers = subject.getWeekNumbers();

                StringBuilder weekNumbersToString = new StringBuilder();
                for (Integer weekNumber : weekNumbers) {
                    weekNumbersToString.append(weekNumber).append(" ");
                }

                result.append("Subject: ").append(subjectName).append("\n")
                        .append("Time: ").append(lessonTime).append("\n")
                        .append("Type: ").append(lessonType).append("\n")
                        .append("Weeks: ").append(weekNumbersToString).append("\n")
                        .append("Employee: ").append(employeeName).append("\n\n");
            }
        }
        return result.toString();
    }

    private Integer getWeekNumber() {
        ResponseEntity<Integer> responseEntity = restTemplate.getForEntity(GET_WEEK_NUMBER, Integer.class);
        return responseEntity.getBody();
    }

    private void validateUpdate(Update update) {
        if (!update.hasMessage()) {
            throw new RuntimeException();
        }
    }

    private void validateUpdateMessage(Message message) {
        if (!message.hasText()) {
            throw new RuntimeException();
        }
    }
}
