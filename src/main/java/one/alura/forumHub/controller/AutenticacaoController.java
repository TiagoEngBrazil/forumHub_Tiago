package one.alura.forumHub.controller;

import jakarta.validation.Valid;
import one.alura.forumHub.domain.autenticacaodeusuario.AutenticacaoDeUsuario;
import one.alura.forumHub.domain.autenticacaodeusuario.DadosAutenticcao;
import one.alura.forumHub.infra.security.DadosTokenJWT;
import one.alura.forumHub.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLongin(@RequestBody @Valid DadosAutenticcao dados) {


        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((AutenticacaoDeUsuario) authentication.getPrincipal());


        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

}
