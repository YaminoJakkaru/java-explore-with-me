package ru.practicum.main.user.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;
import ru.practicum.main.user.dto.UserDto;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Objects;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 250)
    private String name;

    @Email
    @Column(name = "email", nullable = false, unique = true, length = 254)
    private String email;

    public UserDto toUserDto() {
        return new UserDto()
                .setId(this.getId())
                .setName(this.getName())
                .setEmail(this.getEmail());
    }
}
