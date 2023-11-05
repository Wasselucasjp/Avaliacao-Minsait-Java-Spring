package br.com.minsait.service;
import br.com.minsait.infra.exception.ResourceNotFoundException;
import br.com.minsait.model.Contato;
import br.com.minsait.model.Pessoa;
import br.com.minsait.repository.ContatoRepository;
import br.com.minsait.repository.PessoaRepository;
import br.com.minsait.service.interfaces.ContatoServiceInterface;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ContatoService implements ContatoServiceInterface {
    private ContatoRepository contatoRepository;

    public ContatoService(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    @Override
    public Contato registrarContato(Contato contato) {
        return contatoRepository.save(contato);
    }

    @Override
    public List<Contato> buscaTodosOsContatos() {
        List<Contato> contatos = contatoRepository.findAll();
        List<Contato> resultContato = Optional.ofNullable(contatos)
                .map(List::stream)
                .orElseGet(Stream::empty)
                .collect(Collectors.toList());
        if (resultContato.isEmpty()) {
            throw new ResourceNotFoundException("Não há nenhum contato cadastrado no momento");
        }
        return resultContato;
    }

    @Override
    public Optional<Contato> buscarContatoPorID(Long id) {
        Optional<Contato> contato = contatoRepository.findById(id);
       return Optional.ofNullable(contato.orElseThrow(() -> new ResourceNotFoundException("Não foi possível encontrar um contato com o ID " + id + " no banco de dados. Por favor, verifique o ID e tente novamente.")));
    }

    @Override
    public Contato atualizarDadosDoContato(Contato contato) {
        return contatoRepository.findById(contato.getId())
                .map(contatoEncontrado -> {
                    contatoEncontrado.setContato(contato.getContato());
                    contatoEncontrado.setTipoContato(contato.getTipoContato());
                    return contatoRepository.save(contatoEncontrado);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Contato com id " + contato.getId() + " não encontrado "));
    }

    @Override
    public void deletarContatoPorID(Long id) {
        contatoRepository.deleteById(id);
    }
}
