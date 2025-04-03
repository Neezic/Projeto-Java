/* 
 * Classe para Registrar o horário do Ponto do Funcionario
 * Exporta a classe de Funcionario e LocalDateTime
 * 
 * 
 * 
*/

package modelo;
import java.time.LocalDateTime;

public class RegistraPonto {
    private int id; // id único do registro de ponto
    private Funcionario funcionario; // funcionário que está registrando o ponto
    private LocalDateTime dataHoraEntrada; // hora em que o funcionário entrou
    private LocalDateTime dataHoraSaida; // hora em que o funcionário saiu
    private String observacao; // observações extras sobre o registro
    private boolean aprovado;
    // construtor para criar um registro de ponto com todos os dados
    public RegistraPonto(int id, Funcionario funcionario, LocalDateTime entrada, LocalDateTime saida, String observacao) {
        this.id = id; // define o id do registro de ponto
        this.funcionario = funcionario; // define o funcionário do registro
        this.dataHoraEntrada = entrada; // define a hora de entrada
        this.dataHoraSaida = saida; // define a hora de saída
        this.observacao = observacao; // define as observações
    }

    // construtor para criar um registro de ponto apenas com a hora de entrada
    public RegistraPonto(Funcionario funcionario, LocalDateTime dataHoraEntrada) {
        this(0, funcionario, dataHoraEntrada, null, null); // chama o construtor principal, com id 0 e sem hora de saída ou observação
    }

    // método para pegar a observação do registro
    public String getObservacao() {
        return observacao; // retorna a observação do registro
    }
    public boolean getaprovado(){
        return aprovado;
    }
    public void setAprovado(boolean aprovado){
        this.aprovado = aprovado;
    }
    // método para pegar o id do registro
    public int getId() {
        return id; // retorna o id do registro
    }

    // método para pegar o funcionário do registro
    public Funcionario getfuncionario() {
        return funcionario; // retorna o funcionário do registro
    }

    // método para pegar a hora de entrada
    public LocalDateTime gethoraEntrada() {
        return dataHoraEntrada; // retorna a hora de entrada
    }

    // método para pegar a hora de saída
    public LocalDateTime gethoraSaida() {
        return dataHoraSaida; // retorna a hora de saída
    }

    // método para definir a observação do registro
    public void setObservacao(String observacao) {
        this.observacao = observacao; // define a observação
    }

    // método para definir o id do registro
    public void setId(int id) {
        this.id = id; // define o id do registro
    }

    // método para definir o funcionário do registro
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario; // define o funcionário do registro
    }

    // método para definir a hora de entrada
    public void setEntrada(LocalDateTime dataHoraEntrada) {
        this.dataHoraEntrada = dataHoraEntrada; // define a hora de entrada
    }

    // método para definir a hora de saída
    public void setSaida(LocalDateTime dataHoraSaida) {
        this.dataHoraSaida = dataHoraSaida; // define a hora de saída
    }

    // método para calcular as horas trabalhadas entre entrada e saída
    public long calcularHorasTrabalhadas() {
        if (dataHoraSaida == null) return 0; // se não houver hora de saída, não pode calcular
        return java.time.Duration.between(dataHoraEntrada, dataHoraSaida).toHours(); // calcula e retorna as horas trabalhadas
    }
    
}
