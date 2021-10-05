package com.lavanya.api.enums;

/**
 * enum required for Children class and required as one of its attributes
 * Different type of School possible.
 * @author lavanya
 */
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
