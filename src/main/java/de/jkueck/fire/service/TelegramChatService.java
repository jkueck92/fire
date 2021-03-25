package de.jkueck.fire.service;

import de.jkueck.fire.database.TelegramChat;
import de.jkueck.fire.database.repository.TelegramChatRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TelegramChatService implements ITelegramChatService {

    private final TelegramChatRepository telegramChatRepository;

    public TelegramChatService(TelegramChatRepository telegramChatRepository) {
        this.telegramChatRepository = telegramChatRepository;
    }

    public Optional<Set<TelegramChat>> getEnabledTelegramChats() {
        return this.telegramChatRepository.findByIsEnabled(Boolean.TRUE);
    }

    public Optional<Set<TelegramChat>> getDisabledTelegramChats() {
        return this.telegramChatRepository.findByIsEnabled(Boolean.FALSE);
    }

    @Override
    public Optional<TelegramChat> save(String chatId, String message, boolean isEnabled) {
        TelegramChat telegramChat = this.telegramChatRepository.save(new TelegramChat(chatId, message, isEnabled));
        return Optional.of(telegramChat);
    }

    @Override
    public Optional<TelegramChat> save(String chatId, String message) {
        TelegramChat telegramChat = this.telegramChatRepository.save(new TelegramChat(chatId, message, Boolean.FALSE));
        return Optional.of(telegramChat);
    }

    @Override
    public Optional<TelegramChat> findById(long id) {
        return this.telegramChatRepository.findById(id);
    }

    @Override
    public Optional<TelegramChat> update(long id, String message, boolean isEnabled) {
        Optional<TelegramChat> optionalTelegramChat = this.telegramChatRepository.findById(id);
        if (optionalTelegramChat.isPresent()) {
            optionalTelegramChat.get().setMessage(message);
            optionalTelegramChat.get().setEnabled(isEnabled);
            return Optional.of(this.telegramChatRepository.save(optionalTelegramChat.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Set<TelegramChat>> findAll() {
        Set<TelegramChat> telegramChats = new HashSet<>();
        this.telegramChatRepository.findAll().forEach(telegramChats::add);
        return Optional.of(telegramChats);
    }


}
