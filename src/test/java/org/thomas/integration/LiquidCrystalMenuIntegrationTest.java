package org.thomas.integration;

import org.junit.jupiter.api.Test;
import org.thomas.LiquidCrystalMenu;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LiquidCrystalMenuIntegrationTest {

    @Test
    void testMenuIntegration() throws NoSuchFieldException, IllegalAccessException {
        // execute
        LiquidCrystalMenu.setup();
        LiquidCrystalMenu.loop();
        // verify
        Field field = LiquidCrystalMenu.class.getDeclaredField("currentPage");
        field.setAccessible(true);
        assertEquals(0, field.get("currentPage"));
    }

}
