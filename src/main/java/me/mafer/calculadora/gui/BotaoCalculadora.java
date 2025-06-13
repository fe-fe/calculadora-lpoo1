/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.mafer.calculadora.gui;

import me.mafer.calculadora.TipoElemento;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import me.mafer.calculadora.Calculadora;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D; // Import Graphics2D
import java.awt.RenderingHints; // Import RenderingHints
import java.awt.Shape; // Import Shape
import java.awt.geom.RoundRectangle2D; // Import RoundRectangle2D
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Maria Fernanda Zandona Casagrande
 */
public class BotaoCalculadora extends JButton {
    
    public static JLabel labelExibicao;
    private final String acao;
    private final TipoElemento tipoOperacao;
    private final int prioridade;
    
    private final ActionListener handleClick = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (tipoOperacao) {
                case OPERANDO -> Calculadora.handleNovoOperando(acao);
                case OPERADOR -> Calculadora.handleNovoOperador(acao, prioridade);
                case CALCULAR -> Calculadora.handleCalcular();
                case RESET -> Calculadora.handleReset();
                case DELETE -> Calculadora.handleDelete();
            }
            labelExibicao.setText(Calculadora.buildCalculoExibido());
        }
    };
    
    public BotaoCalculadora(String text, TipoElemento tipoOperacao, int prioridade) {
        super(text);
        this.tipoOperacao = tipoOperacao;
        this.acao = text;
        this.setText(text);
        this.setFocusable(false);
        this.prioridade = prioridade;
        this.addActionListener(handleClick);
        this.setForeground(new java.awt.Color(255, 255, 255));
        this.setBorder(new EmptyBorder(15, 15, 15, 15));
        switch (tipoOperacao) {
            case OPERANDO -> this.setBackground(new java.awt.Color(56, 52, 52));
            case CALCULAR, RESET, DELETE -> this.setBackground(new java.awt.Color(51, 1, 115));
            default -> this.setBackground(new java.awt.Color(40, 36, 36));
        }
        
        // codigo copiado do gemini para deixar a borda redonda e continuar com o hover effect
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        Color defaultBg = this.getBackground(); // Get the initial background color
        Color hoverBg;
        
        switch (tipoOperacao) {
            case OPERANDO -> hoverBg = new Color(70, 65, 65);
            case CALCULAR, RESET, DELETE -> hoverBg = new Color(56, 0, 129);
            default -> hoverBg = new Color(50, 45, 45);
        }
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(hoverBg);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(defaultBg); // Revert to initial background color
            }
        });
    }
    
    // CODIGO A PARTIR DE AQUI FOI COPIADO DO GEMINI
    // SERVE PARA DEIXAR A BORDA DOS BOTOES REDONDA
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Define the shape for the rounded rectangle with 15 pixel border-radius
        Shape shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        g2.setPaint(getBackground()); // Use the button's current background color
        g2.fill(shape);

        super.paintComponent(g2); // Paint the text and icon
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Do not paint the default border, making it invisible
    }

    @Override
    public boolean contains(int x, int y) {
        // This ensures the button's clickable area is also rounded, matching its visual appearance
        Shape shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        return shape.contains(x, y);
    }
}
