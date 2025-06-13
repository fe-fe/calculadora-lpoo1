/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package me.mafer.calculadora;

import java.util.ArrayList;
import java.util.List;
import static me.mafer.calculadora.TipoElemento.OPERADOR;
import static me.mafer.calculadora.TipoElemento.OPERANDO;

/**
 *
 * @author fefe
 */
public class Calculadora {
    private static final int maxPrioridade = 2;
    private static TipoElemento ultimaOperacao = TipoElemento.OPERANDO;
    public static List<ElementoOperacao> listaElementos = new ArrayList<>();
    static {
        listaElementos.add(new ElementoOperacao("0", TipoElemento.OPERANDO, 0));
    }
    
    public static TipoElemento getUltimaOperacao() {
        return ultimaOperacao;
    }
    
    public static void setUltimaOperacao(TipoElemento tipoOperacao) {
        ultimaOperacao = tipoOperacao;
    }
    
    public static String buildCalculoExibido() {
        String calculo = "";
        for (ElementoOperacao au : listaElementos) {
            calculo += au.toString();
        }
        return calculo;
    }
    
    public static void calcularResultado() {
        int prioridadeAtual = maxPrioridade;
        while (prioridadeAtual != 0) {
            for (int i = 1; i < listaElementos.size(); i += 2) {
                if (listaElementos.get(i).getPrioridade() == prioridadeAtual) {
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
        float operandoUm = Float.parseFloat(listaElementos.get(indice-1).getElemento());
        ElementoOperacao operador = listaElementos.get(indice);
        float operandoDois = Float.parseFloat(listaElementos.get(indice+1).getElemento());
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
        listaElementos.remove(indice+1);
        listaElementos.remove(indice);
        listaElementos.remove(indice-1);
        ElementoOperacao novoValor = new ElementoOperacao(String.valueOf(val), TipoElemento.OPERANDO, 0);
        listaElementos.add(indice-1, novoValor);
    }
    
    public static void handleCalcular() {
        if (getUltimaOperacao() != TipoElemento.OPERANDO) {
            return;
        }
        calcularResultado();
    }
    
    public static void handleNovoOperando(String operando) {
        switch (getUltimaOperacao()) {
            case OPERADOR -> {
                listaElementos.add(new ElementoOperacao(operando, TipoElemento.OPERANDO, 0));
                setUltimaOperacao(TipoElemento.OPERANDO);
            }
            case OPERANDO -> {
                int ultimoIndice = listaElementos.size() - 1;
                listaElementos.get(ultimoIndice).concatElemento(operando);
            }
            default -> System.out.println("operacao invalida");
        }
    }
    
    public static void handleNovoOperador(String operador, int prioridade) {
        switch (getUltimaOperacao()) {
            case OPERANDO -> {
                boolean unicoElemento = listaElementos.size() == 1;
                boolean elementoZerado = Float.valueOf(listaElementos.get(0).getElemento()) == 0;
                boolean operadorMenos = operador.equals("-");
                if (operadorMenos && unicoElemento && elementoZerado) {
                    listaElementos.get(0).setElemento("-");
                } else {
                    listaElementos.add(new ElementoOperacao(operador, TipoElemento.OPERADOR, prioridade));
                    setUltimaOperacao(TipoElemento.OPERADOR);
                }
                
            }
            default -> System.out.println("operacao invalida");
        }
    }
    
    public static void handleReset() {
        listaElementos.clear();
        ElementoOperacao operandoInicial = new ElementoOperacao("0", TipoElemento.OPERANDO, 0);
        listaElementos.add(operandoInicial);
    }
    
    public static void handleDelete() {
        int ultimoIndice = listaElementos.size() - 1;
        switch (ultimaOperacao) {
            case OPERANDO -> {
                ElementoOperacao elemento = listaElementos.get(ultimoIndice); // pega o elemento atual (ultimo da lista)
                if (elemento.getElemento().length() == 1) { // se o elemento tiver apenas 1 de length
                    if (listaElementos.size() > 1) { // verifica se a lista vai ficar vazia com a remocao
                        listaElementos.remove(ultimoIndice); // se nao for ficar vazia, remove o elemento atual
                        ultimaOperacao = listaElementos.get(listaElementos.size() - 1).getTipoOperacao(); // atualiza a ultima operacao
                    } else {
                        elemento.setElemento("0");
                    }
                } else {
                    elemento.subElemento(1);
                }
            }
            case OPERADOR -> {
                listaElementos.remove(ultimoIndice);
                ultimaOperacao = listaElementos.get(ultimoIndice - 1).getTipoOperacao();
            }
        }
    }
}
