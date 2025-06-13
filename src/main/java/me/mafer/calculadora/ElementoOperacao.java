/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.mafer.calculadora;

/**
 *
 * @author fefe
 */
public class ElementoOperacao {
    private String elemento;
    private final TipoElemento tipoOperacao;
    private final int prioridade;
    
    public ElementoOperacao(String elemento, TipoElemento tipoOperacao, int prioridade) {
        this.elemento = elemento;
        this.tipoOperacao = tipoOperacao;
        this.prioridade = prioridade;
    }
    
    // para numeros
    public void concatElemento(String novoElemento) {
        
        if (novoElemento.equals(".")) {
            if (elemento.contains(".")) {
                return;
            }
            if (elemento.equals("0")) {
                elemento += novoElemento;
                return;
            }
        }
        
        if (novoElemento.equals("0") && elemento.contains(".") && elemento.endsWith("0")) {
            return;
        }
        
        if (elemento.equals("0")) {
            elemento = novoElemento;
            return;
        }
        
        elemento += novoElemento;
        
    }
    
    public String getElemento() {
        return elemento;
    }
    
    public void setElemento(String elemento) {
        this.elemento = elemento;
    }
    
    public TipoElemento getTipoOperacao() {
        return tipoOperacao;
    }
    
    public int getPrioridade() {
        return prioridade;
    }
    
    @Override
    public String toString() {
        return " " + this.elemento;
    }
    
    public void subElemento(int qtd) {
        elemento = elemento.substring(0, elemento.length() - qtd);
    }
}
