package br.com.minsait.resource;

import br.com.minsait.dto.PessoaMalaDiretaDTO;
import br.com.minsait.model.Contato;
import br.com.minsait.model.Pessoa;
import br.com.minsait.service.ContatoService;
import br.com.minsait.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaResource {
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private ContatoService contatoService;

    public PessoaResource(PessoaService pessoaService, ContatoService contatoService) {
        this.pessoaService = pessoaService;
        this.contatoService = contatoService;
    }

    @Operation(summary = "Registra", description = "Registra pessoa no banco de dados", tags = "Pessoas")
    @PostMapping
    public ResponseEntity<Pessoa> save(@RequestBody Pessoa pessoa) {
        Pessoa newPessoa = pessoaService.resgistrarPessoa(pessoa);
        return (Objects.isNull(newPessoa)) ? ResponseEntity.notFound().build() : new ResponseEntity<>(newPessoa, HttpStatus.CREATED);
    }

    @Operation(summary = "Lista", description = "Metodo lista todas a pesaso ", tags = "Pessoas")
    @GetMapping
    public ResponseEntity<List<Pessoa>> buscarPessoas() {
        List<Pessoa> pessoas = pessoaService.buscarTodasAsPessoas();
        return pessoas.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(pessoas);
    }

    @Operation(summary = "Busca ID", description = "Metodo que retorna uma pessoa por ID", tags = "Pessoas")
    @GetMapping("/{id}")
    public ResponseEntity<Stream<Pessoa>> buscaPessoaID(@PathVariable Long id) {
        Optional<Pessoa> optionalPessoa = pessoaService.buscarPessoaPorID(id);
        Stream<Pessoa> pessoaStream = optionalPessoa.stream();
        return ResponseEntity.ok(pessoaStream);
    }

    @Operation(summary = "MalaDireta", description = "Metodo que retonar uma pessao com endereço por ID", tags = "Pessoas")
    @GetMapping("/maladireta/{id}")
    public ResponseEntity<PessoaMalaDiretaDTO> buscaMalaDiretaById(@PathVariable Long id) {
        Optional<Pessoa> optionalPessoa = pessoaService.buscarPessoaPorID(id);
        if (optionalPessoa.isPresent()) {
            Pessoa pessoa = optionalPessoa.get();
            PessoaMalaDiretaDTO malaDiretaDTO = new PessoaMalaDiretaDTO(pessoa.getId(), pessoa.getNome(), pessoa.descricaoMalaDireta());
            return ResponseEntity.ok(malaDiretaDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Atualiza", description = "Metodo para atualizar Pessoa por ID", tags = "Pessoas")
    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizaPessoa(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        pessoa.setId(id);
        Pessoa newPessoa = pessoaService.atualizarDadosPessoa(pessoa);
        return (Objects.isNull(newPessoa)) ? ResponseEntity.notFound().build() : ResponseEntity.ok(newPessoa);
    }

    @Operation(summary = "Excluir", description = "Exclui uma pessoa por id", tags = "Pessoas")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePessoaPorID(@PathVariable Long id) {
        return pessoaService.buscarPessoaPorID(id)
                .map(pessoa -> {
                    pessoaService.deletarPessoaPorID(id);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @Operation(summary = "Adiciona contato", description = "Metodo para adicionar contato por ID", tags = "Pessoas")
    @PostMapping("/{id}/contatos")
    public ResponseEntity<Contato> adicionaContato(@PathVariable Long id, @RequestBody Contato contato) {
        return pessoaService.buscarPessoaPorID(id)
                .map(pessoa -> {
                    contato.setPessoaId(pessoa);
                    return ResponseEntity.ok(contatoService.registrarContato(contato));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}