package FIOS.TextMenu;

import java.util.function.Consumer;

public class ConsumerMenuItem<T> extends SimpleMenuItem {
    private final Consumer<T> func;

    public ConsumerMenuItem(String itemText, Consumer<T> func) {
        super(itemText);
        this.func = func;
    }

    @Override
    public void call(Object obj) {
        func.accept((T) obj);
    }
}
