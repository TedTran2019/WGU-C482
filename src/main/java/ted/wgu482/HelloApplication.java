package ted.wgu482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ted.wgu482.model.*;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
//        Dog dog1 = new Dog(1, "Pitbull", 12, "Cute", 999.99, true, "Biting");
//        Dog dog2 = new Dog(2, "Poodle", 15, "Mean", 699.99, true, "Flying");
//        Dog dog3 = new Dog(3, "Pug", 10, "Hyper", 399.99, true, "Lasers");
//        DataProvider.addAnimal(dog1);
//        DataProvider.addAnimal(dog2);
//        DataProvider.addAnimal(dog3);
        Outsourced part1 = new Outsourced(1, "Part 1", 1.99, 1, 1, 1, "Company 1");
        InHouse part2 = new InHouse(2, "Part 2", 2.99, 2, 2, 2, 2);
        Outsourced part3 = new Outsourced(3, "Part 3", 3.99, 3, 3, 3, "Company 3");
        Product product1 = new Product(1, "Product 1", 1.99, 1, 1, 1);
        Product product2 = new Product(2, "Product 2", 2.99, 2, 2, 2);
        Product product3 = new Product(3, "Product 3", 3.99, 3, 3, 3);
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        launch();
    }
}