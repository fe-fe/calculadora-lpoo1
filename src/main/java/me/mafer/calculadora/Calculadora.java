/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package me.mafer.calculadora;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fefe
 */
public class Calculadora {
    private static final int maxPrioridade = 2;
    private static TipoOperacao ultimaOperacao = TipoOperacao.OPERANDO;
    public static List<AcaoUsuario> historicoAcoes = new ArrayList<>();
    static {
        historicoAcoes.add(new AcaoUsuario("0", TipoOperacao.OPERANDO, 0));
    }
    
    public static TipoOperacao getUltimaOperacao() {
        return ultimaOperacao;
    }
    
    public static void setUltimaOperacao(TipoOperacao tipoOperacao) {
        ultimaOperacao = tipoOperacao;
    }
    
    public static String buildCalculoExibido() {
        String calculo = "";
        for (AcaoUsuario au : historicoAcoes) {
            calculo += au.toString();
        }
        return calculo;
    }
    
    public static void calcularResultado() {
        int prioridadeAtual = maxPrioridade;
        while (prioridadeAtual != 0) {
            for (int i = 1; i < historicoAcoes.size(); i += 2) {
                if (historicoAcoes.get(i).getPrioridade() == prioridadeAtual) {
                    float resultado = realizarOperacao(i);
                    sobrescreverOperacoes(i, resultado);
                    prioridadeAtual++; // gambiarra horrorosa
                    break; // gambiarra horrorosa
                }
            }
            prioridadeAtual--;
        }
    }
    
    private static float realizarOperacao(int indice) {
        float operandoUm = Float.parseFloat(historicoAcoes.get(indice-1).getElemento());
        AcaoUsuario operador = historicoAcoes.get(indice);
        float operandoDois = Float.parseFloat(historicoAcoes.get(indice+1).getElemento());
        switch (operador.getElemento()) {
            case "+" -> {
                return (float) operandoUm + operandoDois;
            }
            case "-" -> {
                return (float) operandoUm - operandoDois;
            }
            case "x" -> {
                return (float) operandoUm * operandoDois;
            }
            case "/" -> {
                return (float) operandoUm / operandoDois;
            }
        }
        return (float) 0;
    } 
    
    // apos realizar uma operacao, sobrescreve as AcoesUsuario
    // com o resultado da operacao em formato de operando
    // ex: 1 + 2 -> sao os indices 0, 1, 2
    // apos a operacao,  tem resultado 3 e sobrescreve
    // 3 no indice 0
    private static void sobrescreverOperacoes(int indice, float val) {
        historicoAcoes.remove(indice+1);
        historicoAcoes.remove(indice);
        historicoAcoes.remove(indice-1);
        AcaoUsuario novoValor = new AcaoUsuario(String.valueOf(val), TipoOperacao.OPERANDO, 0);
        historicoAcoes.add(indice-1, novoValor);
    }
}
