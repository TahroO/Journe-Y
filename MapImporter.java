import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapImporter {
    private final String DATA_DIR = "/resources/data/rooms/";
    private Map<String, Room> rooms;

    public Map<String, Room> getRooms() {
        try {
            List<Map> data = getData(DATA_DIR);
            rooms = data.stream().map(MapImporter::createRoom)
                    .collect(Collectors.toMap(Room::getId, Function.identity(), (prev, next) -> next, HashMap::new));
            data.stream().forEach(this::putItems);
            data.stream().forEach(this::setExits);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rooms;
    }

    private void putItems(Map data) {
        List<Map<String, String>> items = (List<Map<String, String>>) data.get("items");
        if (items == null) {
            return;
        }

        Room room = rooms.get(data.get("id"));
        for (Map<String, String> itemData : items) {
            Item item = new Item(
                    itemData.get("id"),
                    itemData.get("name"),
                    itemData.get("description"));
            room.putItem(itemData.get("id"), item);
        }
    }

    private void setExits(Map data) {
        Map<String, String> exits = (Map<String, String>) data.get("exits");
        Room room = rooms.get(data.get("id"));
        exits.forEach((direction, nextRoom) -> {
            room.setExit(direction, rooms.get(nextRoom));
        });
    }

    private static Room createRoom(Map data) {
        return new Room(
                (String) data.get("id"),
                (String) data.get("name"),
                (String) data.get("description"),
                (String) data.get("image"),
                (String) data.get("audio"));
    }

    private List<Map> getData(String dataDir) throws IOException {
        Yaml yaml = new Yaml();
        return Util.readFolder(dataDir)
                .filter(Files::isRegularFile)
                .map(path -> {
                    try {
                        return yaml.loadAs(Files.newInputStream(path), Map.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }
}
