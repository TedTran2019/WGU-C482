package ted.wgu482.model;

public class Outsourced extends Part{
    private String companyName;

    /**
     * @param id Part ID
     * @param name Part Name
     * @param price Part Price
     * @param stock Part Inventory Level
     * @param min Part Minimum Inventory Level
     * @param max Part Maximum Inventory Level
     * @param companyName Company Name
     * Constructor for Outsourced
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
