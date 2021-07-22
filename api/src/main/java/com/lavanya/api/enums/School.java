package com.lavanya.api.enums;

public enum School {

    N("Non Scolarisé"),M("En Maternelle"),P("En Primaire"),C("Collège");

    private String label;

    School(String label) {
        this.label  = label;
    }

    public String getLabel() {
        return label;
    }
}
