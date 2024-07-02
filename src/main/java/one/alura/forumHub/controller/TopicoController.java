package one.alura.forumHub.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import one.alura.forumHub.domain.topico.*;
import one.alura.forumHub.domain.validacoes.ValidadorId;
import one.alura.forumHub.domain.validacoes.ValidadorNaoHaDuplicidade;
import one.alura.forumHub.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @Autowired
    private ValidadorNaoHaDuplicidade validadorNaoHaDuplicidade;

    @Autowired
    private ValidadorId validadorId;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuilder,
                                    Authentication autenticado) throws ValidacaoException {
            validadorNaoHaDuplicidade.validarDuplicidade(dados.titulo(), dados.mensagem());
            var topico = new Topico(dados, autenticado.getName());
            repository.save(topico);
            var uri=uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
            return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(@PageableDefault(size = 10, sort = {"titulo"}) Pageable paginacao) {
        var page = repository.findAll(paginacao).map(DadosListagemTopico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) throws ValidacaoException {
        validadorId.validar(id);
        var topico=repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoTopico dados, Authentication authentication) throws ValidacaoException {
        validadorId.validar(id);
        var topico = repository.getReferenceById(id);

        if (!topico.getAutor().equals(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        topico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id, Authentication authentication) throws ValidacaoException {
        validadorId.validar(id);
        var topico = repository.findById(id).orElse(null);

        if (topico == null || !topico.getAutor().equals(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
