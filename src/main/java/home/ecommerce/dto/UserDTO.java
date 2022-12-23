package home.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO {
    @NotBlank(message = "Введите ваш логин")
    private String username;
    @NotBlank(message = "Введите ваш емейл")
    @Email(message = "Введите правильный емейл")
    private String email;
    @NotBlank(message = "Введите ваш пароль")
    @Size(min = 6, message = "Длина пароля должна быть от 6 символов")
    private String password;
    @NotBlank(message = "Повторно введите пароль")
    private String rpassword;
}
