package com.company.bot.service;

import com.company.bot.constant.IncomingMessageConstant;
import com.company.bot.constant.OutgoingMessageConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Slf4j
@Service
public class BotServiceImpl implements BotService {

    @Override
    public Optional<String> getIncomingMessage(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();

            if (message.hasText()) {
                String messageText = message.getText();
                return Optional.of(messageText);
            }
        }
        return Optional.empty();
    }

    @Override
    public SendMessage createOutgoingMessage(String incomingMessage, Update update) {
        Long chatId = update.getMessage().getChatId();
        String outgoingMessage = generateAnswer(incomingMessage);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(outgoingMessage);

        return message;
    }

    private String generateAnswer(String incomingMessage) {
        if (IncomingMessageConstant.HELLO.equals(incomingMessage.toLowerCase())) {
            return OutgoingMessageConstant.HELLO;
        }
        return OutgoingMessageConstant.DEFAULT_MESSAGE;
    }
}
