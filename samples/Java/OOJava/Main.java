import core.Database;
import model.Department;

public class Main {
    public static void main(String[] args) {
        Database db = Database.getInstance();

        for (String id : db.departments.keySet()) {
            Department dep = db.departments.get(id);
            System.out.println(id + " => " + dep.getName() + " with " + dep.getEmployees().size() + " employees");
        }
    }
}