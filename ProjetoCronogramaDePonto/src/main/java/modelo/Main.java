package main.java.modelo;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.stream.Collectors;
import main.java.dao.FuncionarioDao;
import main.java.dao.RegistraPontoDAO;
import main.java.dao.CargoDAO;
import main.java.dao.DepartamentoDAO;
import main.java.servico.PontoService;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static PontoService pontoService;
    private static FuncionarioDao funcionarioDAO;
    private static Usuario usuarioAtual;
    private static RegistraPontoDAO registroDAO;
    private static CargoDAO cargoDAO;
    private static DepartamentoDAO departamentoDAO;

    public static void main(String[] args) {
        // Inicialização com injeção de dependência
        registroDAO = new RegistraPontoDAO();
        funcionarioDAO = new FuncionarioDao();
        pontoService = new PontoService(registroDAO); // Dependência injetada
        departamentoDAO = new DepartamentoDAO();
        cargoDAO = new CargoDAO();

       configurarDadosIniciaisSeNecessario();

        Funcionario funcionario = funcionarioDAO.buscarPorCPF("123");
        Gerente gerente = funcionarioDAO.buscarGerentePorCPF("456");


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
    private static void configurarDadosIniciaisSeNecessario() {
        // Departamentos
        Departamento ti = departamentoDAO.buscarPorPrefixo("TI").stream()
                                     .filter(d -> d.getNome().equals("TI"))
                                     .findFirst()
                                     .orElse(null);
        if (ti == null) {
            ti = new Departamento("TI"); //
            departamentoDAO.salvar(ti);
            System.out.println("Departamento 'TI' criado e salvo.");
        }

        // Cargos
        Cargo dev = cargoDAO.buscarPorNome("Desenvolvedor");
        if (dev == null) {
            dev = new Cargo("Desenvolvedor", 5000); //
            // Você pode querer adicionar níveis aos cargos se for usar o NivelCargo do CargoDAO
            // dev.setNivel("JUNIOR"); // Exemplo
            cargoDAO.salvar(dev);
            System.out.println("Cargo 'Desenvolvedor' criado e salvo.");
        }

        Cargo gerenteCargo = cargoDAO.buscarPorNome("Gerente");
        if (gerenteCargo == null) {
            gerenteCargo = new Cargo("Gerente", 8000); //
            // gerenteCargo.setNivel("GERENCIAL"); // Exemplo
            cargoDAO.salvar(gerenteCargo);
            System.out.println("Cargo 'Gerente' criado e salvo.");
        }

        // Funcionários (exemplo de como garantir que existam)
        Funcionario funcionarioExistente = funcionarioDAO.buscarPorCPF("123");
        if (funcionarioExistente == null) {
            Funcionario novoFuncionario = new Funcionario("João", "123", ti, dev); //
            funcionarioDAO.salvar(novoFuncionario);
            System.out.println("Funcionário 'João' criado e salvo.");
        }

        Gerente gerenteExistente = funcionarioDAO.buscarGerentePorCPF("456");
        if (gerenteExistente == null) {
            Gerente novoGerente = new Gerente("Maria", "456", ti, gerenteCargo); //
            funcionarioDAO.salvar(novoGerente); // Salva o gerente
            System.out.println("Gerente 'Maria' criada e salva.");
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
        System.out.println("4. Cadastrar Funcionario Novo:");
        System.out.println("5. Cadastrar Cargo Novo:");
        System.out.println("6. Cadastrar Departamento Novo:");
        System.out.println("7. Logout.");
        System.out.print("Escolha: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine();
        
        switch (opcao) {
            case 1 -> registrarEntrada();
            case 2 -> registrarSaida();
            case 3 -> consultarRegistros();
            case 4 ->   cadastrarNovoFuncionario();
            case 5 -> cadastrarNovoCargo();
            case 6 -> cadastrarNovoDepartamento();
            case 7 -> usuarioAtual = null;
            default -> System.out.println("Opção inválida!");
        }
    }
    private static void cadastrarNovoFuncionario() {
        System.out.println("\n=== CADASTRAR NOVO FUNCIONÁRIO ===");
        System.out.print("Nome do funcionário: ");
        String nome = scanner.nextLine();
        System.out.print("CPF do funcionário: ");
        String cpf = scanner.nextLine();

        // Verificar se CPF já existe
        if (funcionarioDAO.buscarPorCPF(cpf) != null || funcionarioDAO.buscarGerentePorCPF(cpf) != null) {
            System.out.println("Erro: CPF já cadastrado no sistema.");
            return;
        }

        // Selecionar Departamento
        System.out.println("\nDepartamentos disponíveis:");
        List<Departamento> departamentos = departamentoDAO.buscarPorPrefixo(""); //
        if (departamentos.isEmpty()) {
            System.out.println("Nenhum departamento cadastrado. Cadastre departamentos primeiro.");
            return;
        }
        for (int i = 0; i < departamentos.size(); i++) {
            System.out.println((i + 1) + ". " + departamentos.get(i).getNome());
        }
        System.out.print("Escolha o número do departamento: ");
        int escolhaDep = scanner.nextInt();
        scanner.nextLine(); // Consumir nova linha
        Departamento departamentoSelecionado = null;
        if (escolhaDep > 0 && escolhaDep <= departamentos.size()) {
            departamentoSelecionado = departamentos.get(escolhaDep - 1);
        } else {
            System.out.println("Escolha de departamento inválida.");
            return;
        }

        // Selecionar Cargo
        System.out.println("\nCargos disponíveis:");
        List<Cargo> cargos = cargoDAO.listarTodos(); //
        if (cargos.isEmpty()) {
            System.out.println("Nenhum cargo cadastrado. Cadastre cargos primeiro.");
            return;
        }
        for (int i = 0; i < cargos.size(); i++) {
            System.out.println((i + 1) + ". " + cargos.get(i).getNome() + " (Salário: " + cargos.get(i).getsalarioBase() + ")");
        }
        System.out.print("Escolha o número do cargo: ");
        int escolhaCargo = scanner.nextInt();
        scanner.nextLine(); // Consumir nova linha
        Cargo cargoSelecionado = null;
        if (escolhaCargo > 0 && escolhaCargo <= cargos.size()) {
            cargoSelecionado = cargos.get(escolhaCargo - 1);
        } else {
            System.out.println("Escolha de cargo inválida.");
            return;
        }

        // Criar e salvar novo funcionário
        Funcionario novoFuncionario = new Funcionario(nome, cpf, departamentoSelecionado, cargoSelecionado);
        funcionarioDAO.salvar(novoFuncionario);
        System.out.println("Funcionário '" + nome + "' cadastrado com sucesso!");
    }
    private static void cadastrarNovoDepartamento() {
        System.out.println("\n=== CADASTRAR NOVO DEPARTAMENTO ===");
        System.out.print("Nome do departamento: ");
        String nomeDepartamento = scanner.nextLine();

        if (nomeDepartamento.trim().isEmpty()) {
        System.out.println(" Nome do departamento não pode ser vazio.");
        return;
        }

        // Verificar se o departamento já existe
        // Usando buscarPorPrefixo para obter possíveis candidatos e depois filtrando pelo nome exato.
        // Isso é porque não temos um buscarPorNome direto no DepartamentoDAO.
        
        String finalNomeDepartamento = nomeDepartamento; // Para usar dentro da lambda
        boolean departamentoExiste = departamentoDAO.buscarPorPrefixo(finalNomeDepartamento) //
                                          .stream()
                                          .anyMatch(d -> d.getNome().equalsIgnoreCase(finalNomeDepartamento));
    
        if (departamentoExiste) {
        System.out.println(" Erro: Departamento com o nome '" + nomeDepartamento + "' já existe.");
        return;
        }

        Departamento novoDepartamento = new Departamento(nomeDepartamento);
        // Se quisesse associar um gerente ao departamento no momento da criação,
        // você precisaria de lógica adicional para selecionar um gerente existente.

        departamentoDAO.salvar(novoDepartamento); //
        System.out.println(" Departamento '" + nomeDepartamento + "' cadastrado com sucesso!");
    }

    private static void cadastrarNovoCargo() {
        System.out.println("\n=== CADASTRAR NOVO CARGO ===");
        System.out.print("Nome do cargo: ");
        String nomeCargo = scanner.nextLine();

        if (nomeCargo.trim().isEmpty()) {
        System.out.println(" Nome do cargo não pode ser vazio.");
        return;
        }

        // Verificar se o cargo já existe
        if (cargoDAO.buscarPorNome(nomeCargo) != null) { //
        System.out.println(" Erro: Cargo com o nome '" + nomeCargo + "' já existe.");
        return;
        }

        System.out.print("Salário base do cargo: ");
        double salarioBase;
        try {
        salarioBase = scanner.nextDouble();
        scanner.nextLine(); // Consumir nova linha
        if (salarioBase <= 0) {
            System.out.println(" Salário base deve ser um valor positivo.");
            return;
        }
        }   catch (InputMismatchException e) {
        System.out.println(" Entrada inválida para salário base. Use números.");
        scanner.nextLine(); // Limpar buffer do scanner
        return;
        }
    
        System.out.println("\nNíveis de Cargo Disponíveis:");
        CargoDAO.NivelCargo[] niveisDisponiveis = CargoDAO.NivelCargo.values(); //
        for (int i = 0; i < niveisDisponiveis.length; i++) {
        System.out.println((i + 1) + ". " + niveisDisponiveis[i]);
        }
        System.out.print("Escolha o número do nível do cargo (ou 0 para pular): ");
        int escolhaNivel = scanner.nextInt();
        scanner.nextLine(); // Consumir nova linha

        String nivelSelecionado = null;
        if (escolhaNivel > 0 && escolhaNivel <= niveisDisponiveis.length) {
        nivelSelecionado = niveisDisponiveis[escolhaNivel - 1].name();
        } else if (escolhaNivel != 0) {
        System.out.println("Escolha de nível inválida. O nível não será definido.");
        }


        Cargo novoCargo = new Cargo(nomeCargo, salarioBase);
        if (nivelSelecionado != null) {
        novoCargo.setNivel(nivelSelecionado); //
        }

        cargoDAO.salvar(novoCargo); //
        System.out.println(" Cargo '" + nomeCargo + "' cadastrado com sucesso!");
    }

    private static void consultarRegistros() {
        List<RegistraPonto> registros = pontoService.listarRegistros((Funcionario)usuarioAtual);
        exibirRegistros(registros, "SEUS REGISTROS");
    }

    private static void registrarSaida() {
        try {
            pontoService.registrarSaida((Funcionario)usuarioAtual);
            System.out.println("Saída registrada: " + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        } catch (RuntimeException e) {
            System.out.println("Erro ao registrar saida: " + e.getMessage());
        }
    }
    private static void registrarEntrada() {
        try {
            pontoService.registrarEntrada((Funcionario) usuarioAtual);
            System.out.println("Entrada registrada!");
        } catch (IllegalStateException | ClassCastException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void exibirRegistros(List<RegistraPonto> registros, String titulo) {
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        System.out.println("\n📋 " + titulo + ":");
        System.out.println("----------------------------------------");
        if (registros.isEmpty()){
            System.out.println("Nenhum registro encontrado.");
            return;
        }


        registros.forEach(reg -> {
            if (reg != null && reg.getfuncionario() != null && reg.gethoraEntrada() != null){
                System.out.printf("%s | %s | Entrada: %s | Saída: %s | Horas: %d\n",
                reg.getfuncionario().getnome(),
                reg.gethoraEntrada().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                reg.gethoraEntrada().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                reg.gethoraSaida() != null ? reg.gethoraSaida().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")): "---",
                reg.calcularHorasTrabalhadas());
            } else {
                System.out.println("Registro inválido encontrado");
            }
                
        });

        System.out.println("----------------------------------------");

            
            
    }
    
}
