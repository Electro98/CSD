package webapp;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import webapp.FIOS.InputHandler;
import webapp.FIOS.TextMenu.ConsumerMenuItem;
import webapp.FIOS.TextMenu.FunctionalMenu;
import webapp.FIOS.TextMenu.SimpleMenuItem;
import webapp.entity.Stationery;

import java.util.*;

public class Client {
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
    private static final String STATIONERY_API_URL = "http://localhost:8081/csd/api/v0.1/stationers/";
    private static final String STATIONERY_API_URL_ID = "http://localhost:8081/csd/api/v0.1/stationers/{id}";

    private static final Scanner in = new Scanner(System.in).useDelimiter("\\p{javaWhitespace}+\\n*");
    private static final InputHandler userIn = new InputHandler(in);
    private final RestTemplate restTemplate;

    public Client(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin"));
        Client program = new Client(restTemplate);
        FunctionalMenu mainMenu = new FunctionalMenu("Choice your action:", program);
        mainMenu.add(new ConsumerMenuItem<Client>("Get all stationers", Client::printStationers));
        mainMenu.add(new ConsumerMenuItem<Client>("Get one object", Client::getStationery));
        mainMenu.add(new ConsumerMenuItem<Client>("Add stationery", Client::addStationery));
        mainMenu.add(new ConsumerMenuItem<>("Edit stationery", Client::editStationery));
        mainMenu.add(new ConsumerMenuItem<>("Remove stationery", Client::removeStationery));
        mainMenu.add(new SimpleMenuItem("Exit ~"));
        System.out.println(" ~ Welcome back ~ ");
        mainMenu.exec(in);
        System.out.println(" ~ bye ~ ");
    }

    // ***
    // UI methods
    // ***
    private void printStationers() {
        List<Stationery> stationers = getAllStationers();
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

    private void getStationery() {
        int id = userIn.getInt("Enter which id to get: ", 0);
        Optional<Stationery> stationery = getStationery(id);
        if (stationery.isEmpty()) {
            System.out.println(" ~ chosen id that doesn't exist ~ ");
        } else {
            System.out.println(" ~ success ~ ");
            System.out.println("Object in DB: " + stationery.get());
        }
    }

    private void addStationery() {
        Stationery stationery = new Stationery(
                userIn.getString("Enter stationery name: "),
                userIn.getString("Enter stationery type: "),
                userIn.getDouble("Enter its price: ", 0),
                userIn.getInt("How many things in box?: ", 0));
        Optional<Stationery> result = addStationery(stationery);
        if (result.isPresent()) {
            System.out.println(" ~ success ~ ");
            System.out.println("Object in DB: " + result.get());
        } else
            System.out.println(" ~ something went wrong!!!! ~");
    }

    public void editStationery() {
        int id = userIn.getInt("Enter which id to edit: ", 0);
        Optional<Stationery> editableStationery = getStationery(id);
        if (editableStationery.isEmpty()) {
            System.out.println(" ~ chosen id that doesn't exist ~ ");
            return;
        }
        Stationery stationery = editableStationery.get();
        FieldNames fieldToEdit = userIn.getEnum("Which field you want to edit?", Client.FieldNames.class);
        switch (fieldToEdit) {
            case NAME -> stationery.setName(userIn.getString("Enter new name: "));
            case TYPE -> stationery.setType(userIn.getString("Enter new type: "));
            case PRICE -> stationery.setPrice(userIn.getDouble("Enter new price: ", 0));
            case NUM_IN_BOX -> stationery.setNum_in_box(userIn.getInt("Enter new number in box: ", 0));
            default -> System.out.println(" ~ something went really wrong ~ ");
        }
        if (updateStationery(stationery))
            System.out.println(" ~ item updated ~ ");
        else
            System.out.println(" ~ something went wrong! ~");
    }

    private void removeStationery() {
        int id = userIn.getInt("Which id to delete?: ", 0);
        if (deleteStationery(id))
            System.out.println(" ~ Well done ~ ");
        else
            System.out.println(" ~ nothing deleted ~ ");
    }

    // ***
    // Wrappers for API calls
    // ***
    private List<Stationery> getAllStationers() {
        ResponseEntity<List<Stationery>> response = restTemplate.exchange(
                STATIONERY_API_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        return response.getBody();
    }

    private Optional<Stationery> getStationery(int id) {
        try {
            ResponseEntity<Stationery> response = restTemplate.getForEntity(
                    STATIONERY_API_URL_ID, Stationery.class, id);
            return Optional.ofNullable(response.getBody());
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }
    }

    private Optional<Stationery> addStationery(Stationery stationery) {
        try {
            ResponseEntity<Stationery> response = restTemplate.postForEntity(
                    STATIONERY_API_URL, stationery, Stationery.class);
            return Optional.ofNullable(response.getBody());
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }
    }

    private boolean deleteStationery(int id) {
        try {
            restTemplate.delete(STATIONERY_API_URL_ID, id);
            return true;
        } catch (HttpClientErrorException e) {
            return false;
        }
    }

    private boolean updateStationery(Stationery stationery) {
        try {
            restTemplate.put(STATIONERY_API_URL, stationery);
            return true;
        } catch (HttpClientErrorException e) {
            return false;
        }
    }
}
