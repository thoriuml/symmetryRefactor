package org.thomas.driver;
//  ----------------------------------------------------------------------
//  LCD driver

// All methods here are stubs, replacing real library functionality it's too
// annoying to set up a toolchain for.
//
public class LiquidCrystal {
    // Sets up the connection between the screen and the microcontroller
    public LiquidCrystal(int pinNumRS, int pinNumEnable, int pinNumD4, int pinNumD5,
                         int pinNumD6, int pinNumD7) {
    }

    // Clears the LCD screen and positions the cursor in the upper-left corner.
    public void clear() {
    }

    // Initializes the interface to the LCD screen, and specifies the dimensions
// (width and height) of the display.
// begin() needs to be called before any other LCD library commands.
    public void begin(int cols, int rows) {
    }

    // Position the LCD cursor; that is, set the location at which subsequent
// text written to the LCD will be displayed.
    public void setCursor(int col, int row) {
    }

    // Prints text to the LCD.
    public void print(String str) {
    }

    // Write a character to the LCD.
    public void write(byte num) {
    }

    // Create a custom character (glyph) for use on the LCD.
    public void createChar(byte num, byte[] data) {
    }
}


