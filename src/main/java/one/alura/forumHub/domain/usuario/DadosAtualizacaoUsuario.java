package one.alura.forumHub.domain.usuario;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoUsuario(

        @NotNull
        Long id,

        String nome,

        String email,

        String senha
) {

}
