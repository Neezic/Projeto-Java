package Modelo;
import java.time.LocalDateTime;

public class RegistraPonto {
    private int id;
    private Funcionario funcionario;
    private LocalDateTime dataHoraEntrada;
    private LocalDateTime dataHoraSaida;
    private String observacao;

    public RegistraPonto(){}
    public RegistraPonto(Funcionario funcionario, LocalDateTime entrada, LocalDateTime saida){
        this.funcionario = funcionario;
        this.dataHoraEntrada = entrada;
        this.dataHoraSaida = saida;
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
