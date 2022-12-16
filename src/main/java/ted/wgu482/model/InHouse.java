package ted.wgu482.model;

public class InHouse extends Part {
    private int machineId;

    /**
     * Constructor for InHouse
     * @param id Part ID
     * @param name Part Name
     * @param price Part Price
     * @param stock Part Inventory Level
     * @param min Part Minimum Inventory Level
     * @param max Part Maximum Inventory Level
     * @param machineId Machine ID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @return the machineId
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * @param machineId the machineId to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
