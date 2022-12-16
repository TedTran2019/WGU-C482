package ted.wgu482.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * @param newPart the part to add to the inventory
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * @param newProduct the product to add to the inventory
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * @param partId the part ID to search for
     * @return the part with the given ID if found, or null if not found
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * @param productId the product ID to search for
     * @return the product with the given ID if found, or null if not found
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * @param partName the part name to search for
     * @return a list of parts with the given name if found, or an empty list if not found
     */
    public static ObservableList<Part> lookupPart(String partName) {
        partName = partName.toLowerCase();
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().toLowerCase().contains(partName)) {
                foundParts.add(part);
            }
        }
        return foundParts;
    }

    /**
     * @param productName the product name to search for
     * @return a list of products with the given name if found, or an empty list if not found
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        productName = productName.toLowerCase();
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(productName)) {
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }

    /**
     * @param index the index of the part to update
     * @param selectedPart the part to replace the existing part
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * @param index the index of the product to update
     * @param newProduct the product to replace the existing product
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * @param selectedPart the part to delete
     * @return true if the part was deleted, false if not
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * @param selectedProduct the product to delete
     * @return true if the product was deleted, false if not
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * @return the arraylist of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return the arraylist of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
