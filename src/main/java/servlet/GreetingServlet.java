package servlet;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GreetingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Started GreetingServlet.doGet");
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println("Nice to meet you, " + name);
        writer.flush();
        System.out.println("Finished GreetingServlet.doGet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Started GreetingServlet.doPost");
        // Обработаем полученные данные JSON
        String data = req.getParameter("name");
        System.out.println("&&& data = " + data);
        JSONObject jsonObject;
        String email = "";
        try {
            JSONParser parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(data);
            System.out.println("jsonObject = " + jsonObject);
            email = (String) jsonObject.get("email");
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        System.out.println("*** email = " + email);

        // Подготовим ответ в JSON
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        jsonObject = new JSONObject();
        jsonObject.put("email", email);
        System.out.println("^^^ jsonObject = " + jsonObject);

        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        System.out.println("%% jsonObject.toJSONString() = " + jsonObject.toJSONString());
        writer.println(jsonObject.toJSONString());
        writer.flush();

        System.out.println("Finished GreetingServlet.doPost");
    }
}
