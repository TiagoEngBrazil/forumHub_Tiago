package one.alura.forumHub.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import one.alura.forumHub.domain.autenticacaodeusuario.AutenticacaoRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AutenticacaoRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);

        // Verificar se a requisição é para o endpoint de cadastro de usuários
        if (request.getRequestURI().endsWith("/usuarios") && request.getMethod().equals("POST")) {
            // Permitir acesso livre sem autenticação para o endpoint de cadastro de usuários
            filterChain.doFilter(request, response);
            return;
        }

        // Lógica de autenticação normal para outros endpoints protegidos
        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);

            var autenticacaoDeUsuario = repository.findByLogin(subject);

            var authentication = new UsernamePasswordAuthenticationToken(autenticacaoDeUsuario, null,
                    autenticacaoDeUsuario.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }



    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "").trim();
        }

        return null;
    }


}
