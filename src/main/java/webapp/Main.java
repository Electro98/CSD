package webapp;

import webapp.FIOS.InputHandler;
import webapp.FIOS.TextMenu.ConsumerMenuItem;
import webapp.FIOS.TextMenu.FunctionalMenu;
import webapp.FIOS.TextMenu.SimpleMenuItem;
import webapp.entity.Stationery;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import webapp.config.SpringConfig;
import webapp.spring.StationeryDAO;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private enum FieldNames {
        NAME("name"),
        TYPE("type"),
        PRICE("price"),
        NUM_IN_BOX("number in box");

        private final String textRepresentation;

        FieldNames(String textRepresentation){
            this.textRepresentation = textRepresentation;
        }

        @Override public String toString() {
            return textRepresentation;
        }
    };

    private static final Scanner in = new Scanner(System.in).useDelimiter("\\p{javaWhitespace}+\\n*");
    private static final InputHandler userIn = new InputHandler(in);

    private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    private final StationeryDAO stationeryDAO = context.getBean("stationeryDAO", StationeryDAO.class);

    public static void main(String[] args) {
        Main program = new Main();
        program.setupDB();
        FunctionalMenu mainMenu = new FunctionalMenu("Choice your action:", program);
        mainMenu.add(new ConsumerMenuItem<>("Add element", Main::addStationery));
        mainMenu.add(new ConsumerMenuItem<Main>("Print all elements", Main::printStationers));
        mainMenu.add(new ConsumerMenuItem<>("Edit element", Main::editStationery));
        mainMenu.add(new ConsumerMenuItem<>("Remove element", Main::removeStationery));
        mainMenu.add(new ConsumerMenuItem<>("Search elements by name", Main::searchStationersByName));
        mainMenu.add(new SimpleMenuItem("Exit ~"));
        System.out.println(" ~ Welcome back ~ ");
        mainMenu.exec(in);
        System.out.println(" ~ bye ~ ");
        program.context.close();
    }

    private void setupDB() {
        stationeryDAO.checkTableCreated();
        stationeryDAO.insertExampleData();
    }

    private void addStationery() {
        Stationery stationery = new Stationery(
                userIn.getString("Enter stationery name: "),
                userIn.getString("Enter stationery type: "),
                userIn.getDouble("Enter its price: ", 0),
                userIn.getInt("How many things in box?: ", 0));
        if (stationeryDAO.insert(stationery) == 1)
            System.out.println(" ~ success ~ ");
        else
            System.out.println(" ~ something went wrong!!!! ~");
    }

    private void printStationers() {
        List<Stationery> stationers = stationeryDAO.findAll();
        printStationers(stationers);
    }

    private void printStationers(List<Stationery> stationers) {
        printStationers(stationers, "All stationers list:");
    }

    private void printStationers(List<Stationery> stationers, String title) {
        System.out.println(title);
        for (Stationery stationery: stationers)
            System.out.printf(" - %s%n", stationery);
        if (stationers.isEmpty())
            System.out.println(" ~ list is empty ~ ");
    }

    public void editStationery() {
        List<Stationery> stationers = stationeryDAO.findAll();
        if (stationers.isEmpty()) {
            System.out.println(" ~ nothing to remove ~ ");
            return;
        }
        printStationers(stationers);
        int chosen_id = userIn.getInt("Which one to edit?: ", 0);
        Optional<Stationery> editableStationery = stationers.stream().filter(stationery -> stationery.getId() == chosen_id).findFirst();
        if (editableStationery.isEmpty()) {
            System.out.println(" ~ chosen id that doesn't exist ~ ");
            return;
        }
        Stationery stationery = editableStationery.get();
        FieldNames fieldToEdit = userIn.getEnum("Which field you want to edit?", FieldNames.class);
        switch (fieldToEdit) {
            case NAME -> stationery.setName(userIn.getString("Enter new name: "));
            case TYPE -> stationery.setType(userIn.getString("Enter new type: "));
            case PRICE -> stationery.setPrice(userIn.getDouble("Enter new price: ", 0));
            case NUM_IN_BOX -> stationery.setNum_in_box(userIn.getInt("Enter new number in box: ", 0));
            default -> System.out.println(" ~ something went really wrong ~ ");
        }
        if (stationeryDAO.update(stationery) == 1)
            System.out.println(" ~ item updated ~ ");
        else
            System.out.println(" ~ something went wrong! ~");
    }

    private void removeStationery() {
        List<Stationery> stationers = stationeryDAO.findAll();
        if (stationers.isEmpty()) {
            System.out.println(" ~ nothing to remove ~ ");
            return;
        }
        printStationers(stationers);
        int result = stationeryDAO.delete(userIn.getInt("Which to delete?: ", 0));
        if (result == 1)
            System.out.println(" ~ Well done ~ ");
        else
            System.out.println(" ~ nothing deleted ~ ");
    }

    private void searchStationersByName() {
        System.out.println(" ~ help: use this symbol '%' to match any string ~ ");
        System.out.println(" ~ example: 'ca%' matches 'calamity' ~ ");
        List<Stationery> stationers = stationeryDAO.findByName(userIn.getString("Enter name for search: "));
        printStationers(stationers, "Found stationers:");
    }
}
