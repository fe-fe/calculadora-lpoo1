/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.mafer.calculadora;

/**
 *
 * @author fefe
 */
public class AcaoUsuario {
    private String elemento;
    private final TipoOperacao tipoOperacao;
    private final int prioridade;
    
    public AcaoUsuario(String elemento, TipoOperacao tipoOperacao, int prioridade) {
        this.elemento = elemento;
        this.tipoOperacao = tipoOperacao;
        this.prioridade = prioridade;
    }
    
    public static void handleCalcular() {
        if (Calculadora.getUltimaOperacao() != TipoOperacao.OPERANDO) {
            return;
        }
        Calculadora.calcularResultado();
    }
    
    public static void handleNovoOperando(String operando) {
        switch (Calculadora.getUltimaOperacao()) {
            case OPERADOR -> {
                Calculadora.historicoAcoes.add(new AcaoUsuario(operando, TipoOperacao.OPERANDO, 0));
                Calculadora.setUltimaOperacao(TipoOperacao.OPERANDO);
            }
            case OPERANDO -> {
                int ultimoIndice = Calculadora.historicoAcoes.size() - 1;
                Calculadora.historicoAcoes.get(ultimoIndice).concatElemento(operando);
            }
            default -> System.out.println("operacao invalida");
        }
        System.out.println("tam = " + Calculadora.historicoAcoes.size());
    }
    
    public static void handleNovoOperador(String operador, int prioridade) {
        System.out.println(Calculadora.getUltimaOperacao());
        switch (Calculadora.getUltimaOperacao()) {
            case OPERANDO -> {
                Calculadora.historicoAcoes.add(new AcaoUsuario(operador, TipoOperacao.OPERANDO, prioridade));
                Calculadora.setUltimaOperacao(TipoOperacao.OPERADOR);
            }
            default -> System.out.println("operacao invalida");
        }
    }
    
    public static void handleReset() {
        Calculadora.historicoAcoes.clear();
        AcaoUsuario operandoInicial = new AcaoUsuario("0", TipoOperacao.OPERANDO, 0);
        Calculadora.historicoAcoes.add(operandoInicial);
    }
    
    // para numeros
    public void concatElemento(String novoElemento) {
        if (this.elemento.equals("0")) {
            this.elemento = novoElemento;
        } else {
            this.elemento += novoElemento;
        }
    }
    
    public String getElemento() {
        return elemento;
    }
    
    public TipoOperacao getTipoOperacao() {
        return tipoOperacao;
    }
    
    public int getPrioridade() {
        return prioridade;
    }
    
    @Override
    public String toString() {
        return " " + this.elemento;
    }
}
