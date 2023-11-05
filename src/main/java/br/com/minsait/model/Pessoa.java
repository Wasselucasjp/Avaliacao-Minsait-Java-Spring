package br.com.minsait.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_pessoas")
@Entity(name = "Pessoas")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Nome obrigatório")
    @NotBlank(message = "Nome não pode ser vazio")
    @Length(max = 100, message = "Nome não pode conter mais de 100 caracteres")
    @Pattern(regexp = "^[A-Za-z\\s]*$")
    private String nome;

    @Length(max = 100, message = "Endereço não pode conter mais de 100 caracteres")
    private String endereco;

    @Length(max = 9, message = "Cep não pode conter mais de 9 caracteres")
    @Pattern(regexp = "^\\d{5}-\\d{3}$")
    private String cep;

    @Length(max = 100, message = "Cidade não pode conter mais de 100 caracteres")
    private String cidade;

    @Length(max = 2, message = "Uf não pode conter mais de 2 caracteres")
    private String uf;

    @JsonIgnore
    @OneToMany(mappedBy = "pessoaId", cascade = CascadeType.ALL)
    private List<Contato> contatos;


    public String descricaoMalaDireta() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(endereco)
                .append(" - CEP: ")
                .append(cep)
                .append(" - ")
                .append(cidade)
                .append("/")
                .append(uf);
        // Retorna o resultado como uma String
        return stringBuilder.toString();
    }

    public int hashcode() {
        return Objects.hash(id);
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Pessoa other = (Pessoa) obj;
        return Objects.equals(id, other.id);
    }
}
