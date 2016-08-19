import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with Intellij IDEA.
 * Project name: fsToParser.
 * Date: 19.08.2016.
 * Time: 11:13.
 * To change this template use File|Setting|Editor|File and Code Templates.
 */
public class FilmPageParser {

    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<String>();

        strings.add("http://fs.to/video/films/iw8zfCK3epL1mKa9b6ou08-poltora-shpiona.html");
        strings.add("http://fs.to/video/films/i4pFzwAydBJE5OUgRXePKcU-jestestvennyj-otbor.html");
        strings.add("http://fs.to/video/films/iLevrF8PN42exYP5C8btXq-svadebnyj-ugar.html");
        strings.add("http://fs.to/video/films/i4ELvFcdK3t01zOMt172tJC-sosedi-na-trope-vojny-2.html");
        strings.add("http://fs.to/video/films/i4FEzYJz7wRW0sv5B4RLOSc-cherepashki-nindzya-2.html");
        strings.add("http://fs.to/video/films/iLaPSRuwDB1tlKMLB6ARnW-illuziya-obmana-2.html");


        for (String string : strings) {
            System.out.println(Arrays.toString(getNames(string)) + " " + getViews(string));
        }

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
}
