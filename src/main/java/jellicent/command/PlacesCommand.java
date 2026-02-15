package jellicent.command;

import jellicent.entry.EntryLists;
import jellicent.entry.PlaceList;
import jellicent.storage.Storage;
import jellicent.ui.Ui;

/**
 * Represents a command to list all places.
 * <p>
 * A {@code ListCommand} interacts with a {@link PlaceList} to
 * retrieve all places and uses {@link jellicent.ui.Ui} to display them to the user.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     ListCommand cmd = new ListCommand();
 *     String result = cmd.execute(entrylists, ui, storage);
 * </pre>
 * </p>
 */
public class PlacesCommand extends Command {
    @Override
    public String execute(EntryLists entryLists, Ui ui, Storage storage) {
        assert entryLists != null: "EntryLists should not be null";
        assert ui != null : "Ui should not be null";
        assert storage != null : "Storage should not be null";

        PlaceList places = entryLists.places();
        assert places != null : "PlaceList should not be null";

        return ui.listPlaces(places);
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.LIST;
    }
}
