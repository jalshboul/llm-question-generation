package model;

public class Name {
    private String first;
    private String middle;
    private String last;

    public Name(String first, String middle, String last) {
        this.first = first;
        this.middle = middle;
        this.last = last;
    }

    public String getFullName() {
        if (middle != null && !middle.isEmpty()) {
            return first + " " + middle + " " + last;
        } else {
            return first + " " + last;
        }
    }
}