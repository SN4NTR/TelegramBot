package com.company.bot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.Optional;

public interface BotService {

    Optional<Long> getChatId(Update update);

    Optional<String> getIncomingMessage(Update update);

    SendMessage createOutgoingMessage(Long chatId, String incomingMessage);

    SendChatAction createChatAction(Long chatId);

    ReplyKeyboardMarkup getKeyBoard();
}
