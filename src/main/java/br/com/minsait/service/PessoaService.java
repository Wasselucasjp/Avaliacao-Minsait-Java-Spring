package br.com.minsait.service;

import br.com.minsait.infra.exception.ResourceNotFoundException;
import br.com.minsait.model.Pessoa;
import br.com.minsait.repository.PessoaRepository;
import br.com.minsait.service.interfaces.PessoaServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class PessoaService implements PessoaServiceInterface {
    private PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public Pessoa resgistrarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @Override
    public List<Pessoa> buscarTodasAsPessoas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        List<Pessoa> result = Optional.ofNullable(pessoas)
                .map(List::stream)
                .orElseGet(Stream::empty)
                .collect(Collectors.toList());
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Não há pessoas cadastradas no momento");
        }
        return result;
    }

    @Override
    public Optional<Pessoa> buscarPessoaPorID(Long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        return Optional.ofNullable(pessoa.orElseThrow(
                () -> new ResourceNotFoundException(
                        "Não foi possível encontrar uma pessoa com o ID " + id + " no banco de dados. Por favor, verifique o ID e tente novamente.")));

    }

    @Override
    public Pessoa atualizarDadosPessoa(Pessoa pessoa) {
        return pessoaRepository.findById(pessoa.getId())
                .map(pessoaAtualizada -> {
                    pessoaAtualizada.setNome(pessoa.getNome());
                    pessoaAtualizada.setEndereco(pessoa.getEndereco());
                    pessoaAtualizada.setCep(pessoa.getCep());
                    pessoaAtualizada.setCidade(pessoa.getCidade());
                    pessoaAtualizada.setUf(pessoa.getUf());
                    return pessoaRepository.save(pessoaAtualizada);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa com id " + pessoa.getId() + " não encontrada"));
    }

    @Override
    public void deletarPessoaPorID(Long id) {
        pessoaRepository.deleteById(id);
    }
}