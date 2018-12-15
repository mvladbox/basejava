package ru.vlad.app.web;

import ru.vlad.app.Config;
import ru.vlad.app.model.*;
import ru.vlad.app.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ResumeServlet extends javax.servlet.http.HttpServlet {

    private static final Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        PrintWriter pw = response.getWriter();

        String uuid = request.getParameter("uuid");
        if (uuid != null) {
            Resume r = storage.get(uuid);
            pw.write("<h1>" + r.getFullName() + "</h1>");

            pw.write("<h2>Контакты</h2>");
            pw.write("<ul>");
            for (Contact contact : r.getContacts().values()) {
                pw.write("<li>" + contact + "</li>");
            }
            pw.write("</ul>");

            for (Map.Entry<SectionType, AbstractSection> entry : r.getSections().entrySet()) {
                SectionType st = entry.getKey();
                AbstractSection section = entry.getValue();
                pw.write("<h3>" + st.getTitle() + "</h3>");
                switch (st) {
                    case OBJECTIVE:
                    case PERSONAL:
                        pw.write("<p>" + ((SimpleTextSection) section).getDescription() + "</p>");
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        pw.write("<ul>");
                        for (String item : ((ListOfTextSection) section).getItems()) {
                            pw.write("<li>" + item + "</li>");
                        }
                        pw.write("</ul>");
                        break;
                }
            }
        } else {
            pw.write("<table><th><td>Соискатели</td></th>");
            for (Resume r : storage.getAllSorted()) {
                pw.write("<tr><td><a href='?uuid=" + r.getUuid() + "'>" + r.getFullName() + "</a> </td></tr>");
            }
            pw.write("</table>");
        }
    }
}
