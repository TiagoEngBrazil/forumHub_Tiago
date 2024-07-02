package one.alura.forumHub.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import one.alura.forumHub.domain.resposta.DadosCadastroResposta;
import one.alura.forumHub.domain.resposta.DadosDetalhamentoResposta;
import one.alura.forumHub.domain.resposta.Resposta;
import one.alura.forumHub.domain.resposta.RespostaRepository;
import one.alura.forumHub.domain.topico.TopicoRepository;
import one.alura.forumHub.domain.validacoes.ValidadorId;
import one.alura.forumHub.domain.validacoes.ValidadorNaoHaDuplicidade;
import one.alura.forumHub.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/topicos/{id}/respostas")
public class RespostaController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private ValidadorNaoHaDuplicidade validadorNaoHaDuplicidade;

    @Autowired
    private ValidadorId validadorId;

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@PathVariable Long id,
                                       @RequestBody @Valid DadosCadastroResposta dados,
                                       UriComponentsBuilder uriBuilder,
                                       Authentication autenticado) throws ValidacaoException {
        validadorId.validar(id);

        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Tópico não encontrado"));

        var resposta = new Resposta(dados, autenticado.getName(), topico);
        respostaRepository.save(resposta);

        var uri = uriBuilder.path("/topicos/{id}/respostas/{idResposta}")
                .buildAndExpand(id, resposta.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoResposta(resposta));
    }

//    @GetMapping
//    public ResponseEntity<Page<DadosListagemTopico>> listar(@PageableDefault(size = 10, sort = {"titulo"}) Pageable paginacao) {
//        var page = repository.findAll(paginacao).map(DadosListagemTopico::new);
//        return ResponseEntity.ok(page);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity detalhar(@PathVariable Long id) throws ValidacaoException {
//        validadorId.validar(id);
//        var topico=repository.getReferenceById(id);
//        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
//    }
//
//    @PutMapping("/{id}")
//    @Transactional
//    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoTopico dados, Authentication authentication) throws ValidacaoException {
//        validadorId.validar(id);
//        var topico = repository.getReferenceById(id);
//
//        if (!topico.getAutor().equals(authentication.getName())) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//
//        topico.atualizarInformacoes(dados);
//        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
//    }
//
//    @DeleteMapping("/{id}")
//    @Transactional
//    public ResponseEntity excluir(@PathVariable Long id, Authentication authentication) throws ValidacaoException {
//        validadorId.validar(id);
//        var topico = repository.findById(id).orElse(null);
//
//        if (topico == null || !topico.getAutor().equals(authentication.getName())) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//
//        repository.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }

}
