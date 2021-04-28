package de.jkueck.fire.web.telegram;

import de.jkueck.fire.database.TelegramChat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {

    private long id;

    private String chatId;

    private Boolean isEnabled;

    private String message;

    public ChatResponse(TelegramChat chat) {
        this.id = chat.getId();
        this.chatId = chat.getChatId();
        this.isEnabled = chat.isEnabled();
        this.message = chat.getMessage();
    }

}
