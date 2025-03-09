package br.com.juliana.calculadora.bin.visao;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import br.com.juliana.calculadora.bin.modelo.Memoria;
import br.com.juliana.calculadora.bin.modelo.MemoriaObservador;

public class Display extends JPanel implements MemoriaObservador {
   private final JLabel label;

   public Display() {
      Memoria.getInstancia().adicionarObservador(this);
      this.setBackground(new Color(46, 49, 50));
      this.label = new JLabel(Memoria.getInstancia().getTextoAtual());
      this.label.setForeground(Color.WHITE);
      this.label.setFont(new Font("courier", 0, 30));
      this.setLayout(new FlowLayout(2, 10, 20));
      this.add(this.label);
   }

   public void valorAlterado(String novoValor) {
      this.label.setText(novoValor);
   }
}
