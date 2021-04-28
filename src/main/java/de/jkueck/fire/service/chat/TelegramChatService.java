package de.jkueck.fire.service.chat;

import de.jkueck.fire.database.TelegramChat;
import de.jkueck.fire.web.telegram.ChatResponse;
import org.springframework.data.domain.Sort;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public interface TelegramChatService {

    Set<TelegramChat> getEnabledTelegramChats();

    LinkedHashSet<TelegramChat> getAll(Sort sort);

    Optional<TelegramChat> findById(long id);

    Optional<TelegramChat> update(ChatResponse chatUpdate);

    Optional<TelegramChat> save(ChatResponse chatSave);

}
