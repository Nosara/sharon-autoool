import java.io.*;
import java.util.List;

public class WriteResposeFile {

    public void writeResponses(List<String> responses) throws IOException {
        StringBuilder response = new StringBuilder();

        responses.forEach(r ->{
            response.append(r).append(System.lineSeparator());
        } );

        writeFile(response.toString());
    }

    private void writeFile(String response) throws IOException {
        try {
            FileWriter myWriter = new FileWriter("response.txt");
            myWriter.write(response);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
