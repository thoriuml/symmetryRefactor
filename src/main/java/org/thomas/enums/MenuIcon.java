package org.thomas.enums;

//Icons with their id and bitmap definition
public enum MenuIcon {
    DOWN_ARROW((byte) 0, new byte[]{0b00100, 0b00100, 0b00100, 0b00100,
            0b00100, 0b11111, 0b01110, 0b00100}),
    UP_ARROW((byte) 1, new byte[]{0b00100, 0b01110, 0b11111, 0b00100,
            0b00100, 0b00100, 0b00100, 0b00100});

    public final byte id;
    public final byte[] definition;

    MenuIcon(byte id, byte[] definition) {
        this.id = id;
        this.definition = definition;
    }
}
