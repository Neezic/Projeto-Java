package main.java.modelo;

import java.io.Serializable;

public abstract class Usuario implements Serializable {
    protected int id;
    protected String nome;
    protected String cpf;

    private static final long serialVersionUID = 1L;

    public Usuario(String nome, String cpf){
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getcpf(){
        return cpf;
    }
    public String getnome(){
        return nome;
    }
    public void setnome(String nome){
        this.nome = nome;
    }
    public void setcpf(String cpf){
        this.cpf = cpf;
    }
    public abstract boolean autenticar(String senha);
    public void exibirDados(){
        System.out.println("Nome" + nome +" | CPF:" + cpf);
    }
}
