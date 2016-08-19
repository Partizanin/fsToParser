import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

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

        String[] filmsPagesUrls = filmPageParser.getFilmsPagesUrls();
        System.out.println("size: " + filmsPagesUrls.length);
        for (String filmsPagesUrl : filmsPagesUrls) {
            System.out.println(filmsPagesUrl);
        }
        /*/video/films/iw8zfCK3epL1mKa9b6ou08-poltora-shpiona.html*/

    }

    private static String[] getNames(String url) {
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

    private static int getViews(String url) {
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

    private String[] getFilmsPagesUrls() {
        ArrayList<String> strings1 = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(filmsUrls).get();
            Elements table = doc.select("div[class=\"b-section-list \"]").select("table");
            Elements td = table.select("td");
                /*table.get(0).select("td").get(0).select("a[class=\"b-poster-tile__link\"]").get(0).select("[class=\"b-poster-tile__link\"]").first().attr("href")*/
            for (Element element : td) {
                StringBuilder href = new StringBuilder();
                href.append(element.select("a[class=\"b-poster-tile__link\"]").get(0).select("[class=\"b-poster-tile__link\"]").first().attr("href"));
                href.insert(0, "http://fs.to");
                strings1.add(href.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return strings1.toArray(new String[strings1.size()]);
    }
}
