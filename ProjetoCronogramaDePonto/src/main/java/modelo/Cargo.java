// classe que representa o cargo de um funcionário
/* Polimorfismo aqui Get Set
 * Fazer a classe dao dessa classe.
 * 
 */
package modelo;

// classe Cargo
public class Cargo {
    
    // atributos que definem o cargo
    private int id;           // id único do cargo
    private String nome;      // nome do cargo
    private double salarioBase;  // salário base do cargo
    private String beneficios;   // benefícios que o cargo oferece
    private String nivel;        // nível do cargo (por exemplo, Júnior, Pleno, Sênior)

    // construtor sem parâmetros
    public Cargo() {}

    // construtor que define o nome e o salário base ao criar o cargo
    public Cargo(String nome, double salarioBase){
        this.nome = nome;
        this.salarioBase = salarioBase;
    }

    // métodos para acessar os atributos

    // pega o id do cargo
    public int getId(){
        return id;
    }

    // define o id do cargo
    public void setId(int id){
        this.id = id;
    }

    // pega o nome do cargo
    public String getNome(){
        return nome;
    }

    // define o nome do cargo
    public void setNome(String nome){
        this.nome = nome;
    }

    // pega o salário base do cargo
    public double getsalarioBase(){
        return salarioBase;
    }

    // define o salário base do cargo
    public void setsalarioBase(double salarioBase){
        this.salarioBase = salarioBase;
    }

    // pega os benefícios do cargo
    public String getBeneficios(){
        return beneficios;
    }

    // define os benefícios do cargo
    public void setBeneficios(String beneficios){
        this.beneficios = beneficios;
    }

    // pega o nível do cargo
    public String getNivel(){
        return nivel;
    }

    // define o nível do cargo
    public void setNivel(String nivel){
        this.nivel = nivel;
    }

    // método que transforma o cargo em uma string
    @Override
    public String toString(){
        return "Cargo [id="+id+", nome="+nome+", salarioBase="+salarioBase+", nivel="+nivel+"]";
    }
}

