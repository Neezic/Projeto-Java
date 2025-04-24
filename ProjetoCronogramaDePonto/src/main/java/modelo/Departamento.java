// classe que representa um departamento na empresa
/* polimorfismo aqui get set
 * 
 * Fazer a classe DAO dessa classe
 */
package modelo;

import java.util.ArrayList;
import java.util.List;

public class Departamento {
    
    // atributos que definem o departamento
    private int id;  // id único do departamento
    private String nome;  // nome do departamento
    private Funcionario gerente;  // gerente responsável pelo departamento
    private List<Funcionario> funcionarios = new ArrayList<>();  // lista de funcionários do departamento

    // construtor sem parâmetros
    public Departamento(){}

    // construtor que define o nome do departamento
    public Departamento(String nome){
        this.nome = nome;
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
    public List<Funcionario> getFuncionarios(){
        return funcionarios;
    }

    // adiciona um funcionário ao departamento
    public void adicionarFuncionario(Funcionario funcionario){
        this.funcionarios.add(funcionario);
        funcionario.setDepartamento(this);  // atribui o departamento ao funcionário
    }

    // remove um funcionário do departamento
    public void removerFuncionario(Funcionario funcionario){
        this.funcionarios.remove(funcionario);
        funcionario.setDepartamento(null);  // remove o departamento do funcionário
    }

    // método que transforma o departamento em uma string
    @Override
    public String toString(){
        return "Departamento [id="+id+", nome= "+nome+", gerente="+ (gerente != null ? gerente.getnome(): "Não definido") + "]";
    }
}
