package org.thomas.enums;

public enum MenuButton {
    IDLE(Integer.MAX_VALUE), //placeholder value for idle state
    UP_ARROW(50),
    DOWN_ARROW(195);

    public final int voltageMaxExclusive; // used to evaluate button pressed

    MenuButton(int voltageMaxExclusive) {
        this.voltageMaxExclusive = voltageMaxExclusive;
    }
}
