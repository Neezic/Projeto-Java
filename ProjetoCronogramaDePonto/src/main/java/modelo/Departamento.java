package main.java.modelo;

import java.util.ArrayList;
import java.util.List;
public class Departamento {
    private int id; 
    private String nome;
    private Funcionario gerente;
    private List<Funcionario> funcionarios = new ArrayList<>();

    public Departamento(){}
    public Departamento(String nome){
        this.nome = nome;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public Funcionario getGerente(){
        return gerente;
    }
    public void setGerente(Funcionario gerente){
        this.gerente = gerente;
    }

    public List<Funcionario> getFuncionarios(){
        return funcionarios;
    }
    public void adicionarFuncionario(Funcionario funcionario){
        this.funcionarios.add(funcionario);
        funcionario.setDepartamento(this);
    }
    public void removerFuncionario(Funcionario funcionario){
        this.funcionarios.remove(funcionario);
        funcionario.setDepartamento(null);
    }
    @Override
    public String toString(){
        return "Departamento [id="+id+", nome= "+nome+",gerente="+ (gerente != null ? gerente.getnome(): "Não definido") + "]";
    }
}
