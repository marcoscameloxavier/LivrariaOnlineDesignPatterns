package pucpr.livraria.entity;

public class Cliente {
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private String cpf;
    private String cep;

    public Cliente(String nome, String email, String telefone, String endereco, String cpf, String cep) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.cpf = cpf;
        this.cep = cep;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCep() {
        return cep;
    }

}
