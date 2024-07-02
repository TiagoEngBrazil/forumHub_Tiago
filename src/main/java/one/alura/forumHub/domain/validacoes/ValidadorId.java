package one.alura.forumHub.domain.validacoes;

import one.alura.forumHub.domain.topico.TopicoRepository;
import one.alura.forumHub.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorId {

    @Autowired
    TopicoRepository repository;

    public void validar(Long id) throws ValidacaoException {
        if (id == null || id <= 0) {
            throw new ValidacaoException("ID inválido. Forneça um ID válido.");
        }
        var topico=repository.findById(id).orElseThrow(() -> new ValidacaoException("ID não encontrado: " + id));
    }
}
