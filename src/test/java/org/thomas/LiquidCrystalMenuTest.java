package org.thomas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.thomas.service.MenuOperationService;
import org.thomas.service.MenuViewService;
import org.thomas.utils.PropertiesLoader;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

class LiquidCrystalMenuTest {
    @Mock
    private MenuViewService menuViewService;
    @Mock
    private MenuOperationService menuOperationService;
    @Mock
    private MockedStatic<PropertiesLoader> propertiesLoader;

    @BeforeEach
    public void init() throws NoSuchFieldException, IllegalAccessException {
        menuViewService = Mockito.mock(MenuViewService.class);
        menuOperationService = Mockito.mock(MenuOperationService.class);
        propertiesLoader = Mockito.mockStatic(PropertiesLoader.class);
        // Not very ideal, very hacky way to test the main class
        Field field = LiquidCrystalMenu.class.getDeclaredField("menuViewService"); // Set required fields for the class that would've been initialized by the main method
        field.setAccessible(true);
        field.set(null, menuViewService);
        field = LiquidCrystalMenu.class.getDeclaredField("menuOperationService");
        field.setAccessible(true);
        field.set(null, menuOperationService);
    }

    @Test
    void testLoop() {
        //execute
        LiquidCrystalMenu.loop();
        //verify
        Mockito.verify(menuViewService, times(1)).drawMainMenu(0);
        Mockito.verify(menuOperationService, times(1)).operateMainMenu(0);
    }

    @Test
    void testSetup() throws NoSuchFieldException, IllegalAccessException {
        //setup
        propertiesLoader.when(() -> PropertiesLoader.getIntValue(any(), any())).thenReturn(0);
        //execute
        LiquidCrystalMenu.setup();
        //verify
        Field field = LiquidCrystalMenu.class.getDeclaredField("menuViewService");
        field.setAccessible(true);
        assertNotNull(field.get(MenuViewService.class));

        field = LiquidCrystalMenu.class.getDeclaredField("menuOperationService");
        field.setAccessible(true);
        assertNotNull(field.get(MenuOperationService.class));

    }
}