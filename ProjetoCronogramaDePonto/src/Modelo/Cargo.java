package Modelo;

public class Cargo {
    private int id;
    private String nome;
    private double salarioBase;
    private String beneficios;
    private String nivel;

    public Cargo(){}
    public Cargo(String nome, double salarioBase){
        this.nome = nome;
        this.salarioBase = salarioBase;
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
    public double getsalarioBase(){
        return salarioBase;
    }
    public void setsalarioBase(double salarioBase){
        this.salarioBase = salarioBase;
    }
    public String getBeneficios(){
        return beneficios;
    }
    public void setBeneficios(String beneficios){
        this.beneficios = beneficios;
    }
    public String getNivel(){
        return nivel;
    }
    public void setNivel(String nivel){
        this.nivel = nivel;
    }

    @Override
    public String toString(){
        return "Cargo [id="+id+", nome="+nome+",salarioBase="+salarioBase+", nivel="+nivel+"]";
    }
}
