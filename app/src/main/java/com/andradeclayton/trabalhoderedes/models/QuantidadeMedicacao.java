package com.andradeclayton.trabalhoderedes.models;

public class QuantidadeMedicacao {

    private String faixa_etaria;
    private String tipo_de_remedio;
    private Double peso_paciente;

    public QuantidadeMedicacao(String faixa_etaria) {
        this.faixa_etaria = faixa_etaria;
    }

    public QuantidadeMedicacao(String faixa_etaria, String tipo_de_remedio, Double peso_paciente) {
        this.faixa_etaria = faixa_etaria;
        this.tipo_de_remedio = tipo_de_remedio;
        this.peso_paciente = peso_paciente;
    }

    public String getFaixa_etaria() {
        return faixa_etaria;
    }

    public void setFaixa_etaria(String faixa_etaria) {
        this.faixa_etaria = faixa_etaria;
    }

    public String getTipo_de_remedio() {
        return tipo_de_remedio;
    }

    public void setTipo_de_remedio(String tipo_de_remedio) {
        this.tipo_de_remedio = tipo_de_remedio;
    }

    public Double getPeso_paciente() {
        return peso_paciente;
    }

    public void setPeso_paciente(Double peso_paciente) {
        this.peso_paciente = peso_paciente;
    }


}
