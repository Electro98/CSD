package FIOS.TextMenu;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringJoiner;

import static java.util.Arrays.asList;

public class SimpleMenu implements MenuItem {
    public static final int MENU_EXIT = -1;
    private String title;
    private boolean lastItemIsExit = false;
    final ArrayList<MenuItem> menuItems = new ArrayList<>();

    public SimpleMenu(String title) {
        this.title = title;
    }

    public void add(MenuItem item) {
        menuItems.add(item);
    }

    public void add(MenuItem... items) {
        menuItems.addAll(asList(items));
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLastItemIsExit(boolean lastItemIsExit) {
        this.lastItemIsExit = lastItemIsExit;
    }

    public int takeChoose(Scanner in) {
        return takeChoose(System.out, in);
    }

    public int takeChoose(PrintStream out, Scanner in) {
        if (menuItems.isEmpty())
            return MENU_EXIT;
        int result;
        out.println(this);
        out.print("Your choose: ");
        while (!in.hasNextInt() || (result = in.nextInt()) < 0 || result >= menuItems.size()) {
            in.nextLine();
            out.print("Your choose: ");
        }
        return !lastItemIsExit || result != menuItems.size() - 1? result: MENU_EXIT;
    }

    @Override
    public String toText() {
        if (!lastItemIsExit)
            throw new UnsupportedOperationException("this menu for using as submenu should have exit item.");
        return title;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add(title);
        final int[] i = {0};
        menuItems.forEach(elem -> joiner.add(" %d - %s".formatted(i[0]++, elem.toText())));
        return joiner.toString();
    }
}
