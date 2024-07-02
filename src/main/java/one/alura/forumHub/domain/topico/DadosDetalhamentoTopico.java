package one.alura.forumHub.domain.topico;

import one.alura.forumHub.domain.resposta.Resposta;

import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhamentoTopico(Long id, String titulo, String mensagem, LocalDateTime dataCriacao, String nomeAutor, String status, List<Resposta> respostas) {
    public DadosDetalhamentoTopico(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(),topico.getDataCriacao(),topico.getAutor(),topico.getStatus(), topico.getRespostas());
    }
}
