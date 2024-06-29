package one.alura.forumHub.domain.topico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import one.alura.forumHub.domain.curso.Curso;
import one.alura.forumHub.domain.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titulo;

    String mensagem;

    LocalDateTime dataCriacao = LocalDateTime.now();

    Usuario autor;

    Curso curso;

    private List<Resposta> respostas = new ArrayList<>();

}
