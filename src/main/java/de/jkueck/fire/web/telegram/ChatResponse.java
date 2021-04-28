package de.jkueck.fire.web.telegram;

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

}
