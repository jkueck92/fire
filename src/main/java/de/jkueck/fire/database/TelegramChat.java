package de.jkueck.fire.database;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "telegram_chats")
public class TelegramChat extends BaseEntity {

    private String chatId;

    private String message;

    private boolean isEnabled = Boolean.FALSE;

    @Override
    public String toString() {
        return "TelegramChat{" +
                "chatId='" + chatId + '\'' +
                ", isEnabled=" + isEnabled +
                '}';
    }

}
