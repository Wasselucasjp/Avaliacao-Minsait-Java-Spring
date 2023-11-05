package br.com.minsait.service.interfaces;

import br.com.minsait.model.Contato;
import br.com.minsait.model.Pessoa;

import java.util.List;
import java.util.Optional;

public interface ContatoServiceInterface {

    Contato registrarContato(Contato contato);
    List<Contato> buscaTodosOsContatos();
    Optional<Contato> buscarContatoPorID(Long id);
    Contato atualizarDadosDoContato(Contato contato);
    void  deletarContatoPorID(Long id);

}
