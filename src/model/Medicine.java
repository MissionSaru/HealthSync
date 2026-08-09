package model;

public class Medicine {

    private int medicineId;
    private String name;
    private String description;

    public Medicine(int medicineId, String name, String description) {

        this.medicineId = medicineId;
        this.name = name;
        this.description = description;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name;
    }
}