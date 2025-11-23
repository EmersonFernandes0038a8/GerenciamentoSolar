package hoyobank;


public abstract class Conta {

    private static int proximoNumero = 101; 
    protected int numero;
    protected String cliente;
    protected double saldo;

    public Conta(String cliente) {
        this.numero = proximoNumero++;
        this.cliente = cliente;
        this.saldo = 0.0;
    }

    public int getNumero() {
        return numero;
    }

    public String getCliente() {
        return cliente;
    }

    public double getSaldo() {
        return saldo;
    }


    public void depositar(double valor) {
        if (valor > 0) {
            this.saldo += valor;
            System.out.println("Depósito realizado com sucesso!");
        } else {
            System.out.println("Valor de depósito inválido.");
        }
    }

    public abstract boolean sacar(double valor);


    public boolean transferir(double valor, Conta destino) {
        if (this.sacar(valor)) {
            destino.depositar(valor);
            System.out.println("Transferência realizada com sucesso!");
            return true;
        } else {
            System.out.println("Transferência falhou.");
            return false;
        }
    }

    @Override
    public String toString() {
        return "Número: " + numero +
               ", Cliente: " + cliente +
               ", Saldo: R$" + String.format("%.2f", saldo);
    }
}