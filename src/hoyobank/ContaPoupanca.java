package hoyobank;


public class ContaPoupanca extends Conta {

    public ContaPoupanca(String cliente) {
        super(cliente);
    }

    @Override
    public boolean sacar(double valor) {
        if (valor <= 0) {
            System.out.println("Valor de saque inválido.");
            return false;
        }

        if (super.saldo >= valor) {
            super.saldo -= valor;
            System.out.println("Saque realizado com sucesso!");
            return true;
        } else {
            System.out.println("Saldo insuficiente.");
            return false;
        }
    }

    @Override
    public String toString() {
        return "[Conta Poupança] " + super.toString();
    }
}