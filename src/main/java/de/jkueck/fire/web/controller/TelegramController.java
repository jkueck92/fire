package de.jkueck.fire.web.controller;

import de.jkueck.fire.database.TelegramChat;
import de.jkueck.fire.service.ITelegramChatService;
import de.jkueck.fire.web.entity.telegram.TelegramChatListResponse;
import de.jkueck.fire.web.entity.telegram.TelegramChatRequest;
import de.jkueck.fire.web.entity.telegram.TelegramChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
public class TelegramController {

    private final ITelegramChatService telegramChatService;

    public TelegramController(ITelegramChatService telegramChatService) {
        this.telegramChatService = telegramChatService;
    }

    @GetMapping("/telegram/chats")
    public ResponseEntity<TelegramChatListResponse> getAll() {
        Optional<Set<TelegramChat>> optionalTelegramChats = this.telegramChatService.findAll();
        if (optionalTelegramChats.isPresent()) {
            TelegramChatListResponse telegramChatListResponse = new TelegramChatListResponse();
            for (TelegramChat telegramChat : optionalTelegramChats.get()) {
                telegramChatListResponse.add(new TelegramChatResponse(telegramChat.getId(), telegramChat.getChatId(), telegramChat.getMessage(), telegramChat.isEnabled()));
            }
            return ResponseEntity.ok(telegramChatListResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/telegram/chats/{id}")
    public ResponseEntity<TelegramChatResponse> getById(@PathVariable(name = "id") long id) {
        Optional<TelegramChat> optionalTelegramChat = this.telegramChatService.findById(id);
        if (optionalTelegramChat.isPresent()) {
            return ResponseEntity.ok(new TelegramChatResponse(optionalTelegramChat.get().getId(), optionalTelegramChat.get().getChatId(), optionalTelegramChat.get().getMessage(), optionalTelegramChat.isPresent()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/telegram/chats")
    public ResponseEntity<TelegramChatResponse> add(@RequestBody TelegramChatRequest telegramChatRequest) {
        Optional<TelegramChat> optionalTelegramChat = this.telegramChatService.save(telegramChatRequest.getChatId(), telegramChatRequest.getMessage(), telegramChatRequest.isEnabled());
        if (optionalTelegramChat.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new TelegramChatResponse(optionalTelegramChat.get().getId(), optionalTelegramChat.get().getChatId(), optionalTelegramChat.get().getMessage(), optionalTelegramChat.isPresent()));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/telegram/chats/{id}/disable")
    public ResponseEntity<TelegramChatResponse> disableChat(@PathVariable(name = "id") long id) {
        Optional<TelegramChat> optionalTelegramChat = this.telegramChatService.findById(id);
        if (optionalTelegramChat.isPresent()) {
            Optional<TelegramChat> updatedTelegramChat = this.telegramChatService.update(optionalTelegramChat.get().getId(), optionalTelegramChat.get().getMessage(), Boolean.FALSE);
            if (updatedTelegramChat.isPresent()) {
                return ResponseEntity.accepted().body(new TelegramChatResponse(updatedTelegramChat.get().getId(), updatedTelegramChat.get().getChatId(), updatedTelegramChat.get().getMessage(), updatedTelegramChat.isPresent()));
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/telegram/chats/{id}/enable")
    public ResponseEntity<TelegramChatResponse> enableChat(@PathVariable(name = "id") long id) {
        Optional<TelegramChat> optionalTelegramChat = this.telegramChatService.findById(id);
        if (optionalTelegramChat.isPresent()) {
            Optional<TelegramChat> updatedTelegramChat = this.telegramChatService.update(optionalTelegramChat.get().getId(), optionalTelegramChat.get().getMessage(), Boolean.TRUE);
            if (updatedTelegramChat.isPresent()) {
                return ResponseEntity.accepted().body(new TelegramChatResponse(updatedTelegramChat.get().getId(), updatedTelegramChat.get().getChatId(), updatedTelegramChat.get().getMessage(), updatedTelegramChat.isPresent()));
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/telegram/chats/{id}")
    public ResponseEntity<TelegramChatResponse> update(@PathVariable(name = "id") long id, @RequestBody TelegramChatRequest telegramChatRequest) {
        Optional<TelegramChat> optionalTelegramChat = this.telegramChatService.update(id, telegramChatRequest.getMessage(), telegramChatRequest.isEnabled());
        if (optionalTelegramChat.isPresent()) {
            return ResponseEntity.accepted().body(new TelegramChatResponse(optionalTelegramChat.get().getId(), optionalTelegramChat.get().getChatId(), optionalTelegramChat.get().getMessage(), optionalTelegramChat.isPresent()));
        }
        return ResponseEntity.badRequest().build();
    }

}
