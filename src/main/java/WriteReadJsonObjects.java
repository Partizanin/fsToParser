import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with Intellij IDEA.
 * Project name: fsToParser.
 * Date: 21.08.2016.
 * Time: 11:52.
 * To change this template use File|Setting|Editor|File and Code Templates.
 */
public class WriteReadJsonObjects {

    public static void main(String[] args) {
        WriteReadJsonObjects writeReadJsonObjects = new WriteReadJsonObjects();
        writeReadJsonObjects.writeObjectToFile(new Film("FilmName", "OriginalName", 1225, new String[2]));
        System.out.println(writeReadJsonObjects.getFilmsFromFile());
    }

    private void writeObjectToFile(Film film) {
        JSONObject jsonObject = readObjectFromFile();
        int filmCount = jsonObject.getInt("filmCount");
        filmCount++;
        jsonObject = jsonObject.put("filmCount", filmCount);
        jsonObject = jsonObject.append("films", new JSONObject(film));

        try {
            FileWriter file = new FileWriter("src/main/resources/data.json");
            file.write(jsonObject.toString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject readObjectFromFile() {
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/data.json"));
            line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONObject(sb.toString());
    }

    public ArrayList<Film> getFilmsFromFile() {
        ArrayList<Film> films = new ArrayList<>();

        JSONObject jsonObject = readObjectFromFile();
        JSONArray jsonArray = jsonObject.getJSONArray("films");

        for (Object film : jsonArray) {
            films.add(new Film((JSONObject) film));
        }

        return films;
    }

}


