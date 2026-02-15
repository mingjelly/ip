package jellicent.entry;

public class Place  {
    private final String name;

    public Place(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
