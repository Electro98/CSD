package webapp.FIOS.TextMenu;

public class SimpleMenuItem implements MenuItem {
    private final String itemText;

    public SimpleMenuItem(String itemText) {
        this.itemText = itemText;
    }

    @Override
    public String toText() {
        return itemText;
    }
}
