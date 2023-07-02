package ru.practicum.main.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.practicum.main.user.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
@Accessors(chain = true)
public class UserDto {
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    public User toUser() {
        return new User()
                .setName(this.getName())
                .setEmail(this.getEmail());
    }
}
