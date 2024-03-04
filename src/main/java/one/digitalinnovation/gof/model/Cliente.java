package one.digitalinnovation.gof.model;

import jakarta.persistence.*;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;

    @ManyToOne
    private Endereco Endereco;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Endereco getEndereco() {
        return Endereco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(Endereco endereco) {
        Endereco = endereco;
    }

    @Override
    public String toString() {
        return "Cliente: " + nome +
                "\n id: " + id +
                "\n Endereco: " + Endereco;
    }
}
