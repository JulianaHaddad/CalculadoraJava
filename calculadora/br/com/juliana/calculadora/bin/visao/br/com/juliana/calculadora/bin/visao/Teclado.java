package br.com.juliana.calculadora.bin.visao;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Teclado extends JPanel implements ActionListener {
   private final Color COR_CINZA_ESCURO = new Color(68, 68, 68);
   private final Color COR_CINZA_CLARO = new Color(99, 99, 99);
   private final Color COR_LARANJA = new Color(242, 163, 60);

   public Teclado() {
      GridBagLayout layout = new GridBagLayout();
      GridBagConstraints c = new GridBagConstraints();
      this.setLayout(layout);
      c.weightx = 1.0D;
      c.weighty = 1.0D;
      c.fill = 1;
      c.gridwidth = 2;
      this.adicionarBotao("AC", this.COR_CINZA_ESCURO, c, 0, 0);
      c.gridwidth = 1;
      this.adicionarBotao("\u00b1", this.COR_CINZA_ESCURO, c, 2, 0);
      this.adicionarBotao("/", this.COR_LARANJA, c, 3, 0);
      this.adicionarBotao("7", this.COR_CINZA_CLARO, c, 0, 1);
      this.adicionarBotao("8", this.COR_CINZA_CLARO, c, 1, 1);
      this.adicionarBotao("9", this.COR_CINZA_CLARO, c, 2, 1);
      this.adicionarBotao("*", this.COR_LARANJA, c, 3, 1);
      this.adicionarBotao("4", this.COR_CINZA_CLARO, c, 0, 2);
      this.adicionarBotao("5", this.COR_CINZA_CLARO, c, 1, 2);
      this.adicionarBotao("6", this.COR_CINZA_CLARO, c, 2, 2);
      this.adicionarBotao("-", this.COR_LARANJA, c, 3, 2);
      this.adicionarBotao("1", this.COR_CINZA_CLARO, c, 0, 3);
      this.adicionarBotao("2", this.COR_CINZA_CLARO, c, 1, 3);
      this.adicionarBotao("3", this.COR_CINZA_CLARO, c, 2, 3);
      this.adicionarBotao("+", this.COR_LARANJA, c, 3, 3);
      c.gridwidth = 2;
      this.adicionarBotao("0", this.COR_CINZA_CLARO, c, 0, 4);
      c.gridwidth = 1;
      this.adicionarBotao(",", this.COR_CINZA_CLARO, c, 2, 4);
      this.adicionarBotao("=", this.COR_LARANJA, c, 3, 4);
   }

   private void adicionarBotao(String texto, Color cor, GridBagConstraints c, int x, int y) {
      c.gridx = x;
      c.gridy = y;
      Botao botao = new Botao(texto, cor);
      botao.addActionListener(this);
      this.add(botao, c);
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() instanceof JButton) {
         JButton botao = (JButton)e.getSource();
         Memoria.getInstancia().processarComando(botao.getText());
      }

   }
}
