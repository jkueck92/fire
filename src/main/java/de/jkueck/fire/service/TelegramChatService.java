package de.jkueck.fire.service;

import de.jkueck.fire.database.TelegramChat;
import de.jkueck.fire.database.repository.TelegramChatRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class TelegramChatService {

    private final TelegramChatRepository telegramChatRepository;

    public TelegramChatService(TelegramChatRepository telegramChatRepository) {
        this.telegramChatRepository = telegramChatRepository;
    }

    /**
     * @param chatId
     * @return
     */
    public Optional<TelegramChat> getTelegramChatByChatId(final String chatId) {
        return this.telegramChatRepository.findByChatId(chatId);
    }

    /**
     * @return
     */
    public Optional<Set<TelegramChat>> getEnabledTelegramChats() {
        return this.telegramChatRepository.findByIsEnabled(Boolean.TRUE);
    }

    /**
     * @return
     */
    public Optional<Set<TelegramChat>> getDisabledTelegramChats() {
        return this.telegramChatRepository.findByIsEnabled(Boolean.FALSE);
    }

}
