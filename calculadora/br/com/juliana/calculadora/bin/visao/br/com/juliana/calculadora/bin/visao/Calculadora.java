package br.com.juliana.calculadora.bin.visao;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JFrame;
import br.com.juliana.calculadora.bin.visao.Calculadora;

public class Calculadora extends JFrame {
   public Calculadora() {
      this.organizarLayout();
      this.setSize(250, 322);
      this.setDefaultCloseOperation(3);
      this.setLocationRelativeTo((Component)null);
      this.setVisible(true);
   }

   private void organizarLayout() {
      this.setLayout(new BorderLayout());
      Display display = new Display();
      display.setPreferredSize(new Dimension(250, 60));
      this.add(display, "North");
      Teclado teclado = new Teclado();
      this.add(teclado, "Center");
   }

   public static void main(String[] args) {
      new Calculadora();
   }
}
