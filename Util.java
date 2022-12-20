import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

final public class Util {
    public static String join(List<String> strings) {
        StringBuilder builder = new StringBuilder();
        int inputCount = strings.size();
        for (int i = 0; i < inputCount; i++) {
            builder.append(strings.get(i));
            if (i == inputCount - 2) {
                builder.append(" and ");
            } else if (i < inputCount - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    public static Stream<Path> readFolder(String path) {
        Stream<Path> walk;
        try {
            URI uri = Util.class.getResource(path).toURI();
            Path myPath;
            if (uri.getScheme().equals("jar")) {
                FileSystem fileSystem = null;
                fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
                myPath = fileSystem.getPath(path);
            } else {
                myPath = Paths.get(uri);
            }
            walk = Files.walk(myPath, 1);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return walk;
    }

}
