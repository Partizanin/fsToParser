import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * Created with Intellij IDEA.
 * Project name: fsToParser.
 * Date: 19.08.2016.
 * Time: 13:13.
 * To change this template use File|Setting|Editor|File and Code Templates.
 */
public class Film {
    private String name;
    private String url;
    private String originalName;
    private int views;
    private String[] genres;
    private LocalDate updateDate;

    public Film() {
    }

    public Film(JSONObject object) {
        initializeFromJsonObject(object);
    }

    public Film(String name, String originalName, int views, String[] genres) {
        this.name = name;
        this.originalName = originalName;
        this.views = views;
        this.genres = genres;
    }

    public Film(String name, String originalName, int views, String filmUrl, String[] genres, LocalDate updateDate) {
        this.name = name;
        this.originalName = originalName;
        this.views = views;
        this.genres = genres;
        this.updateDate = updateDate;
        this.url = filmUrl;
    }

    private void initializeFromJsonObject(JSONObject object) {
        JSONArray jsonArray = (JSONArray) object.get("genres");
        String[] genres = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            genres[i] = (String) jsonArray.get(i);
        }
        this.genres = genres;
        this.name = (String) object.get("name");
        this.originalName = (String) object.get("originalName");
        this.views = (int) object.get("views");
        this.url = (String) object.get("url");
        this.url = (String) object.get("url");
        this.updateDate = LocalDate.parse((CharSequence) object.get("updateDate"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;

        if (!getName().equals(film.getName())) return false;
        if (!getUrl().equals(film.getUrl())) return false;
        if (!getOriginalName().equals(film.getOriginalName())) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(getGenres(), film.getGenres());

    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getUrl().hashCode();
        result = 31 * result + getOriginalName().hashCode();
        result = 31 * result + Arrays.hashCode(getGenres());
        return result;
    }

    @Override
    public String toString() {
        return "Film{" +
                "name='" + name + '\'' +
                ", originalName='" + originalName + '\'' +
                ", views=" + views +
                ", genres=" + Arrays.toString(genres) +
                ", updateDate=" + updateDate +
                '}';
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
