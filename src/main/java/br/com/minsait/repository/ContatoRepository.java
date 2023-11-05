package br.com.minsait.repository;

import br.com.minsait.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContatoRepository extends JpaRepository<Contato,Long> {

    @Query(value = "SELECT * FROM tb_contatos WHERE pessoa_id = ?", nativeQuery = true)
    List<Contato> findByPessoaId(Long pessoaId);

}
