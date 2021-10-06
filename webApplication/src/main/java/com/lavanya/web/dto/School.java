package com.lavanya.web.dto;

/**
 * enum associated to cChildrenDto Object.
 * School has all attributes required to qualify a children school status.
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

