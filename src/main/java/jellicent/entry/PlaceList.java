package jellicent.entry;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a list of places in the Jellicent application.
 * <p>
 * A {@code PlaceList} manages a collection of {@link Place} objects, providing
 * methods to add, remove, retrieve places by index, and
 * iterate over the list. Place indices used in public methods are 1-based, while
 * internal storage uses 0-based indexing.
 * </p>
 */
public class PlaceList implements Iterable<Place> {
    private final ArrayList<Place> places;
    private static final int MIN_NUM = 1;
    private static final int MIN_IDX = 0;
    private static final int ONE_BASED_TO_ZERO_BASED_OFFSET = 1;

    public PlaceList() {
        this.places = new ArrayList<>();
    }

    /**
     * Adds a place into the places list.
     *
     * @param place Place received from user input.
     * @return Place for further processing if required.
     */
    public Place add(Place place) {
        assert place != null : "Place should not be null";

        this.places.add(place);
        return place;
    }

    /**
     * Removes a place from the list by its 1-based index.
     *
     * @param num 1-based index of the place to remove.
     * @return The removed {@link Place} object.
     * @throws IllegalArgumentException if num < 1 or num > size of the list.
     */
    public Place remove(int num) {
        if (num < MIN_NUM) {
            throw new IllegalArgumentException("Number cannot be less than 0");
        }
        if (num > this.places.size()) {
            throw new IllegalArgumentException("Number is greater than current placelist size");
        }
        return this.places.remove(num - ONE_BASED_TO_ZERO_BASED_OFFSET);
    }

    /**
     * Returns the number of entries currently in the place list.
     *
     * @return Number of entries in the place list.
     */
    public int size() {
        return this.places.size();
    }

    /**
     * Retrieves a place from the list by its 0-based index.
     *
     * @param idx 0-based index of the place to retrieve.
     * @return The {@link Place} object at the given index.
     * @throws IllegalArgumentException if idx < 0 or idx >= size of the list.
     */
    public Place get(int idx) {
        if (idx < MIN_IDX) {
            throw new IllegalArgumentException("Number cannot be less than 0");
        }
        if (idx >= this.places.size()) {
            throw new IllegalArgumentException("Number is greater than current tasklist size");
        }
        return this.places.get(idx);
    }

    @Override
    public Iterator<Place> iterator() {
        return this.places.iterator();
    }
}