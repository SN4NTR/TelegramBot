package com.company.bot.model.bot;

import com.company.bot.service.BotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.company.bot.constant.BotConstant.BOT_NAME;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ScheduleBot extends TelegramLongPollingBot {

    @Value("${bot.token}")
    private String botToken;

    private final BotService botService;

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = botService.getChatId(update).orElseThrow();
        String incomingMessage = botService.getIncomingMessage(update).orElseThrow();
        SendMessage outgoingMessage = botService.createOutgoingMessage(chatId, incomingMessage);
        SendChatAction chatAction = botService.createChatAction(chatId);
        ReplyKeyboardMarkup keyboardMarkup = botService.getKeyBoard();
        outgoingMessage.setReplyMarkup(keyboardMarkup);
        sendMessage(chatAction, outgoingMessage);
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    private void sendMessage(SendChatAction chatAction, SendMessage message) {
        try {
            execute(chatAction);
            execute(message);
        } catch (TelegramApiException e) {
            String errorMessage = e.getMessage();
            log.error(errorMessage);
        }
    }
}
