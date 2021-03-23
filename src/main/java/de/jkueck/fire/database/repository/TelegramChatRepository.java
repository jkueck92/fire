package de.jkueck.fire.database.repository;

import de.jkueck.fire.database.TelegramChat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TelegramChatRepository extends CrudRepository<TelegramChat, Long> {

    Optional<TelegramChat> findByChatId(final String chatId);

    Optional<Set<TelegramChat>> findByIsEnabled(final boolean isEnabled);

}
