package org.account.cli;


import org.account.cli.command.Command;
import org.account.cli.command.ExitCommand;
import org.account.cli.ui.Menu;
import org.account.cli.ui.MenuItem;


public abstract class App {
    private Menu menu;

    public App() {
        // Constructor logic, if any
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void init() {
        menu = new Menu();
        createMenu(menu);
        menu.add(new MenuItem("종료", new ExitCommand()));
    }

    // Abstract method for subclasses to implement
    public abstract void createMenu(Menu menu);

    public void run() {
        init();
        while (true) {
            try {
                menu.printMenu();
                Command command = menu.getSelect();
                command.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}