package ted.wgu482.model;

public class Dog extends Animal {
    private String special;

    public Dog(Integer id, String breed, Integer lifespan, String behavior, Double price, Boolean vaccinated, String special) {
        super(id, breed, lifespan, behavior, price, vaccinated);
        this.special = special;
    }
    public String getSpecialAbility() {
        return special;
    }

    public void setSpecialAbility(String special) {
        this.special = special;
    }
}
