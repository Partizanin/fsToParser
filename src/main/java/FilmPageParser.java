import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created with Intellij IDEA.
 * Project name: fsToParser.
 * Date: 19.08.2016.
 * Time: 11:13.
 * To change this template use File|Setting|Editor|File and Code Templates.
 */
public class FilmPageParser {

    private String filmsUrls = "http://fs.to/video/films/";

    public static void main(String[] args) {

        FilmPageParser filmPageParser = new FilmPageParser();
        ArrayList<Film> films = filmPageParser.getFilms(18);
        for (Film film : films) {
            System.out.println(film.getYear() + " " + Arrays.toString(film.getCountries()));
        }
    }


    public ArrayList<Film> getFilms(int filmsCount) {
        ArrayList<Film> films = new ArrayList<Film>();
        for (String filmUrl : getFilmsUrlsFromPages(filmsCount)) {
            String[] names = getNames(filmUrl);
            films.add(new Film(names[0], getYear(filmUrl), getCountries(filmUrl), names[1], getRates(filmUrl), filmUrl, getImgURl(filmUrl), getGenres(filmUrl), LocalDate.now()));
        }

        return films;
    }

    private String[] getCountries(String filmUrl) {
        String[] result = new String[0];
        try {
            Document doc = Jsoup.connect(filmUrl).get();

            Elements selectedElements = doc.select("span[class=\"tag-country-flag\"");

            result = new String[selectedElements.size()];

            for (int i = 0; i < selectedElements.size(); i++) {

                result[i] = selectedElements.get(i).parent().childNode(1).attr("text");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getYear(String filmUrl) {
        String result = "";
        try {
            Document doc = Jsoup.connect(filmUrl).get();
            result = doc.select("div[id=\"contentInner\"]").select("a[href*=/video/films/year/]").first().childNode(0).childNode(0).attr("text");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getImgURl(String url) {
        String result = "";
        try {
            Document doc = Jsoup.connect(url).get();
            result = doc.select("a[class=\"images-show\"").get(0).select("img").first().absUrl("src");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String[] getNames(String url) {
        String[] result = new String[2];
        try {
            Document doc = Jsoup.connect(url).get();
            result[0] = doc.select("span[itemprop=\"name\"").get(0).text();
            result[1] = doc.getElementsByAttribute("itemprop").select("[itemprop=\"alternativeHeadline\"").text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private int getRates(String url) {
        int result = 0;
        try {/*itemprop="aggregateRating"*/
            Document doc = Jsoup.connect(url).get();
            Elements elementsByClass = doc.select("div[class=\"b-tab-item__vote-value m-tab-item__vote-value_type_yes\"]");
            result = Integer.parseInt(elementsByClass.text());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private String[] getGenres(String url) {
        ArrayList<String> strings = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url).get();
            Elements select = doc.select("span[itemprop=\"genre\"");
            strings.addAll(select.stream().map(Element::text).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return strings.toArray(new String[0]);
    }

    private ArrayList<String> getFilmsUrlsFromPages(int filmCount) {
        ArrayList<String> strings = new ArrayList<>();

        int pageCount = getFilmsPageCount(filmCount);

        for (int i = 0; i < pageCount; i++) {
            String url = filmsUrls + "?page=" + (i + 1);
            strings.addAll(getFilmsPagesUrls(url));
        }
        int count = (strings.size() - filmCount);

        for (int i = 0; i < count; i++) {
            strings.remove(0);
        }

        return strings;
    }

    private ArrayList<String> getFilmsPagesUrls(String filmsUrls) {

        ArrayList<String> strings1 = new ArrayList<String>();
        try {
            Document doc = Jsoup.connect(filmsUrls).get();
            Elements table = doc.select("div[class=\"b-section-list \"]").select("table");
            Elements td = table.select("td");
            for (Element element : td) {
                StringBuilder href = new StringBuilder();
                href.append(element.select("a[class=\"b-poster-tile__link\"]").get(0).select("[class=\"b-poster-tile__link\"]").first().attr("href"));
                href.insert(0, "http://fs.to");
                strings1.add(href.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return strings1;
    }

    private int getFilmsPageCount(int filmsCount) {

        double v = filmsCount / 18d;
        long part = (long) v;
        int result = (int) part;
        double fPart = v - part;


        if (fPart > 0) {
            result++;
        }


        return result;
    }

}
