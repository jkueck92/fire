package de.jkueck.fire;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMessage {

    @JsonProperty("chat_id")
    private String chatId;

    private String text;

}