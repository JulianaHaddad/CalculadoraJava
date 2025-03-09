package br.com.juliana.calculadora.bin.visao;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class Botao extends JButton {
   public Botao(String texto, Color cor) {
      this.setText(texto);
      this.setOpaque(true);
      this.setBackground(cor);
      this.setFont(new Font("courier", 0, 25));
      this.setForeground(Color.WHITE);
      this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      this.setFocusable(false);
   }
}
