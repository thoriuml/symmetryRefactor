package org.thomas;


import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.thomas.driver.LiquidCrystal;
import org.thomas.enums.MenuButton;
import org.thomas.enums.MenuIcon;
import org.thomas.enums.MenuItem;
import org.thomas.service.MenuOperationService;
import org.thomas.service.MenuViewService;
import org.thomas.utils.PropertiesLoader;
import org.thomas.utils.Utils;

import java.util.List;
import java.util.Properties;

/* As discussed with James over emails, included commentary on the rationale behind some of the changes */

public class LiquidCrystalMenu {
    private static final Logger logger = LogManager.getLogger(LiquidCrystalMenu.class);
    private static LiquidCrystal liquidCrystal;
    //Commentary: extracting a bunch of magic numbers to constants to make it easier to read
    private static MenuOperationService menuOperationService;
    private static MenuViewService menuViewService;
    private static final int VIEW_COLS = 16;
    private static final int VIEW_ROWS = 2;
    private static final int ITEMS_PER_PAGE = 2;
    private static final int PIN_INPUT_SOURCE = 0; //pin to read button input from
    private static final ImmutablePair<Integer, Integer> UP_ARROW_POS = new ImmutablePair<>(15, 0); //up and down arrows position
    private static final ImmutablePair<Integer, Integer> DOWN_ARROW_POS = new ImmutablePair<>(15, 1);
    private static final ImmutablePair<Integer, Integer> MENU_TOP_POS = new ImmutablePair<>(1, 0); //starting print position for menu items

    /* Commentary: not ideal to have this as static and global because it is not actually static,
     can avoid this by putting the loop() methods and variables into a separate class but we need to maintain loop's signature*/
    private static int currentPage = 0;

    //Commentary: having immutable list instead of using MenuItems.values() so future additional MenuItems and MenuIcons won't change existing behaviour
    private static final List<MenuItem> MENU_ITEMS = ImmutableList.of(
            MenuItem.START_CAPTURE, MenuItem.START_SHOWCASE, MenuItem.PRESETS, MenuItem.SET_TRIGGER, MenuItem.SETTINGS, MenuItem.ABOUT);
    private static final List<MenuButton> MENU_BUTTONS = ImmutableList.of(MenuButton.UP_ARROW, MenuButton.DOWN_ARROW);

    public static void main(String[] args) { // Must not change
        setup();
        while (true) {
            loop();
        }
    }

    static void setup() { // Commentary: ideally this should be private, but opening this to bypass the limitation of the program's requirement so we can test this class
        /* Commentary: extracting the parameters for liquidCrystal into properties so that future changes to
        the init doesn't have to change the code */
        logger.info("Setting up liquidCrystal");
        Properties properties = PropertiesLoader.loadProperties();
        liquidCrystal = new LiquidCrystal(
                PropertiesLoader.getIntValue(properties, ("liquidCrystal.init.pinNumRS")),
                PropertiesLoader.getIntValue(properties, ("liquidCrystal.init.pinNumEnable")),
                PropertiesLoader.getIntValue(properties, ("liquidCrystal.init.pinNumD4")),
                PropertiesLoader.getIntValue(properties, ("liquidCrystal.init.pinNumD5")),
                PropertiesLoader.getIntValue(properties, ("liquidCrystal.init.pinNumD6")),
                PropertiesLoader.getIntValue(properties, ("liquidCrystal.init.pinNumD7")));

        // Commentary: extracted logic for drawing to the screen and menu operations to their own classes

        // Providing the parameters and resources to draw the menu
        int maxPages = Utils.resolveMaxPages(MENU_ITEMS.size(), ITEMS_PER_PAGE);
        menuViewService = new MenuViewService(liquidCrystal, ITEMS_PER_PAGE, MENU_ITEMS,
                MENU_TOP_POS, UP_ARROW_POS, DOWN_ARROW_POS, maxPages, MenuIcon.UP_ARROW, MenuIcon.DOWN_ARROW);

        menuOperationService = new MenuOperationService(menuViewService, MENU_BUTTONS, PIN_INPUT_SOURCE, maxPages);
        initMenu();
    }

    private static void initMenu() { // Init screen
        logger.info("Initializing screen");
        liquidCrystal.begin(VIEW_COLS, VIEW_ROWS);
        liquidCrystal.createChar(MenuIcon.UP_ARROW.id, MenuIcon.UP_ARROW.definition);
        liquidCrystal.createChar(MenuIcon.DOWN_ARROW.id, MenuIcon.DOWN_ARROW.definition);
    }

    //Must keep method signature
    static void loop() { // Commentary: ideally this should be private, but opening this to bypass the limitation of the program's requirement so we can test this class
        menuViewService.drawMainMenu(currentPage);
        currentPage = menuOperationService.operateMainMenu(currentPage); //update currentPage with the result of button operation
    }
}