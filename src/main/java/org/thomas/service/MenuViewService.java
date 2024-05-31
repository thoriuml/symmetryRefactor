package org.thomas.service;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.thomas.driver.LiquidCrystal;
import org.thomas.enums.MenuIcon;
import org.thomas.enums.MenuItem;

import java.util.List;


//Commentary: extracted out the logic that writes to screen to its own class
//This service provided menu items and arrow icons will print them to the corresponding positions on the screen
public class MenuViewService {

    private final LiquidCrystal liquidCrystal;
    private final int itemsPerPage;
    private final List<MenuItem> menuItems;
    private final ImmutablePair<Integer, Integer> menuTopPos;
    private final ImmutablePair<Integer, Integer> upArrowPos;
    private final ImmutablePair<Integer, Integer> downArrowPos;
    private final int maxMenuPages;
    private final MenuIcon upArrow;
    private final MenuIcon downArrow;

    public MenuViewService(LiquidCrystal liquidCrystal, int itemsPerPage, List<MenuItem> menuItems, ImmutablePair<Integer, Integer> menuTopPos,
                           ImmutablePair<Integer, Integer> upArrowPos, ImmutablePair<Integer, Integer> downArrowPos, int maxMenuPages,
                           MenuIcon upArrow, MenuIcon downArrow) {
        this.liquidCrystal = liquidCrystal;
        this.itemsPerPage = itemsPerPage;
        this.menuItems = menuItems;
        this.menuTopPos = menuTopPos;
        this.upArrowPos = upArrowPos;
        this.downArrowPos = downArrowPos;
        this.maxMenuPages = maxMenuPages;
        this.upArrow = upArrow;
        this.downArrow = downArrow;
    }

    // This function will generate itemsPerPage number of menu items on the screen,
    // and up and down arrows based on the currentPage number
    public void drawMainMenu(int currentPage) {
        liquidCrystal.clear();
        printMenuText(currentPage);
        printArrows(currentPage);
    }

    // Commentary: extracted the logic hardcoded to print menu item's text and made it more abstract
    // Prints menu item text for the current page, one in each row starting from position of menuTopPos
    private void printMenuText(int currentPage) {
        for (int offset = 0; offset < itemsPerPage; offset++) {
            printTextAtPosition(liquidCrystal,
                    menuItems.get(currentPage + offset).text,
                    menuTopPos.left, menuTopPos.right + offset);
        }
    }

    private void printArrows(int currentPage) {
        if (currentPage == 0) { // Prints down arrow only
            writeIconAtPosition(liquidCrystal, downArrow, downArrowPos.left, downArrowPos.right);
        } else if (currentPage > 0 && currentPage < maxMenuPages) { // Prints both arrows
            writeIconAtPosition(liquidCrystal, upArrow, upArrowPos.left, upArrowPos.right);
            writeIconAtPosition(liquidCrystal, downArrow, downArrowPos.left, downArrowPos.right);
        } else if (currentPage == maxMenuPages) { // Prints up arrow only
            writeIconAtPosition(liquidCrystal, downArrow, downArrowPos.left, downArrowPos.right);
        }
    }

    // Commentary - extracting repeating code to methods and make it easier to read and use
    private void printTextAtPosition(LiquidCrystal liquidCrystal, String output, int col, int row) {
        liquidCrystal.setCursor(col, row);
        liquidCrystal.print(output);
    }

    private void writeIconAtPosition(LiquidCrystal liquidCrystal, MenuIcon menuIcon, int col, int row) {
        liquidCrystal.setCursor(col, row);
        liquidCrystal.write(menuIcon.id);
    }
}
