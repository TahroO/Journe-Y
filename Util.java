import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
}
