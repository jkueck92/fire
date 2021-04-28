package de.jkueck.fire.service.chat;

import de.jkueck.fire.database.TelegramChat;
import de.jkueck.fire.web.telegram.ChatResponse;

import java.util.Optional;
import java.util.Set;

public interface TelegramChatService {

    Set<TelegramChat> getEnabledTelegramChats();

    Set<TelegramChat> getAll();

    Optional<TelegramChat> findById(long id);

    Optional<TelegramChat> update(ChatResponse chatUpdate);

    Optional<TelegramChat> save(ChatResponse chatSave);

}
