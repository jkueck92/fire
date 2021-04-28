package de.jkueck.fire.database;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "users")
public class User extends BaseEntity {

    private String username;

    private String password;

}
