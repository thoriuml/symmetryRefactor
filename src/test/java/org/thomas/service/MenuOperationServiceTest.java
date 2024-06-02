package org.thomas.service;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.thomas.enums.MenuButton;
import org.thomas.utils.Utils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;

class MenuOperationServiceTest {

    private MenuOperationService menuOperationService;
    @Mock
    private MenuViewService menuViewService;
    private static final List<MenuButton> MENU_BUTTONS = ImmutableList.of(MenuButton.UP_ARROW, MenuButton.DOWN_ARROW);
    private static final int PIN_INPUT_SOURCE = 0; //pin to read button input from
    private static final int MAX_PAGES = 5;
    @Mock
    private MockedStatic<Utils> utils;


    @BeforeEach
    void init() {
        menuViewService = Mockito.mock(MenuViewService.class);
        menuOperationService = new MenuOperationService(menuViewService, MENU_BUTTONS, PIN_INPUT_SOURCE, MAX_PAGES);
        utils = Mockito.mockStatic(Utils.class);
    }

    @Test
    void when_up_arrow_operateMainMenu_verify_calls() {
        //setup
        int upArrowVolt = MenuButton.UP_ARROW.voltageMaxExclusive - 1;
        utils.when(() -> Utils.analogRead(PIN_INPUT_SOURCE)).thenReturn(upArrowVolt);
        utils.when(() -> Utils.constrain(anyInt(), anyInt())).thenReturn(0);
        //execute
        int result = menuOperationService.operateMainMenu(0);
        //verify
        assertEquals(0, result);
        Mockito.verify(menuViewService, times(1)).drawMainMenu(0);
        utils.close();
    }

    @Test
    void when_idle_then_up_arrow_operateMainMenu_verify_calls() {
        //setup
        int upArrowVolt = MenuButton.UP_ARROW.voltageMaxExclusive - 1;
        int idleVolt = MenuButton.IDLE.voltageMaxExclusive;
        utils.when(() -> Utils.analogRead(PIN_INPUT_SOURCE)).thenReturn(idleVolt, upArrowVolt);
        utils.when(() -> Utils.constrain(anyInt(), anyInt())).thenReturn(0);
        //execute
        int result = menuOperationService.operateMainMenu(0);
        //verify
        assertEquals(0, result);
        utils.verify(() -> Utils.analogRead(PIN_INPUT_SOURCE), times(2)); // should call again after initial call evaluated as idle
        Mockito.verify(menuViewService, times(1)).drawMainMenu(0);
        utils.close();
    }
}