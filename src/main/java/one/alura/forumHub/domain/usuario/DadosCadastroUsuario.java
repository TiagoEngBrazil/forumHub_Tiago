package one.alura.forumHub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroUsuario(

        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email,

        @Pattern(
                regexp = "^[A-Za-z\\d]{6}$",
                message = "A senha deve conter exatamente 6 caracteres, podendo ser letras e/ou números"
        )
        String senha

) {

}
