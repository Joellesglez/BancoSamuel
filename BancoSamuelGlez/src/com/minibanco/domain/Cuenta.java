package com.minibanco.domain;

import com.minibanco.exceptions.LimiteDiarioExcedidoException;
import com.minibanco.exceptions.SaldoInsuficienteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {
    private final String titular;
    private double saldo;
    private double limiteDiario;
    private final List<Movimiento> movimientos;

    public Cuenta(String titular) {
        this.titular = titular;
        this.saldo = 0.0;
        this.limiteDiario = 200.0;
        this.movimientos = new ArrayList<>();
    }

    public String getTitular() { return titular; }
    public double getSaldo() { return saldo; }
    public double getLimiteDiario() { return limiteDiario; }
    public void setLimiteDiario(double limiteDiario) { this.limiteDiario = limiteDiario; }
    public List<Movimiento> getMovimientos() { return movimientos; }

    public void ingresar(double importe) {
        saldo += importe;
        movimientos.add(new Movimiento(LocalDateTime.now(), TipoMovimiento.INGRESO, importe, saldo));
    }

    public void retirar(double importe) throws SaldoInsuficienteException, LimiteDiarioExcedidoException {
        if (importe <= 0) throw new IllegalArgumentException("El importe debe ser mayor que 0");
        if (importe > saldo) throw new SaldoInsuficienteException("Saldo insuficiente. Saldo actual: " + String.format("%.2f €", saldo));
        double retiradoHoy = getRetiradoEnFecha(LocalDate.now());
        if (retiradoHoy + importe > limiteDiario)
            throw new LimiteDiarioExcedidoException(String.format("Límite diario excedido. Retirado hoy: %.2f €, límite: %.2f €", retiradoHoy, limiteDiario));
        saldo -= importe;
        movimientos.add(new Movimiento(LocalDateTime.now(), TipoMovimiento.RETIRO, importe, saldo));
    }

    private double getRetiradoEnFecha(LocalDate fecha) {
        return movimientos.stream()
                .filter(m -> m.getTipo() == TipoMovimiento.RETIRO && m.getFecha().toLocalDate().equals(fecha))
                .mapToDouble(Movimiento::getImporte)
                .sum();
    }
}
