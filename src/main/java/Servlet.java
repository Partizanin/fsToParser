import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created with Intellij IDEA.
 * Project name: fsToParser.
 * Date: 22.08.2016.
 * Time: 11:01.
 * To change this template use File|Setting|Editor|File and Code Templates.
 */
@WebServlet(name = "/Servlet", urlPatterns = "/Servlet")
public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/plain; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();


        JSONObject catchObject = new JSONObject();
        JSONObject sendObject = new JSONObject();
        String requestValue = "";


        try {
            catchObject = new JSONObject(request.getParameter("jsonData"));
            requestValue = catchObject.getString("operationCall");

            sendObject = setNewRateToJsonObject(requestValue);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        writer.println(sendObject);
        writer.flush();
    }

    private JSONObject setNewRateToJsonObject(String requestValue) {
        FilmPageParser filmPageParser = new FilmPageParser();
        JSONObject jsonObject = new JSONObject();
        ArrayList<Film> films = filmPageParser.getFilms(18);
        for (int i = 0; i < films.size(); i++) {
            jsonObject.accumulate(String.valueOf(i), new JSONObject(films.get(i)));
        }
        return jsonObject;
    }


}
