package one.alura.forumHub.domain.autenticacaodeusuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AutenticacaoDeUsuarioRepository extends JpaRepository<AutenticacaoDeUsuario, Long> {

    UserDetails findByLogin(String login);
}
