package com.monoton.horizont.bomb.painter.logic;

public class BodyDescription {

    private String id;
    private String label;

    public BodyDescription(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
