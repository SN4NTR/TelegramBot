package com.company.bot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public interface BotService {

    Optional<String> getIncomingMessage(Update update);

    SendMessage createOutgoingMessage(String incomingMessage, Update update);
}
