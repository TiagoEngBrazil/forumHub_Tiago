package one.alura.forumHub.domain.resposta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import one.alura.forumHub.domain.topico.Topico;

import java.time.LocalDateTime;

@Entity
@Table(name = "respostas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private Boolean solucao;

    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;

    public Resposta(DadosCadastroResposta dados, String autor, Topico topico) {
        this.mensagem = dados.getMensagem();
        this.dataCriacao = LocalDateTime.now();
        this.solucao = false;
        this.topico = topico;
    }
}
