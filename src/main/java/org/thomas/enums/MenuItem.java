package org.thomas.enums;

public enum MenuItem { //all possible menu items with their display text
    START_CAPTURE("START CAPTURE"),
    START_SHOWCASE("START SHOWCASE"),
    PRESETS("PRESETS"),
    SET_TRIGGER("SET TRIGGER"),
    SETTINGS("SETTINGS"),
    ABOUT("ABOUT");

    public final String text;

    MenuItem(String text) {
        this.text = text;
    }

}
