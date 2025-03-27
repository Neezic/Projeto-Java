/* 
 * @autor  Neezic, Loshad, MH.
 * Classe para Registrar o horário do Ponto do Funcionario
 * Exporta a classe de Funcionario e LocalDateTime
 * 
 * 
 * 
*/
package modelo;
import java.time.LocalDateTime;

public class RegistraPonto {
    private int id;
    private Funcionario funcionario;
    private LocalDateTime dataHoraEntrada;
    private LocalDateTime dataHoraSaida;
    private String observacao;

    public RegistraPonto(int id,Funcionario funcionario, LocalDateTime entrada, LocalDateTime saida, String observacao){
        this.id = id;
        this.funcionario = funcionario;
        this.dataHoraEntrada = entrada;
        this.dataHoraSaida = saida;
        this.observacao = observacao;
    }
    public RegistraPonto(Funcionario funcionario, LocalDateTime dataHoraEntrada){
        this(0, funcionario, dataHoraEntrada, null, null);   
    }

    public String getObservacao(){
        return observacao;
    }
    public int getId(){
        return id;
    }
    public Funcionario getfuncionario(){
        return funcionario;
    }
    public LocalDateTime gethoraEntrada(){
        return dataHoraEntrada;
    }
    public LocalDateTime gethoraSaida(){
        return dataHoraSaida;
    }
    public void setObservacao(String observacao){
        this.observacao = observacao;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setFuncionario(Funcionario funcionario){
        this.funcionario = funcionario;
    }
    public void setEntrada(LocalDateTime dataHoraEntrada){
        this.dataHoraEntrada = dataHoraEntrada;
    }
    public void setSaida(LocalDateTime dataHoraSaida){
        this.dataHoraEntrada = dataHoraSaida;
    }
    public long calcularHorasTrabalhadas(){
        if(dataHoraSaida == null) return 0;
        return java.time.Duration.between(dataHoraEntrada,dataHoraSaida).toHours();
    }
    
}
