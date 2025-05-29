package main.java.modelo;
import java.time.*;
import java.time.format.*;
import java.util.*;
import main.java.dao.FuncionarioDao;
import main.java.dao.RegistraPontoDAO;
import main.java.servico.PontoService;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static PontoService pontoService;
    private static FuncionarioDao funcionarioDAO;
    private static Usuario usuarioAtual;
    private static RegistraPontoDAO registroDAO;

    public static void main(String[] args) {
        // Inicialização com injeção de dependência
        registroDAO = new RegistraPontoDAO();
        funcionarioDAO = new FuncionarioDao();
        pontoService = new PontoService(registroDAO); // Dependência injetada

        // Configuração inicial
        Departamento ti = new Departamento("TI");
        Cargo dev = new Cargo("Desenvolvedor", 5000);
        Cargo gerenteCargo = new Cargo("Gerente", 8000);

        // Cadastro de usuários
        Funcionario funcionario = new Funcionario("João", "123", ti, dev);
        Gerente gerente = new Gerente("Maria", "456", ti, gerenteCargo);

        //Agregação do Departamento e Funcionario, se Departamento deixar de existir Funcionario continua existindo.
        
        funcionarioDAO.salvar(funcionario);
        funcionarioDAO.salvar(gerente);

        // Menu principal
        while (true) {
            if (usuarioAtual == null) {
                exibirMenuLogin();
            } else {
                if (usuarioAtual instanceof Gerente) {
                    exibirMenuGerente();
                } else {
                    exibirMenuFuncionario();
                }
            }
        }
    }

    private static void exibirMenuLogin() {
        System.out.println("\n=== SISTEMA DE PONTO ===");
        System.out.println("1. Login");
        System.out.println("2. Sair");
        System.out.print("Escolha: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine();
        
        switch (opcao) {
            case 1 -> fazerLogin();
            case 2 -> System.exit(0);
            default -> System.out.println("Opção inválida!");
        }
    }

    private static void fazerLogin() {
        System.out.print("\nCPF: ");
        String cpf = scanner.nextLine();

        usuarioAtual = funcionarioDAO.buscarGerentePorCPF(cpf);

        if (usuarioAtual == null) {
            usuarioAtual = funcionarioDAO.buscarPorCPF(cpf);
        }
        if (usuarioAtual == null) {
            System.out.println("Usuário não encontrado!");
        } else {
            System.out.println("\nBem-vindo, " + usuarioAtual.getnome() + "!");
        }
    }

    private static void exibirMenuFuncionario() {
        System.out.println("\n=== MENU FUNCIONÁRIO ===");
        System.out.println("1. Registrar Entrada");
        System.out.println("2. Registrar Saída");
        System.out.println("3. Consultar Registros");
        System.out.println("4. Logout");
        System.out.print("Escolha: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine();
        
        switch (opcao) {
            case 1 -> registrarEntrada();
            case 2 -> registrarSaida();
            case 3 -> consultarRegistros();
            case 4 -> usuarioAtual = null;
            default -> System.out.println("Opção inválida!");
        }
    }
     private static void exibirMenuGerente() {
        System.out.println("\n=== MENU GERENTE ===");
        System.out.println("1. Registrar Entrada");
        System.out.println("2. Registrar Saída");
        System.out.println("3. Consultar Registros");
        System.out.println("4. Logout");
        System.out.print("Escolha: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine();
        
        switch (opcao) {
            case 1 -> registrarEntrada();
            case 2 -> registrarSaida();
            case 3 -> consultarRegistros();
            case 4 ->usuarioAtual = null;
            default -> System.out.println("Opção inválida!");
        }
    }
    private static void consultarRegistros() {
        List<RegistraPonto> registros = pontoService.listarRegistros((Funcionario)usuarioAtual);
        exibirRegistros(registros, "SEUS REGISTROS");
    }

    private static void registrarSaida() {
        try {
            pontoService.registrarSaida((Funcionario)usuarioAtual);
            System.out.println("✅ Saída registrada: " + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        } catch (IllegalStateException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }
    private static void registrarEntrada() {
        try {
            pontoService.registrarEntrada((Funcionario) usuarioAtual);
            System.out.println("✅ Entrada registrada!");
        } catch (IllegalStateException | ClassCastException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    private static void exibirRegistros(List<RegistraPonto> registros, String titulo) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        System.out.println("\n📋 " + titulo + ":");
        System.out.println("----------------------------------------");

        registros.forEach(reg -> {
            System.out.printf("%s | %s | Entrada: %s | Saída: %s | Horas: %d\n",
                reg.getfuncionario().getnome(),
                reg.gethoraEntrada().toLocalDate(),
                reg.gethoraEntrada().toLocalTime(),
                reg.gethoraSaida() != null ? reg.gethoraSaida().toLocalTime() : "---",
                reg.calcularHorasTrabalhadas());
        });
    }
    
}
