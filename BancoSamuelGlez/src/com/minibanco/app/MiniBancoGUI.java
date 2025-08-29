package com.minibanco.app;

import com.minibanco.domain.Cuenta;
import com.minibanco.domain.Movimiento;
import com.minibanco.exceptions.LimiteDiarioExcedidoException;
import com.minibanco.exceptions.SaldoInsuficienteException;
import javax.swing.*;
import java.util.List;

public class MiniBancoGUI {
    private final Cuenta cuenta;
    public MiniBancoGUI(Cuenta cuenta) { this.cuenta = cuenta; }

    public void iniciar() {
        String[] opciones = {"Consultar saldo", "Ingresar", "Retirar", "Ver movimientos", "Cambiar límite diario", "Salir"};
        while (true) {
            int seleccion = JOptionPane.showOptionDialog(null, "Cuenta: " + cuenta.getTitular() + "\nSelecciona una opción:",
                    "MiniBanco", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

            if (seleccion == -1 || seleccion == 5) {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Deseas salir?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) break; else continue;
            }

            try {
                switch (seleccion) {
                    case 0 -> consultarSaldo();
                    case 1 -> ingresar();
                    case 2 -> retirar();
                    case 3 -> verMovimientos();
                    case 4 -> cambiarLimite();
                }
            } catch (SaldoInsuficienteException | LimiteDiarioExcedidoException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Entrada numérica no válida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        JOptionPane.showMessageDialog(null, "Gracias por usar BancoSamuel. Nos vemos pronto, no olvides que en el rastro no se permite pagar con tarjeta, asegúrate de sacar efectivo :)");
    }

    private void consultarSaldo() {
        JOptionPane.showMessageDialog(null, String.format("Saldo actual: %.2f €", cuenta.getSaldo()), "Saldo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void ingresar() {
        String input = JOptionPane.showInputDialog(null, "Introduce importe a ingresar (ej: 100.50):", "Ingresar", JOptionPane.QUESTION_MESSAGE);
        if (input == null || input.trim().isEmpty()) return;
        double importe = Double.parseDouble(input.trim());
        if (importe > 0) cuenta.ingresar(importe);
    }

    private void retirar() throws SaldoInsuficienteException, LimiteDiarioExcedidoException {
        String input = JOptionPane.showInputDialog(null, "Introduce importe a retirar (ej: 50):", "Retirar", JOptionPane.QUESTION_MESSAGE);
        if (input == null || input.trim().isEmpty()) return;
        double importe = Double.parseDouble(input.trim());
        if (importe > 0) cuenta.retirar(importe);
    }

    private void verMovimientos() {
        List<Movimiento> movimientos = cuenta.getMovimientos();
        if (movimientos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay movimientos registrados.", "Movimientos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Movimiento m : movimientos) sb.append(m.toString()).append("\n");
        JTextArea textArea = new JTextArea(sb.toString()); textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea); scrollPane.setPreferredSize(new java.awt.Dimension(600, 400));
        JOptionPane.showMessageDialog(null, scrollPane, "Historial de Movimientos", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cambiarLimite() {
        String input = JOptionPane.showInputDialog(null, String.format("Límite diario actual: %.2f €. Introduce nuevo límite:", cuenta.getLimiteDiario()), "Cambiar límite", JOptionPane.QUESTION_MESSAGE);
        if (input == null || input.trim().isEmpty()) return;
        double nuevo = Double.parseDouble(input.trim());
        if (nuevo > 0) cuenta.setLimiteDiario(nuevo);
    }

    public static void main(String[] args) {
        while (true) {
            String titular = JOptionPane.showInputDialog(null, "Nombre del titular:", "BancoSamuel - Nueva de cuenta", JOptionPane.QUESTION_MESSAGE);
            if (titular == null) System.exit(0);
            titular = titular.trim();
            if (titular.isEmpty()) continue;
            Cuenta cuenta = new Cuenta(titular);
            new MiniBancoGUI(cuenta).iniciar();
            break;
        }
    }
}
