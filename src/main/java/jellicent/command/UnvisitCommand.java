package jellicent.command;

import jellicent.entry.EntryLists;
import jellicent.entry.Place;
import jellicent.entry.PlaceList;
import jellicent.storage.Storage;
import jellicent.ui.Ui;

/**
 * Represents a command to remove (unvisit) a place from the {@link PlaceList}.
 * <p>
 * An {@code UnvisitCommand} removes the place at a specified 1-based index
 * from the {@link PlaceList}. Execution of this command returns a message
 * describing the result.
 * </p>
 */
public class UnvisitCommand extends Command {
    private final int index;

    /**
     * Creates a new {@code UnvisitCommand}.
     *
     * @param index 1-based index of the place to remove.
     */
    public UnvisitCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(EntryLists entryLists, Ui ui, Storage storage) throws IndexOutOfBoundsException {
        assert entryLists != null : "EntryLists should not be null";
        assert ui != null : "Ui should not be null";
        assert storage != null : "Storage should not be null";

        PlaceList places = entryLists.places();
        assert places != null : "PlaceList should not be null";

        // Remove the place at the given 1-based index
        Place removedPlace = places.remove(this.index);
        return ui.removePlace(places, removedPlace);
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.UNVISIT;
    }
}
