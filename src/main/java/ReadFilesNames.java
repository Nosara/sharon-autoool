import java.io.File;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ReadFilesNames {
    private String filesFolder = "C:\\Users\\Nosara\\Documents\\test-autool-files";
    public Set<String> getFilesNames() {
        File[] files = new File(filesFolder).listFiles((dir, name) -> name.endsWith(".pdf"));

        assert files != null;

        return Arrays.stream(files).map(f-> f.getName().replace(".pdf", "")).collect(Collectors.toSet());
    }
}
