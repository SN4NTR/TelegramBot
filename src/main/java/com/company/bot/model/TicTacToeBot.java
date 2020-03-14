package com.company.bot.model;

import com.company.bot.service.BotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.company.bot.constant.BotConstant.BOT_NAME;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TicTacToeBot extends TelegramLongPollingBot {

    @Value("${bot.token}")
    private String botToken;

    private final BotService botService;

    @Override
    public void onUpdateReceived(Update update) {
        String incomingMessage = botService.getIncomingMessage(update).orElseThrow();
        SendMessage outgoingMessage = botService.createOutgoingMessage(incomingMessage, update);
        sendMessage(outgoingMessage);
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    private void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            String errorMessage = e.getMessage();
            log.error(errorMessage);
        }
    }
}
