/* 
 * Classe para o registro de Funcionarios 
 * A classe mais usada pois sem ela não se pode registrar pontos
 * 
 * 
 */


// classe que representa um funcionário na empresa
package modelo;

import java.time.LocalDateTime;

public class Funcionario {

    // atributos que definem um funcionário
    private int id;  // id único do funcionário
    private String nome;  // nome do funcionário
    private String cpf;   // CPF do funcionário
    private LocalDateTime dataAdmissao;  // data de admissão do funcionário
    private Departamento departamento;  // departamento em que o funcionário trabalha
    private Cargo cargo;  // cargo do funcionário

    // construtor sem parâmetros
    public Funcionario(){}

    // construtor que define o nome, cpf e data de admissão
    public Funcionario(String nome, String cpf, LocalDateTime dataAdmissao){
        this.nome = nome;
        this.cpf = cpf;
        this.dataAdmissao = dataAdmissao;
    }

    // métodos para acessar os atributos

    // pega o id do funcionário
    public int getId(){
        return id;
    }

    // pega o nome do funcionário
    public String getnome(){
        return nome;
    }

    // pega o CPF do funcionário
    public String getcpf(){
        return cpf;
    }

    // pega a data de admissão do funcionário
    public LocalDateTime dataAdmissao(){
        return dataAdmissao;
    }

    // define o id do funcionário
    public void setId(int id){
        this.id = id;
    }

    // define o nome do funcionário
    public void setnome(String nome){
        this.nome = nome;
    }

    // define a data de admissão do funcionário
    public void setdataAdmissao(LocalDateTime dataAdmissao){
        this.dataAdmissao = dataAdmissao;
    }

    // define o CPF do funcionário
    public void setcpf(String cpf){
        this.cpf = cpf;
    }

    // pega o departamento do funcionário
    public Departamento getdepartamento(){
        return departamento;
    }

    // define o departamento em que o funcionário trabalha
    public void setDepartamento(Departamento departamento){
        this.departamento = departamento;
    }

    // pega o cargo do funcionário
    public Cargo getCargo(){
        return cargo;
    }

    // define o cargo do funcionário
    public void setCargo(Cargo cargo){
        this.cargo = cargo;
    }

    // método que transforma o funcionário em uma string
    @Override 
    public String toString(){
        return "Funcionario[id="+id+",nome="+nome+",cpf="+cpf+"]";
    }
}
