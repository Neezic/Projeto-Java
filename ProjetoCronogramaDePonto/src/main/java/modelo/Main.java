package modelo;
import dao.FuncionarioDao;
import java.time.*;
import java.time.format.*;
import java.util.*;
import servico.PontoService;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static PontoService pontoService = new PontoService();
    private static FuncionarioDao funcionarioDAO = new FuncionarioDao();
    private static Usuario usuarioAtual = null;

    public static void main(String[] args) {
        // Inicializa dados de teste
        Departamento ti = new Departamento("TI");
        Cargo dev = new Cargo("Desenvolvedor", 5000.00);
        Cargo gerenteCargo = new Cargo("Gerente", 8000.00);

        // Cadastro de Usuario e polimorfismo classe Gerente herda de Funcionario. O Gerente pode ser tratado como um Funcionario devido à herança. dsd compartilhem a mesma classe base
        Funcionario funcionario = new Funcionario("João", "12345678900", ti, dev);
        Gerente gerente = new Gerente("Maria", "98765432100", ti, gerenteCargo);
        
        funcionarioDAO.salvar(funcionario);
        funcionarioDAO.salvar(gerente); // Gerente é um Funcionario
        
        // Loop principal
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

  

    // MENU DE LOGIN (igual ao seu original)
    private static void exibirMenuLogin() {
        System.out.println("\n=== SISTEMA DE PONTO ===");
        System.out.println("1. Login");
        System.out.println("2. Sair");
        System.out.print("Escolha: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine();
        
        switch (opcao) {
            case 1 -> fazerLogin();
            case 2 -> {
                System.out.println("Saindo do sistema...");
                System.exit(0);
            }
            default -> System.out.println("Opção inválida!");
        }
    }

    private static void fazerLogin() {
        System.out.print("\nCPF (digite 123 ou 456): ");
        String cpf = scanner.nextLine();

        usuarioAtual = funcionarioDAO.buscarGerentePorCPF(cpf);
        if (usuarioAtual == null){
            usuarioAtual = funcionarioDAO.buscarPorCPF(cpf);
        } 
        
        if (usuarioAtual == null) {
            System.out.println("Usuário não encontrado!");
        } else {
            System.out.println("\nBem-vindo, " + usuarioAtual.getnome() + "!");
        }
    }

    // MENU DE FUNCIONÁRIO (similar ao seu original)
    private static void exibirMenuFuncionario() {
        System.out.println("\n=== MENU FUNCIONÁRIO ===");
        System.out.println("1. Registrar Entrada");
        System.out.println("2. Registrar Saída");
        System.out.println("3. Consultar Meus Registros");
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

    // NOVO MENU ESPECÍFICO PARA GERENTES
    private static void exibirMenuGerente() {
        System.out.println("\n=== MENU GERENTE ===");
        System.out.println("1. Registrar Entrada");
        System.out.println("2. Registrar Saída");
        System.out.println("3. Consultar Registros da Equipe");
        System.out.println("4. Aprovar Registros Pendentes");
        System.out.println("5. Logout");
        System.out.print("Escolha: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine();
        
        switch (opcao) {
            case 1 -> registrarEntrada();
            case 2 -> registrarSaida();
            case 3 ->aprovarRegistroPendente();
            case 4 -> usuarioAtual = null;
            default -> System.out.println("Opção inválida!");
        }
        

    }
    private static void aprovarRegistroPendente() {
        // Verifica se o usuário atual é um Gerente
        if (!(usuarioAtual instanceof Gerente)) {
            System.out.println("Apenas gerentes podem aprovar registros!");
            return;
        }
        
        // Cast seguro para Gerente
        Gerente gerente = (Gerente) usuarioAtual;
        
        // Solicita CPF
        System.out.print("Digite o CPF do funcionário: ");
        String cpf = scanner.nextLine();
        
        // Busca funcionário
        Funcionario funcionario = funcionarioDAO.buscarPorCPF(cpf);
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado!");
            return;
        }
        
        // Busca e aprova registro
        RegistraPonto registro = pontoService.buscarRegistroAberto(funcionario);
        if (registro != null) {
            gerente.aprovarRegistroPonto(registro);
        } else {
            System.out.println("Nenhum registro pendente encontrado para " + funcionario.getnome());
        }
    }

    // MÉTODOS COMPARTILHADOS
    private static void registrarEntrada() {
        try {
            pontoService.registrarEntrada((Funcionario)usuarioAtual);
            System.out.println("✅ Entrada registrada: " + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        } catch (IllegalStateException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
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

    private static void consultarRegistros() {
        List<RegistraPonto> registros = pontoService.listarRegistros((Funcionario)usuarioAtual);
        exibirRegistros(registros, "SEUS REGISTROS");
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