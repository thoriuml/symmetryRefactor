package org.thomas.enums;

public enum MenuButton {
    IDLE(Integer.MIN_VALUE), //placeholder value for idle state
    PAGE_UP(50),
    PAGE_DOWN(195);

    public final int voltageMaxExclusive; // used to evaluate button pressed

    MenuButton(int voltageMaxExclusive) {
        this.voltageMaxExclusive = voltageMaxExclusive;
    }
}
