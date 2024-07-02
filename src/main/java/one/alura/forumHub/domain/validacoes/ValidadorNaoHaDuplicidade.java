package one.alura.forumHub.domain.validacoes;

import one.alura.forumHub.domain.topico.TopicoRepository;
import one.alura.forumHub.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorNaoHaDuplicidade {
    private final TopicoRepository topicoRepository;

    @Autowired
    public ValidadorNaoHaDuplicidade(TopicoRepository topicoRepository) {
        this.topicoRepository = topicoRepository;
    }

    public void validarDuplicidade(String titulo, String mensagem) throws ValidacaoException {
        if (topicoRepository.existsByTituloAndMensagem(titulo, mensagem)) {
            throw new ValidacaoException("Já existe um tópico com o mesmo título e mensagem.");
        }
    }
}
