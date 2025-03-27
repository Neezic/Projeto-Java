/*
 * @autor Neezic, Loshad e MH 
 * Classe para o registro de Funcionarios 
 * A classe mais usada pois sem ela não se pode registrar pontos
 * 
 * 
 */




package modelo;
import java.time.LocalDate;

public class Funcionario {
    private int id;
    private String nome;
    private String cpf;
    private LocalDate dataAdmissao;
    private Departamento departamento;
    private Cargo cargo;

    public Funcionario(){}
    
    public Funcionario(String nome,int id, String cpf, LocalDate dataAdmissao){
        this.nome = nome;
        this.cpf = cpf;
        this.dataAdmissao = dataAdmissao;
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public String getnome(){
        return nome;
    }
    public String getcpf(){
        return cpf;
    }
    public LocalDate dataAdmissao(){
        return dataAdmissao;
    }

    public void setId(int id){
        this.id = id;
    }
    public void setnome(String nome){
        this.nome = nome;
    }
    public void setdataAdmissao(LocalDate dataAdmissao){
        this.dataAdmissao = dataAdmissao;
    }
    public void setcpf(String cpf){
        this.cpf = cpf;
    }
    public Departamento getdepartamento(){
        return departamento;
    }
    public void setDepartamento(Departamento departamento){
        this.departamento = departamento;
    }
    public Cargo getCargo(){
        return cargo;
    }
    public void setCargo(Cargo cargo){
        this.cargo = cargo;
    }

    @Override 
    public String toString(){
        return "Funcionario[id="+id+",nome="+nome+",cpf"+cpf+"]";
    }



}
