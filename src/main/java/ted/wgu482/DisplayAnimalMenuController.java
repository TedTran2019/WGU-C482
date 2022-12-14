package ted.wgu482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ted.wgu482.model.Animal;
import ted.wgu482.model.DataProvider;
import ted.wgu482.model.Dog;

import java.io.IOException;

public class DisplayAnimalMenuController {
    @FXML
    private TableColumn<Animal, String> breedCol;

    @FXML
    private TableColumn<Animal, Integer> idCol;

    @FXML
    private TableColumn<Animal, Integer> lifespanCol;

    @FXML
    private TableColumn<Animal, Double> priceCol;

    @FXML
    private TableView<Animal> tableView;

    Stage stage;
    Parent root;

    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void onActionSeeDetails(ActionEvent event) throws IOException {
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AnimalDetailsMenu.fxml"));
        loader.load();
        AnimalDetailsMenuController animalDetailsMenuController = loader.getController();
        animalDetailsMenuController.setAnimal(tableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
//        root = FXMLLoader.load(getClass().getResource("AnimalDetailsMenu.fxml"));
        root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public Animal searchAnimal(int id) {
        for (Animal animal : DataProvider.getAllAnimals()) {
            if (animal.getId() == id) {
                System.out.println("Animal found");
                return animal;
            }
        }
        System.out.println("Animal not found");
        return null;
    }

    @FXML
    public boolean update(int id, Animal animal) {
        int index = 0;
        for (Animal a : DataProvider.getAllAnimals()) {
            if (a.getId() == id) {
//                a.setBreed(animal.getBreed());
//                a.setLifespan(animal.getLifespan());
//                a.setPrice(animal.getPrice());
//                a.setBehavior(animal.getBehavior());
//                a.setVaccinated(animal.isVaccinated());
//                a.setId(animal.getId());
//                Would need to set special too for Dog
                DataProvider.getAllAnimals().set(index, animal);
                return true;
            }
            index++;
        }
        return false;
    }

    @FXML
    public boolean delete(int id) {
        int index = 0;
        for (Animal animal: DataProvider.getAllAnimals()) {
            if (animal.getId() == id) {
//                DataProvider.getAllAnimals().remove(animal);
                DataProvider.getAllAnimals().remove(index);
                return true;
            }
            index++;
        }
        return false;
    }

    @FXML
    public ObservableList<Animal> filter(String breed) {
        ObservableList<Animal> filteredList = FXCollections.observableArrayList();
        for (Animal animal : DataProvider.getAllAnimals()) {
            if (animal.getBreed().equals(breed)) {
                filteredList.add(animal);
            }
        }
        return filteredList;
    }

    @FXML
    public void initialize() {
//        tableView.setItems(DataProvider.getAllAnimals());
        tableView.setItems(filter("Pug"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        breedCol.setCellValueFactory(new PropertyValueFactory<>("breed"));
        lifespanCol.setCellValueFactory(new PropertyValueFactory<>("lifespan"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

//        delete(1);
//        update(2, new Dog(2, "Pitbull", 12, "Aggressive", 99.99, true, "Guard Dog"));
        tableView.getSelectionModel().select(searchAnimal(5));
    }
}
