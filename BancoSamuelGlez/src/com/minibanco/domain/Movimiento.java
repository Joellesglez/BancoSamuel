package com.minibanco.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Movimiento {
    private final LocalDateTime fecha;
    private final TipoMovimiento tipo;
    private final double importe;
    private final double saldoResultante;
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Movimiento(LocalDateTime fecha, TipoMovimiento tipo, double importe, double saldoResultante) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.importe = importe;
        this.saldoResultante = saldoResultante;
    }

    public LocalDateTime getFecha() { return fecha; }
    public TipoMovimiento getTipo() { return tipo; }
    public double getImporte() { return importe; }
    public double getSaldoResultante() { return saldoResultante; }

    @Override
    public String toString() {
        return String.format("%s | %s | %.2f € | Saldo: %.2f €",
                fecha.format(FORMAT), tipo, importe, saldoResultante);
    }
}
