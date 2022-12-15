package examples;

import examples.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataProvider {
    private static ObservableList<Animal> allAnimals = FXCollections.observableArrayList();

    public static void addAnimal(Animal animal) {
        allAnimals.add(animal);
    }

    public static ObservableList<Animal> getAllAnimals() {
        return allAnimals;
    }
}
