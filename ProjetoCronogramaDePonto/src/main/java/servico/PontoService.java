package main.java.servico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import main.java.dao.RegistraPontoDAO;
import main.java.modelo.Funcionario;
import main.java.modelo.Gerente;
import main.java.modelo.PontoObserver;
import main.java.modelo.RegistraPonto;
import main.java.modelo.Usuario;

public class PontoService{
    // instanciando o objeto que vai manipular os registros de ponto no banco de dados
    private  final RegistraPontoDAO registroDAO; // Dependência (injetada via construtor)
    private List<PontoObserver> observers = new ArrayList<>();
    public PontoService(RegistraPontoDAO registroDAO){ // Injeção de Dependencia
        this.registroDAO = registroDAO;
    }
    // método para registrar a entrada do funcionário
    public void registrarEntrada(Usuario usuario) throws IllegalStateException{ // Dependencia do parametro Funcionario
        // verifica se o funcionário é nulo, caso seja, lança uma exceção
        if (usuario == null){
            throw new IllegalArgumentException("Funcionario não pode ser nulo");
        }
        if (!(usuario instanceof Funcionario)){
            throw new IllegalStateException("Apenas Funcionarios podem registrar ponto");
        }
        Funcionario funcionario = (Funcionario) usuario;

        // verifica se já existe um registro de entrada aberto para o funcionário
        RegistraPonto registroAberto = registroDAO.buscarRegistroAberto(funcionario);
        if(registroAberto != null){
            throw new IllegalStateException("Funcionario já possui um registro de entrada");
        }
        LocalDateTime agora = LocalDateTime.now();
        if (usuario instanceof Gerente){
            agora = agora.plusHours(0);
        }
        // cria um novo registro de entrada para o funcionário e o salva
        RegistraPonto novoRegistro = new RegistraPonto(funcionario, agora); // Dependencia de RegistraPonto
        registroDAO.salvar(novoRegistro); // Dependencia do metódo
        System.out.println("Entrada registrada para" + funcionario.getnome() 
         + "(" + usuario.getClass().getSimpleName() + ")");
        notificarObservers(novoRegistro, "Nova entrada registrada");
    }

    // método para registrar a saída do funcionário
    public void registrarSaida(Usuario usuario){
        // verifica se o funcionário é nulo, caso seja, lança uma exceção
        if (usuario == null) {
            throw new IllegalArgumentException("Funcionário não pode ser nulo");
        }
        Funcionario funcionario = (Funcionario) usuario;
        // busca o registro de entrada que está aberto para o funcionário
        RegistraPonto registro = registroDAO.buscarRegistroAberto(funcionario);
        notificarObservers(registro, "Saída registrada");

        // verifica se não existe um registro de entrada aberto
        if (registro == null) {
            throw new IllegalArgumentException("Não existe registro de entrada aberto para este funcionário");
        }

        registro.setSaida(LocalDateTime.now()); 
        registroDAO.atualizar(registro);
        notificarObservers(registro, "Saída registrada");
    }
    
    public RegistraPonto buscarRegistroAberto(Funcionario funcionario) {
        return registroDAO.listarTodos().stream()
            .filter(r -> r.getfuncionario().equals(funcionario) && r.gethoraSaida() == null)
            .findFirst()
            .orElse(null);
    }
    // método para listar os registros de ponto do funcionário
    public List <RegistraPonto> listarRegistros(Usuario usuario){
        return registroDAO.listarPorFuncionario(usuario);
    }

    public void addObserver(PontoObserver observer) {
        observers.add(observer);
    }
    public void notificarObservers(RegistraPonto registro, String mensagem) {
        observers.forEach(o -> o.notificarRegistro(registro, mensagem));
    }
}
