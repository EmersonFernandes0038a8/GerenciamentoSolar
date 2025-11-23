package hoyobank;


public class ContaCorrente extends Conta implements ITributavel {

    private static final double TAXA_SAQUE = 0.05; 

    public ContaCorrente(String cliente) {
        super(cliente);
    }

    @Override
    public boolean sacar(double valor) {
        if (valor <= 0) {
            System.out.println("Valor de saque inválido.");
            return false;
        }

        double valorComTaxa = valor + (valor * TAXA_SAQUE);
        if (super.saldo >= valorComTaxa) {
            super.saldo -= valorComTaxa;
            System.out.println("Saque realizado com sucesso. Taxa de R$" + String.format("%.2f", valor * TAXA_SAQUE) + " aplicada.");
            return true;
        } else {
            System.out.println("Saldo insuficiente para realizar o saque com a taxa.");
            return false;
        }
    }

    @Override
    public double calculaTributos() {
        return this.saldo * 0.01; 
    }

    @Override
    public String toString() {
        return "[Conta Corrente] " + super.toString();
    }
}