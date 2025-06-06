/* COMPOSIÇÃO AQUI EM PRIVATE CARGO
 * Classe para o registro de Funcionarios 
 * A classe mais usada pois sem ela não se pode registrar pontos
 * 
 * Começar a implementar Herança nessa classe.
 * 
 */
// classe que representa um funcionário na empresa
package main.java.modelo;

import java.time.LocalDateTime;

public class Funcionario extends Usuario {

    // atributos que definem um funcionário
    private LocalDateTime dataAdmissao;  // data de admissão do funcionário
    private Departamento departamento;  // departamento em que o funcionário trabalha e Associação (1 Funcionario está associado a 1 Departamento
    private Cargo cargo;  // cargo do funcionário que tem um cargo.
    private calcularSalarioStrategy calculoSalarioStrategy = new calcularSalarioBase();
    private static final long serialVersionUID = 1L; 
    // Isso caracteriza uma relação de composição, onde uma classe contém outra como parte de sua estrutura.

    // construtor que define o nome, cpf e data de admissão
    public Funcionario(String nome, String cpf,Departamento departamento, Cargo cargo){
        super(nome,cpf);
        this.departamento = departamento;
        this.cargo = cargo;
        departamento.adicionarFuncionario(this); // Associação Bidirecional.
    }

    // métodos para acessar os atributos

    // pega a data de admissão do funcionário
    public LocalDateTime dataAdmissao(){
        return dataAdmissao;
    }

    // define a data de admissão do funcionário
    public void setdataAdmissao(LocalDateTime dataAdmissao){
        this.dataAdmissao = dataAdmissao;
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

    public void setCalculoSalarioStrategy(calcularSalarioStrategy strategy) {
        this.calculoSalarioStrategy = strategy;
    }
    
    public double calcularSalario() {
        return calculoSalarioStrategy.calcularSalario(this);
    }

    //metódo de autenticar senha
    @Override
    public boolean autenticar(String senha){
        //lógica especifica para funcionarios
        return senha != null && senha.length() >= 6;
    }
    //metodo para registrar ponto
    public void registrarPonto(){
        System.out.println(nome + "registrou ponto no departamento" + departamento.getNome());
    }
    // método que transforma o funcionário em uma string
    @Override 
    public String toString(){
        return "Funcionario[id="+id+",nome="+nome+",cpf="+cpf+"]";
    }
}
