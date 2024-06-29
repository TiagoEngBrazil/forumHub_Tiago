package one.alura.forumHub.domain.topico;

import jakarta.persistence.*;
import lombok.*;
import one.alura.forumHub.domain.usuario.Usuario;

import java.time.LocalDateTime;

@Entity(name = "Resposta")
@Table(name= "resposta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensagem;
    private LocalDateTime data_criacao;
    private boolean solucao;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;

}
