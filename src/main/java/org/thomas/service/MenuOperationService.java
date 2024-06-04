package org.thomas.service;

import com.google.common.collect.ImmutableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.thomas.enums.MenuButton;
import org.thomas.utils.Utils;

import java.util.Comparator;
import java.util.List;

public class MenuOperationService {
    private static final Logger logger = LogManager.getLogger(MenuOperationService.class);
    private final MenuViewService menuViewService;
    private final List<MenuButton> menuButtons;
    private final int pinInputSource;
    private final int maxPages;

    //This ser
    public MenuOperationService(MenuViewService menuViewService, List<MenuButton> menuButtons, int pinInputSource, int maxPages) {
        this.menuViewService = menuViewService;
        this.menuButtons = menuButtons.stream() // Sorted by voltageMaxExclusive to ensure it is evaluated properly in order
                .sorted(Comparator.comparing(m -> m.voltageMaxExclusive)).collect(ImmutableList.toImmutableList());
        this.pinInputSource = pinInputSource;
        this.maxPages = maxPages;
    }

    // This function is called whenever a button press is evaluated.
    // The LCD shield works by observing voltage drop
    // Evaluates the voltage for all available buttons in asc order of the voltage value else return MenuButton.IDLE as default
    private MenuButton evaluateButton(int voltage) {
        for (MenuButton button : menuButtons) {
            if (voltage < button.voltageMaxExclusive) {
                logger.info("Button voltage {} in range for button {}", voltage, button);
                return button;
            }
        }
        return MenuButton.IDLE;
    }

    // This function checks for a button press and loops until a non-idle button press is detected
    // PAGE_UP moves the current menu position up and PAGE_DOWN moves the current menu position down
    // Returns the result of the operation (the new currentPage)
    // Commentary: changed the function name from MainMenuOperate to make it more in line with the naming convention of verb first
    public int operateMainMenu(int currentPage) {
        MenuButton button;
        do { // Commentary: using do while loop instead, so we dont have to manage an additional flag
            button = evaluateButton(Utils.analogRead(pinInputSource)); // Read and evaluate the voltage drop from pinInputSource
            switch (button) {
                case IDLE: // When button returns as idle there is no action taken
                    break; // Restart the while loop to wait for another button press
                case UP_ARROW:
                    currentPage = Utils.constrain(currentPage - 1, maxPages);
                    menuViewService.drawMainMenu(currentPage); // Refresh main menu
                    break;
                case DOWN_ARROW:
                    currentPage = Utils.constrain(currentPage + 1, maxPages);
                    menuViewService.drawMainMenu(currentPage);
                    break;
            }
        } while (button == MenuButton.IDLE);
        logger.info("Menu operation result: {}", currentPage);
        return currentPage;
    }
}
