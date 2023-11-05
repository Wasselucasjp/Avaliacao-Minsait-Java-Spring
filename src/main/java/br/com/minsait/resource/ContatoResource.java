package br.com.minsait.resource;

import br.com.minsait.model.Contato;
import br.com.minsait.repository.ContatoRepository;
import br.com.minsait.service.ContatoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/contatos")
public class ContatoResource {

    private ContatoService contatoService;
    private final ContatoRepository contatoRepository;

    public ContatoResource(ContatoService contatoService,
                           ContatoRepository contatoRepository){this.contatoService = contatoService;
        this.contatoRepository = contatoRepository;
    }

    @Operation (summary = "Lista ", description = "Metodo para listar todos os contatos", tags = "Contatos")
    @GetMapping
    public ResponseEntity<List<Contato>> buscarTodosOsContatos() {
        List<Contato> contatos = contatoService.buscaTodosOsContatos();
        return contatos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(contatos);
    }

    @Operation(summary = "Lista ",description = "Metodo retorna contatos por ID", tags = "Contatos")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Contato>> buscaContatoPorID(@PathVariable Long id){
            Optional<Contato> contato = contatoService.buscarContatoPorID(id);
            return ResponseEntity.ok(contato);
    }

    @Operation(summary = "Atualiza", description = "Atualiza contato por ID", tags = "Contatos")
    @PutMapping("/{id}")
    public ResponseEntity <Contato> atualizaContatoPorID(@PathVariable Long id, @RequestBody Contato contato){
        contato.setId(id);
        Contato novoContato = contatoService.atualizarDadosDoContato(contato);
        return (Objects.isNull(novoContato)) ? ResponseEntity.notFound().build() : ResponseEntity.ok(novoContato);
    }

    @Operation(summary = "Deleta", description = "Deleta uma contato por ID",tags = "Contatos")
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deletaContatoPorID(@PathVariable Long id){
        Optional<Contato> contato = contatoService.buscarContatoPorID(id);
        return contato.isPresent() ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
     }

}
