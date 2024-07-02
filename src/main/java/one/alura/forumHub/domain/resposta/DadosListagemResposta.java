package one.alura.forumHub.domain.resposta;

import java.time.LocalDateTime;

public record DadosListagemResposta(Long id, String mensagem, LocalDateTime dataCriacao, Boolean solução) {

    public DadosListagemResposta(Resposta resposta){
        this(resposta.getId(), resposta.getMensagem(), resposta.getDataCriacao(), resposta.getSolucao());
    }

}
