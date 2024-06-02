package org.thomas.service;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.thomas.driver.LiquidCrystal;
import org.thomas.enums.MenuIcon;
import org.thomas.enums.MenuItem;

import java.util.List;

import static org.mockito.Mockito.times;

class MenuViewServiceTest {
    private MenuViewService menuViewService;
    @Mock
    private LiquidCrystal liquidCrystal;
    private static final int ITEMS_PER_PAGE = 2;
    private static final List<MenuItem> MENU_ITEMS = ImmutableList.of(
            MenuItem.START_CAPTURE, MenuItem.START_SHOWCASE, MenuItem.PRESETS, MenuItem.SET_TRIGGER, MenuItem.SETTINGS, MenuItem.ABOUT);
    private static final ImmutablePair<Integer, Integer> UP_ARROW_POS = new ImmutablePair<>(15, 0); //up and down arrows position
    private static final ImmutablePair<Integer, Integer> DOWN_ARROW_POS = new ImmutablePair<>(15, 1);
    private static final ImmutablePair<Integer, Integer> MENU_TOP_POS = new ImmutablePair<>(1, 0); //starting print position for menu items
    private static final int MAX_PAGES = 4;

    @BeforeEach
    public void init() {
        liquidCrystal = Mockito.mock(LiquidCrystal.class);
        menuViewService = new MenuViewService(liquidCrystal, ITEMS_PER_PAGE, MENU_ITEMS, MENU_TOP_POS,
                UP_ARROW_POS, DOWN_ARROW_POS, MAX_PAGES, MenuIcon.UP_ARROW, MenuIcon.DOWN_ARROW);
    }

    @Test
    public void test_draw_menu_first_page() {
        //setup
        //execute
        menuViewService.drawMainMenu(0);
        //verify
        Mockito.verify(liquidCrystal, times(1)).setCursor(1, 0); //first item
        Mockito.verify(liquidCrystal, times(1)).print(MENU_ITEMS.get(0).text);

        Mockito.verify(liquidCrystal, times(1)).setCursor(1, 1); //second item
        Mockito.verify(liquidCrystal, times(1)).print(MENU_ITEMS.get(1).text);

        Mockito.verify(liquidCrystal, times(1)).setCursor(15, 1); //down arrow
        Mockito.verify(liquidCrystal, times(1)).write(MenuIcon.DOWN_ARROW.id);
    }

    @Test
    public void test_draw_menu_second_page() {
        //setup
        //execute
        menuViewService.drawMainMenu(1);
        //verify
        Mockito.verify(liquidCrystal, times(1)).setCursor(1, 0); //first item
        Mockito.verify(liquidCrystal, times(1)).print(MENU_ITEMS.get(1).text);

        Mockito.verify(liquidCrystal, times(1)).setCursor(1, 1); //second item
        Mockito.verify(liquidCrystal, times(1)).print(MENU_ITEMS.get(2).text);

        Mockito.verify(liquidCrystal, times(1)).setCursor(15, 0); //up arrow
        Mockito.verify(liquidCrystal, times(1)).write(MenuIcon.UP_ARROW.id);

        Mockito.verify(liquidCrystal, times(1)).setCursor(15, 1); //down arrow
        Mockito.verify(liquidCrystal, times(1)).write(MenuIcon.DOWN_ARROW.id);
    }

    @Test
    public void test_draw_menu_last_page() {
        //setup
        //execute
        menuViewService.drawMainMenu(4);
        //verify
        Mockito.verify(liquidCrystal, times(1)).setCursor(1, 0); //first item
        Mockito.verify(liquidCrystal, times(1)).print(MENU_ITEMS.get(4).text);

        Mockito.verify(liquidCrystal, times(1)).setCursor(1, 1); //second item
        Mockito.verify(liquidCrystal, times(1)).print(MENU_ITEMS.get(5).text);

        Mockito.verify(liquidCrystal, times(1)).setCursor(15, 0); //up arrow
        Mockito.verify(liquidCrystal, times(1)).write(MenuIcon.UP_ARROW.id);
    }

}