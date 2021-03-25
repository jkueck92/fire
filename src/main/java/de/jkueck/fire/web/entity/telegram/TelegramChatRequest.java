package de.jkueck.fire.web.entity.telegram;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TelegramChatRequest {

    private long id;

    private String chatId;

    private String message;

    private boolean isEnabled;

}
