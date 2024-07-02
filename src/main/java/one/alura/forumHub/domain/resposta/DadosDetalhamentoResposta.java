package one.alura.forumHub.domain.resposta;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import one.alura.forumHub.domain.topico.DadosDetalhamentoTopico;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DadosDetalhamentoResposta {

    private Long id;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private Boolean solucao;
    private DadosDetalhamentoTopico dadosDetalhamentoTopico;

    public DadosDetalhamentoResposta(Resposta resposta) {
        this.id = resposta.getId();
        this.mensagem = resposta.getMensagem();
        this.dataCriacao = resposta.getDataCriacao();
        this.solucao = resposta.getSolucao();
        this.dadosDetalhamentoTopico = new DadosDetalhamentoTopico(resposta.getTopico());
    }
}
