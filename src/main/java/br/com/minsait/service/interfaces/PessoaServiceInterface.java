package br.com.minsait.service.interfaces;

import br.com.minsait.model.Pessoa;

import java.util.List;
import java.util.Optional;

public interface PessoaServiceInterface {
    Pessoa resgistrarPessoa(Pessoa pessoa);
    List<Pessoa> buscarTodasAsPessoas();
    Optional<Pessoa> buscarPessoaPorID(Long id);
    Pessoa  atualizarDadosPessoa(Pessoa pessoa);
    void  deletarPessoaPorID(Long id);

}
