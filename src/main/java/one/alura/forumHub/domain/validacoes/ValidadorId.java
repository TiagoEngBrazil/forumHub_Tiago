package one.alura.forumHub.domain.validacoes;

import one.alura.forumHub.infra.exception.ValidacaoException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidadorId {

    public <T, ID> void validar(ID id, JpaRepository<T, ID> repository) throws ValidacaoException {
        if (id == null || (id instanceof Long && (Long) id <= 0)) {
            throw new ValidacaoException("ID inválido. Forneça um ID válido.");
        }
        repository.findById(id).orElseThrow(() -> new ValidacaoException("ID não encontrado: " + id));
    }
}
