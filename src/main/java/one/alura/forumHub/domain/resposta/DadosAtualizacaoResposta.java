package one.alura.forumHub.domain.resposta;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoResposta (
        @NotBlank
        String mensagem
) {}
