package org.saasify.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class PlanSuscripcion implements Serializable {

    private final int id;
    private final String nombre;
    private final BigDecimal precio;
    private final int periodoMeses;
    private static final long serialVerisonUID = 1L;

    public PlanSuscripcion(int id, String nombre, BigDecimal precio, int periodoMeses) {
        if (id <= 0 ) {
            throw new IllegalArgumentException("Id may not be negative");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre may not be null or empty");
        }
        if (precio == null || precio.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Precio may not be null or empty");
        }
        if (periodoMeses <= 0) {
            throw new IllegalArgumentException("Periodo may not be negative");
        }

        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.periodoMeses = periodoMeses;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public int getPeriodoMeses() {
        return periodoMeses;
    }

}
