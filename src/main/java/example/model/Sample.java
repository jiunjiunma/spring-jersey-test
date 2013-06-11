package example.model;

/**
 * Date: 6/10/13
 * Time: 5:27 PM
 */
public class Sample {
    private int id;
    private String name;

    public Sample(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
