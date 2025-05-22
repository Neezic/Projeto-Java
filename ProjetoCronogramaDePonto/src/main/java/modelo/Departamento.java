// classe que representa um departamento na empresa
/* polimorfismo aqui get set
 * 
 * Fazer a classe DAO dessa classe
 */
package main.java.modelo;


import java.util.HashSet;
import java.util.Set;

public class Departamento {
    
    // atributos que definem o departamento
    private int id;  // id único do departamento
    private String nome;  // nome do departamento
    private Funcionario gerente;  // gerente responsável pelo departamento
    private Set<Funcionario> funcionarios;  // lista de funcionários do departamento e Agregação (1 Departamento contem N Funcionarios)

    // construtor sem parâmetros
    public Departamento(){}

    // construtor que define o nome do departamento
    public Departamento(String nome){
        this.nome = nome;
        this.funcionarios = new HashSet<>();
    }

    // métodos para acessar e definir os atributos

    // pega o id do departamento
    public int getId(){
        return id;
    }

    // define o id do departamento
    public void setId(int id){
        this.id = id;
    }

    // pega o nome do departamento
    public String getNome(){
        return nome;
    }

    // define o nome do departamento
    public void setNome(String nome){
        this.nome = nome;
    }

    // pega o gerente do departamento
    public Funcionario getGerente(){
        return gerente;
    }

    // define o gerente do departamento
    public void setGerente(Funcionario gerente){
        this.gerente = gerente;
    }

    // pega a lista de funcionários do departamento
    public Set<Funcionario> getFuncionarios(){
        return funcionarios;
    }

    // adiciona um funcionário ao departamento
    public void adicionarFuncionario(Funcionario funcionario){
        funcionarios.add(funcionario); // atribui o departamento ao funcionário
    }

    // remove um funcionário do departamento
    public void removerFuncionario(Funcionario funcionario){
        this.funcionarios.remove(funcionario);
        funcionario.setDepartamento(null);  // remove o departamento do funcionário
    }

}
