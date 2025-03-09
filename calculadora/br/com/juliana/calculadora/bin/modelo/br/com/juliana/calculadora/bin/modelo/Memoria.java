package br.com.juliana.calculadora.bin.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {
   private static final Memoria instancia = new Memoria();
   private final List<MemoriaObservador> observadores = new ArrayList();
   private br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando ultimaOperacao = null;
   private boolean substituir = false;
   private String textoAtual = "";
   private String textoBuffer = "";

   private Memoria() {
   }

   public static Memoria getInstancia() {
      return instancia;
   }

   public void adicionarObservador(MemoriaObservador observador) {
      this.observadores.add(observador);
   }

   public String getTextoAtual() {
      return this.textoAtual.isEmpty() ? "0" : this.textoAtual;
   }

   public void processarComando(String texto) {
      br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando tipoComando = this.detectarTipoComando(texto);
      if (tipoComando != null) {
         if (tipoComando == br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando.ZERAR) {
            this.textoAtual = "";
            this.textoBuffer = "";
            this.substituir = false;
            this.ultimaOperacao = null;
         } else if (tipoComando == br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando.SINAL && this.textoAtual.contains("-")) {
            this.textoAtual = this.textoAtual.substring(1);
         } else if (tipoComando == br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando.SINAL && !this.textoAtual.contains("-")) {
            this.textoAtual = "-" + this.textoAtual;
         } else if (tipoComando != br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando.NUMERO && tipoComando != br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando.VIRGULA) {
            this.substituir = true;
            this.textoAtual = this.obterResultadoOperacao();
            this.textoBuffer = this.textoAtual;
            this.ultimaOperacao = tipoComando;
         } else {
            this.textoAtual = this.substituir ? texto : this.textoAtual + texto;
            this.substituir = false;
         }

         this.observadores.forEach((o) -> {
            o.valorAlterado(this.getTextoAtual());
         });
      }
   }

   private String obterResultadoOperacao() {
      if (this.ultimaOperacao != null && this.ultimaOperacao != br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando.IGUAL) {
         double numeroBuffer = Double.parseDouble(this.textoBuffer.replace(",", "."));
         double numeroAtual = Double.parseDouble(this.textoAtual.replace(",", "."));
         double resultado = 0.0D;
         if (this.ultimaOperacao == br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando.SOMA) {
            resultado = numeroBuffer + numeroAtual;
         } else if (this.ultimaOperacao == br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando.SUB) {
            resultado = numeroBuffer - numeroAtual;
         } else if (this.ultimaOperacao == br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando.MULT) {
            resultado = numeroBuffer * numeroAtual;
         } else if (this.ultimaOperacao == br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando.DIV) {
            resultado = numeroBuffer / numeroAtual;
         }

         String texto = Double.toString(resultado).replace(".", ",");
         boolean inteiro = texto.endsWith(",0");
         return inteiro ? texto.replace(",0", "") : texto;
      } else {
         return this.textoAtual;
      }
   }

   private br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando detectarTipoComando(String texto) {
      if (this.textoAtual.isEmpty() && "0".equals(texto)) {
         return null;
      } else {
         try {
            Integer.valueOf(texto);
            return br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando.NUMERO;
         } catch (NumberFormatException var3) {
            if ("AC".equals(texto)) {
               return br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando.ZERAR;
            } else if ("/".equals(texto)) {
               return br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando.DIV;
            } else if ("*".equals(texto)) {
               return br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando.MULT;
            } else if ("+".equals(texto)) {
               return br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando.SOMA;
            } else if ("-".equals(texto)) {
               return br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando.SUB;
            } else if ("=".equals(texto)) {
               return br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando.IGUAL;
            } else if ("\u00b1".equals(texto)) {
               return br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando.SINAL;
            } else {
               return ",".equals(texto) && !this.textoAtual.contains(",") ? br.com.juliana.calculadora.bin.modelo.Memoria$TipoComando.VIRGULA : null;
            }
         }
      }
   }
}
