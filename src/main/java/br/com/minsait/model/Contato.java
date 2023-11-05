package br.com.minsait.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_contatos")
public class Contato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotNull(message = "Tipo de contato obrigatório")
    @Max(value = 1, message = "Tipo contato não pode ser maior que 1")
    @Min(value = 0, message = "Tipo contato não pode ser menor que 0")
    private Integer tipoContato;

    @Column(nullable = false)
    @NotNull(message = "Contato obrigatório")
    @NotBlank(message = "Contato não pode ser vazio")
    @Length(max = 30, message = "Contato não pode conter mais de 30 caracteres")
    @Pattern(regexp = "^[0-9]*$")
    private String contato;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "pessoa_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Pessoa pessoaId;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Contato other = (Contato) obj;
        return Objects.equals(id, other.id);
    }
}