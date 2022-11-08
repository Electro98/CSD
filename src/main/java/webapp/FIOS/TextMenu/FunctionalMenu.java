package webapp.FIOS.TextMenu;

import java.util.Scanner;

public class FunctionalMenu extends SimpleMenu{
    private final Object argument;

    public FunctionalMenu(String title, Object argument) {
        super(title);
        this.argument = argument;
        super.setLastItemIsExit(true);
    }

    public void exec(Scanner in) {
        int choose;
        while ((choose = takeChoose(in)) != MENU_EXIT) {
            MenuItem menuItem = menuItems.get(choose);
            if (menuItem.getClass().equals(FunctionalMenu.class))
                menuItem.call(in);
            else
                menuItem.call(argument);
        }
    }

    @Override
    public void call(Object obj) {
        this.exec((Scanner) obj);
    }
}
