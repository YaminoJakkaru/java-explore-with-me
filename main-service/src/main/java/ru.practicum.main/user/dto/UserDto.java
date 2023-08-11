package ru.practicum.main.user.dto;


import lombok.Data;
import lombok.experimental.Accessors;
import ru.practicum.main.user.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@Accessors(chain = true)
public class UserDto {
    private long id;

    @NotBlank
    @Size(min = 2, max = 250)
    private String name;

    @NotBlank
    @Email
    @Size(min = 6, max = 254)
    private String email;

    public User toUser() {
        return new User()
                .setName(this.getName())
                .setEmail(this.getEmail());
    }
}
