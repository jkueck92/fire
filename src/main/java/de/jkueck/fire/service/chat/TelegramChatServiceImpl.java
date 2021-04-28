package de.jkueck.fire.service.chat;

import de.jkueck.fire.database.TelegramChat;
import de.jkueck.fire.database.repository.TelegramChatRepository;
import de.jkueck.fire.web.telegram.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class TelegramChatServiceImpl implements TelegramChatService {

    private final TelegramChatRepository telegramChatRepository;

    public TelegramChatServiceImpl(TelegramChatRepository telegramChatRepository) {
        this.telegramChatRepository = telegramChatRepository;
    }

    @Override
    public Set<TelegramChat> getEnabledTelegramChats() {
        Optional<Set<TelegramChat>> optionalTelegramChats = this.telegramChatRepository.findByIsEnabled(Boolean.TRUE);
        return optionalTelegramChats.orElse(Collections.emptySet());
    }

    @Override
    public Set<TelegramChat> getAll() {
        return StreamSupport.stream(this.telegramChatRepository.findAll().spliterator(), Boolean.FALSE).collect(Collectors.toSet());
    }

    @Override
    public Optional<TelegramChat> findById(long id) {
        return this.telegramChatRepository.findById(id);
    }

    @Override
    public Optional<TelegramChat> update(ChatResponse chatUpdate) {
        Optional<TelegramChat> optionalTelegramChat = this.telegramChatRepository.findById(chatUpdate.getId());
        if (optionalTelegramChat.isPresent()) {
            optionalTelegramChat.get().setChatId(chatUpdate.getChatId());
            optionalTelegramChat.get().setEnabled(chatUpdate.getIsEnabled());
            optionalTelegramChat.get().setMessage(chatUpdate.getMessage());
            TelegramChat dbTelegramChat = this.telegramChatRepository.save(optionalTelegramChat.get());
            return Optional.of(dbTelegramChat);
        }
        return Optional.empty();
    }

    @Override
    public Optional<TelegramChat> save(ChatResponse chatSave) {
        TelegramChat telegramChat = new TelegramChat();
        telegramChat.setChatId(chatSave.getChatId());
        telegramChat.setEnabled(chatSave.getIsEnabled());
        telegramChat.setMessage(chatSave.getMessage());

        TelegramChat dbTelegramChat = this.telegramChatRepository.save(telegramChat);
        return Optional.of(dbTelegramChat);
    }

}
