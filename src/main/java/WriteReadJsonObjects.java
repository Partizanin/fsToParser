import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
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
        FilmPageParser filmPageParser = new FilmPageParser();
        writeReadJsonObjects.writeObjectToFile(filmPageParser.getFilms(10));
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

    private void writeObjectToFile(ArrayList<Film> newFilms) {
        JSONObject jsonObject = readObjectFromFile();

        ArrayList<Film> filmsFromFile = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("films");

        for (Object film : jsonArray) {
            filmsFromFile.add(new Film((JSONObject) film));
        }
        ArrayList<Film> films = compareFilms(filmsFromFile, newFilms);

        for (Film film : films) {
            jsonObject = jsonObject.append("films", new JSONObject(film));
        }

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

    private ArrayList<Film> compareFilms(ArrayList<Film> filmsFromFile, ArrayList<Film> newFilms) {
        ArrayList<Film> result = new ArrayList<>();
        boolean compare = false;
        for (Film newFilm : newFilms) {
            for (Film film : filmsFromFile) {
                if (!film.getName().equals(newFilm.getName())
                        || !film.getOriginalName().equals(newFilm.getOriginalName())
                        || !film.getUrl().equals(newFilm.getUrl())) {
                    compare = true;
                    film.setUpdateDate(LocalDate.now());
                    result.add(film);
                }
            }
            if (!compare) {
                newFilm.setUpdateDate(LocalDate.now());
                result.add(newFilm);
                compare = false;
            }
        }


        return result;
    }

}


