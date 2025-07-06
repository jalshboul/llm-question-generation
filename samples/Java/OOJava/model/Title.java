package model;

public class Title {
    private String id;
    private String name;
    private int baseSalary;

    public Title(String id, String name, int baseSalary) {
        this.id = id;
        this.name = name;
        this.baseSalary = baseSalary;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBaseSalary() {
        return baseSalary;
    }
}