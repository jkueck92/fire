package de.jkueck.fire.web.telegram;

import de.jkueck.fire.database.TelegramChat;
import de.jkueck.fire.service.chat.TelegramChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final TelegramChatService telegramChatService;

    public ChatController(TelegramChatService telegramChatService) {
        this.telegramChatService = telegramChatService;
    }

    @GetMapping
    public ResponseEntity<ChatListResponse> getAllChats() {
        ChatListResponse telegramListResponse = new ChatListResponse();
        for (TelegramChat telegramChat : this.telegramChatService.getAll()) {
            telegramListResponse.add(new ChatResponse(telegramChat.getId(), telegramChat.getChatId(), telegramChat.isEnabled(), telegramChat.getMessage()));
        }
        return ResponseEntity.ok(telegramListResponse);
    }

    @PutMapping
    public ResponseEntity<ChatResponse> updateChat(@RequestBody ChatResponse chatUpdateRequest) {
        Optional<TelegramChat> optionalTelegramChat = this.telegramChatService.update(chatUpdateRequest);
        return optionalTelegramChat.map(this::buildResponseOk).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping
    public ResponseEntity<ChatResponse> saveChat(@RequestBody ChatResponse chatSaveRequest) {
        Optional<TelegramChat> optionalTelegramChat = this.telegramChatService.save(chatSaveRequest);
        return optionalTelegramChat.map(this::buildResponseOk).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatResponse> getChat(@PathVariable(name = "id") Long settingId) {
        Optional<TelegramChat> optionalTelegramChat = this.telegramChatService.findById(settingId);
        return optionalTelegramChat.map(this::buildResponseOk).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private ResponseEntity<ChatResponse> buildResponseOk(TelegramChat chat) {
        return ResponseEntity.ok(new ChatResponse(chat.getId(), chat.getChatId(), chat.isEnabled(), chat.getMessage()));
    }

}
