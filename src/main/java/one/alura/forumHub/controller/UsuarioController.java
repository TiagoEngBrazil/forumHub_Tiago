package one.alura.forumHub.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import one.alura.forumHub.domain.usuario.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder) {


        var usuario = new Usuario(dados);
        repository.save(usuario);

        var uri = uriBuilder.path("usuarios/{id}").build(usuario.getId());

        return ResponseEntity.created(uri).body(new DadosDetalhamentoUsuario(usuario));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoUsuario dados, Authentication authentication) {
        var usuario = repository.getReferenceById(dados.id());

        if (usuario == null || !usuario.getEmail().equals(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        usuario.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id, Authentication authentication) {
        var usuario = repository.getReferenceById(id);

        if (usuario == null || !usuario.getEmail().equals(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        usuario.excluir();

        return ResponseEntity.noContent().build();
    }
}

