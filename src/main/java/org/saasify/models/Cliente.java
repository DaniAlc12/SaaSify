package org.saasify.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class Cliente implements Serializable {
    private final UUID id;
    private final String dni;
    private String nombre;
    private String email;
    private String password;
    private BigDecimal saldoBancario;
    private static final long serialVerisonUID = 1L;

    public Cliente(UUID id, String dni, String nombre, String email, String password, BigDecimal saldoBancario) {
        if (id == null) {
            throw new IllegalArgumentException("id may not be null");
        }
        if (dni == null) {
            throw new IllegalArgumentException("dni may not be null");
        }
        if (nombre == null ||  nombre.isBlank()) {
            throw new IllegalArgumentException("nombre may not be null or empty");
        }
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("email may not be null or empty");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("password may not be null or empty");
        }
        if (saldoBancario == null ||  saldoBancario.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("saldoBancario may not be null or negative");
        }

        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.saldoBancario = saldoBancario;
    }

    public UUID getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getSaldoBancario() {
        return saldoBancario;
    }

    public void setSaldoBancario(BigDecimal saldoBancario) {
        this.saldoBancario = saldoBancario;
    }
}
