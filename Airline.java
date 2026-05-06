
import java.util.ArrayList;
import java.util.List;

public class Airline {
    private String name;
    private List<Aircraft> aircrafts;

    public Airline(String name) {
        this.name = name;
        this.aircrafts = new ArrayList<>();
    }

    public void addAircraft(Aircraft a) {
        aircrafts.add(a);
    }

    public List<Aircraft> getAircrafts() {
        return aircrafts;
    }

    public String getName() {
        return name;
    }
}

