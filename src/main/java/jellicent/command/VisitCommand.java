package jellicent.command;

import jellicent.entry.EntryLists;
import jellicent.entry.Place;
import jellicent.entry.PlaceList;
import jellicent.storage.Storage;
import jellicent.ui.Ui;

public class VisitCommand extends Command {
    private final String name;

    public VisitCommand(String name) {
        this.name = name;
    }

    @Override
    public String execute(EntryLists entryLists, Ui ui, Storage storage) throws IndexOutOfBoundsException {
        assert entryLists != null : "EntryLists should not be null";
        assert ui != null : "Ui should not be null";
        assert storage != null : "Storage should not be null";

        PlaceList places = entryLists.places();
        assert places != null : "PlaceList should not be null";

        Place newPlace = new Place(this.name);
        places.add(newPlace);
        return ui.addPlace(newPlace);
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.VISIT;
    }
}
