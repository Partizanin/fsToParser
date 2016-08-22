import java.util.ArrayList;

/**
 * Created with Intellij IDEA.
 * Project name: fsToParser.
 * Date: 23.08.2016.
 * Time: 0:17.
 * To change this template use File|Setting|Editor|File and Code Templates.
 */
public class Films {
    private WriteReadJsonObjects writeReadJsonObjects = new WriteReadJsonObjects();

    public ArrayList<Film> getFilms(int filmCount) {
        ArrayList<Film> films = new ArrayList<>();

        ArrayList<Film> filmsFromFile = writeReadJsonObjects.getFilmsFromFile();
/*todo: реалізувати метод getFilms який дістає фільми з файлу,якшо їх там мало то дістає з інтернету до тих пір поки їх не стане достатньо! З інтернету дістає так,якщо в файлі фільмів 18 то починає діставати зразу з 2-гої сторінки і тд, після чого фільми порівнюються якщо фільми співпадають з тими що в файлі то фільми в файлі оновлюються але не рахуються як нові*/

        return films;
    }
}
