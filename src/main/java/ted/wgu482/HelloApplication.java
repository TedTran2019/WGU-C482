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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Dog dog1 = new Dog(1, "Pitbull", 12, "Cute", 999.99, true, "Biting");
        Dog dog2 = new Dog(2, "Poodle", 15, "Mean", 699.99, true, "Flying");
        Dog dog3 = new Dog(3, "Pug", 10, "Hyper", 399.99, true, "Lasers");
        DataProvider.addAnimal(dog1);
        DataProvider.addAnimal(dog2);
        DataProvider.addAnimal(dog3);
        Outsourced part = new Outsourced(1, "Part 1", 1.99, 1, 1, 1, "Company 1");
        Product product = new Product(1, "Product 1", 1.99, 1, 1, 1);
        product.addAssociatedPart(part);
        System.out.println(product.getAllAssociatedParts());
        product.deleteAssociatedPart(part);
        System.out.println(product.getAllAssociatedParts());
        launch();
    }
}