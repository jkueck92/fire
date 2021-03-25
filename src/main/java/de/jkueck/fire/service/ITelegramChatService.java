package de.jkueck.fire.service;

import de.jkueck.fire.database.TelegramChat;

import java.util.Optional;
import java.util.Set;

public interface ITelegramChatService {

    Optional<Set<TelegramChat>> getEnabledTelegramChats();

    Optional<Set<TelegramChat>> getDisabledTelegramChats();

    Optional<TelegramChat> save(String chatId, String message, boolean isEnabled);

    Optional<TelegramChat> save(String chatId, String message);

    Optional<TelegramChat> findById(final long id);

    Optional<TelegramChat> update(final long id, final String message, final boolean isEnabled);

    Optional<Set<TelegramChat>> findAll();

}
