package hoyobank;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Banco {

    private static List<Conta> contas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    criarConta();
                    break;
                case 2:
                    realizarDeposito();
                    break;
                case 3:
                    realizarSaque();
                    break;
                case 4:
                    realizarTransferencia();
                    break;
                case 5:
                    listarContas();
                    break;
                case 6:
                    calcularTotalTributos();
                    break;
                case 7:
                    System.out.println("Encerrando o sistema... Obrigado por usar o HoYo Bank!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            System.out.println(); 
        } while (opcao != 7);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("------ BEM-VINDO AO HOYO BANK ------");
        System.out.println("1. Criar Conta");
        System.out.println("2. Realizar Depósito");
        System.out.println("3. Realizar Saque");
        System.out.println("4. Realizar Transferência");
        System.out.println("5. Listar Contas");
        System.out.println("6. Calcular Total de Tributos");
        System.out.println("7. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void criarConta() {
        System.out.print("Digite seu nome ou nome do cliente: ");
        String nome = scanner.nextLine();

        System.out.println("Escolha o tipo de conta:");
        System.out.println("1. Conta Corrente");
        System.out.println("2. Conta Poupança");
        System.out.print("Opção: ");
        int tipoConta = scanner.nextInt();
        scanner.nextLine();

        Conta novaConta;
        if (tipoConta == 1) {
            novaConta = new ContaCorrente(nome);
        } else if (tipoConta == 2) {
            novaConta = new ContaPoupanca(nome);
        } else {
            System.out.println("Tipo de conta inválido.");
            return;
        }

        contas.add(novaConta);
        System.out.println("Conta criada com sucesso! Número da conta: " + novaConta.getNumero());
    }

    private static void realizarDeposito() {
        System.out.print("Digite o número da conta: ");
        int numero = scanner.nextInt();
        Conta conta = encontrarContaPorNumero(numero);

        if (conta != null) {
            System.out.print("Digite o valor a ser depositado: ");
            double valor = scanner.nextDouble();
            conta.depositar(valor);
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void realizarSaque() {
        System.out.print("Digite o número da conta: ");
        int numero = scanner.nextInt();
        Conta conta = encontrarContaPorNumero(numero);

        if (conta != null) {
            System.out.print("Digite o valor a ser sacado: ");
            double valor = scanner.nextDouble();
            conta.sacar(valor); 
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void realizarTransferencia() {
        System.out.print("Digite o número da conta de ORIGEM: ");
        int numOrigem = scanner.nextInt();
        Conta contaOrigem = encontrarContaPorNumero(numOrigem);

        if (contaOrigem == null) {
            System.out.println("Conta de origem não encontrada.");
            return;
        }

        System.out.print("Digite o número da conta de DESTINO: ");
        int numDestino = scanner.nextInt();
        Conta contaDestino = encontrarContaPorNumero(numDestino);

        if (contaDestino == null) {
            System.out.println("Conta de destino não encontrada.");
            return;
        }
        
        if (contaOrigem == contaDestino) {
            System.out.println("A conta de origem e destino não podem ser a mesma.");
            return;
        }

        System.out.print("Digite o valor a ser transferido: ");
        double valor = scanner.nextDouble();
        
        contaOrigem.transferir(valor, contaDestino);
    }

    private static void listarContas() {
        System.out.println("\n--- LISTA DE CONTAS CADASTRADAS ---");
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
        } else {
            for (Conta conta : contas) {
                System.out.println(conta);
            }
        }
        System.out.println("-------------------------------------");
    }

    private static void calcularTotalTributos() {
        double totalTributos = 0.0;
        for (Conta conta : contas) {
            if (conta instanceof ITributavel) {
                ITributavel contaTributavel = (ITributavel) conta;
                totalTributos += contaTributavel.calculaTributos();
            }
        }
        System.out.println("\n========================================");
        System.out.println("O total de tributos a ser recolhido é: R$" + String.format("%.2f", totalTributos));
        System.out.println("========================================");

    }

    private static Conta encontrarContaPorNumero(int numero) {
        for (Conta conta : contas) {
            if (conta.getNumero() == numero) {
                return conta;
            }
        }
        return null;
    }
}