import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReadFilesNames {
//    private String filesFolder = "C:\\Users\\Nosara\\Documents\\test-autool-files";

    private String filesFolder ="C:\\Users\\SMunoz2\\Desktop\\doleBillsInvs\\facturas";
    public List<String> getFilesNames() {
        File[] files = new File(filesFolder).listFiles((dir, name) -> name.endsWith(".pdf"));

        assert files != null;
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());

        return Arrays.stream(files).map(f-> f.getName().replace(".pdf", "")).collect(Collectors.toList());
    }
}
