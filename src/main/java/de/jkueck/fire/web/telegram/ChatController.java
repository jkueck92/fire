package de.jkueck.fire.web.telegram;

import de.jkueck.fire.database.TelegramChat;
import de.jkueck.fire.service.chat.TelegramChatService;
import de.jkueck.fire.web.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/chats")
public class ChatController extends BaseController {

    private final TelegramChatService telegramChatService;

    public ChatController(TelegramChatService telegramChatService) {
        this.telegramChatService = telegramChatService;
    }

    @GetMapping
    public ResponseEntity<ChatListResponse> getAll(@RequestParam(name = "sort", required = false) String sort) {
        ChatListResponse telegramListResponse = new ChatListResponse();
        for (TelegramChat telegramChat : this.telegramChatService.getAll(this.getSortParameter(sort))) {
            telegramListResponse.add(new ChatResponse(telegramChat));
        }
        return ResponseEntity.ok(telegramListResponse);
    }

    @PutMapping
    public ResponseEntity<ChatResponse> update(@RequestBody ChatResponse chatUpdateRequest) {
        Optional<TelegramChat> optionalTelegramChat = this.telegramChatService.update(chatUpdateRequest);
        return optionalTelegramChat.map(this::buildResponseOk).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping
    public ResponseEntity<ChatResponse> save(@RequestBody ChatResponse chatSaveRequest) {
        Optional<TelegramChat> optionalTelegramChat = this.telegramChatService.save(chatSaveRequest);
        return optionalTelegramChat.map(this::buildResponseOk).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatResponse> get(@PathVariable(name = "id") Long settingId) {
        Optional<TelegramChat> optionalTelegramChat = this.telegramChatService.findById(settingId);
        return optionalTelegramChat.map(this::buildResponseOk).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private ResponseEntity<ChatResponse> buildResponseOk(TelegramChat chat) {
        return ResponseEntity.ok(new ChatResponse(chat));
    }

}
