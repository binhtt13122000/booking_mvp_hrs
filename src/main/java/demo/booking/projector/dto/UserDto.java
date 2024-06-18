package demo.booking.projector.dto;

import demo.booking.entities.User;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private Integer id;
    private String email;

    public static UserDto fromDomain(User user) {
        return UserDto.builder().email(user.getEmail()).id(user.getId()).build();
    }
}
