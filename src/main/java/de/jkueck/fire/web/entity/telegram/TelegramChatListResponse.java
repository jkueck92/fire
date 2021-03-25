package de.jkueck.fire.web.entity.telegram;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TelegramChatListResponse {

    private Set<TelegramChatResponse> telegramChats = new HashSet<>();

    public void add(TelegramChatResponse telegramChatResponse) {
        this.telegramChats.add(telegramChatResponse);
    }

}
