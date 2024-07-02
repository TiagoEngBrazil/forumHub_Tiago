package one.alura.forumHub.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import one.alura.forumHub.domain.resposta.*;
import one.alura.forumHub.domain.topico.TopicoRepository;
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
        validadorId.validar(id, topicoRepository);

        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Tópico não encontrado"));

        var resposta = new Resposta(dados, autenticado.getName(), topico);
        respostaRepository.save(resposta);

        var uri = uriBuilder.path("/topicos/{id}/respostas/{idResposta}")
                .buildAndExpand(id, resposta.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoResposta(resposta));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemResposta>> listar(@PathVariable Long id,
                                                              @PageableDefault(size = 10, sort = {"dataCriacao"})
                                                              Pageable paginacao) {
        var page = respostaRepository.findByTopicoId(id, paginacao)
                .map(DadosListagemResposta::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id2}")
    public ResponseEntity detalhar(@PathVariable Long id2) throws ValidacaoException {
        validadorId.validar(id2, respostaRepository);
        var resposta = respostaRepository.findById(id2).orElseThrow(()-> new  ValidationException("Id não " +
                "encontrado!"));
        return ResponseEntity.ok(new DadosDetalhamentoResposta(resposta));
    }

    @PutMapping("/{id2}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable Long id2, @RequestBody @Valid DadosAtualizacaoResposta dados,
                                    Authentication authentication) throws ValidacaoException {
        validadorId.validar(id2, respostaRepository);
        var resposta = respostaRepository.getReferenceById(id2);

        if (!resposta.getAutor().equals(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        resposta.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoResposta(resposta));
    }
//
    @DeleteMapping("/{id2}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id2, Authentication authentication) throws ValidacaoException {
        validadorId.validar(id2, respostaRepository);
        var resposta = respostaRepository.findById(id2).orElseThrow(()-> new  ValidationException("resposta não " +
                "encontrado para id:" + id2));

        if (resposta == null || !resposta.getAutor().equals(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        respostaRepository.deleteById(id2);
        return ResponseEntity.noContent().build();
    }

}
