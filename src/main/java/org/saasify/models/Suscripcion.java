package org.saasify.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Suscripcion implements Serializable {
    private final Cliente cliente;
    private final PlanSuscripcion plan;
    private EstadoSuscripcion estado;
    private final LocalDate fechaInicio;
    private LocalDate fechaProximoCobro;
    private static final long serialVerisonUID = 1L;

    public Suscripcion(Cliente cliente, PlanSuscripcion plan) {
        if (cliente == null ||  plan == null) {
            throw new IllegalArgumentException();
        }
        this.cliente = cliente;
        this.plan = plan;
        this.estado = EstadoSuscripcion.ACTIVA;
        this.fechaInicio = LocalDate.now();
        this.fechaProximoCobro = this.fechaInicio.plusMonths(plan.getPeriodoMeses());
    }

    public Suscripcion(Cliente cliente, PlanSuscripcion plan, LocalDate fechaInicio, EstadoSuscripcion estado, LocalDate fechaProximoCobro) {
        if(cliente == null){
            throw new IllegalArgumentException("Cliente no puede ser nulo");
        }
        if(plan == null){
            throw new IllegalArgumentException("Plan no puede ser nulo");
        }
        if(estado == null){
            throw new IllegalArgumentException("estado no puede ser nulo");
        }
        if(fechaInicio == null){
            throw new IllegalArgumentException("Fecha Inicio no puede ser nulo");
        }
        if(fechaProximoCobro == null){
            throw new IllegalArgumentException("Fecha Proximo Cobro no puede ser nulo");
        }

        this.cliente = cliente;
        this.plan = plan;
        this.fechaInicio = fechaInicio;
        this.estado = estado;
        this.fechaProximoCobro = fechaProximoCobro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public PlanSuscripcion getPlan() {
        return plan;
    }

    public EstadoSuscripcion getEstado() {
        return estado;
    }

    public void setEstado(EstadoSuscripcion estado) {
        this.estado = estado;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaProximoCobro() {
        return fechaProximoCobro;
    }

    public void setFechaProximoCobro(LocalDate fechaProximoCobro) {
        this.fechaProximoCobro = fechaProximoCobro;
    }
}
