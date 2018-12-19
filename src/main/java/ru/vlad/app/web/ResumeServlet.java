package ru.vlad.app.web;

import ru.vlad.app.Config;
import ru.vlad.app.model.*;
import ru.vlad.app.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends javax.servlet.http.HttpServlet {

    private static final Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if ("create".equals(action)) {
            response.sendRedirect("resume?uuid=new&action=edit");
            return;
        }
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        if (fullName == null || fullName.trim().length() == 0) {
            response.sendRedirect("resume");
            return;
        }
        Resume r = ("new".equals(uuid)) ? new Resume("") : storage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(new Contact(type, value));
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addSection(type, convertStringToSection(type, value.trim()));
            } else {
                r.getSections().remove(type);
            }
        }
        if ("new".equals(uuid)) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = ("new".equals(uuid)) ? new Resume("new","") : storage.get(uuid);
                if (action.equals("edit")) {
                    for (SectionType type : SectionType.values()) {
                        AbstractSection section = r.getSection(type);
                        if (section != null) {
                            request.setAttribute(type.name(), convertSectionToString(type, section));
                        }
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    private AbstractSection convertStringToSection(SectionType sectionType, String value) {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                return new SimpleTextSection(value);
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListOfTextSection(value.split("\\s*\n+\\s*"));
        }
        return null;
    }

    private String convertSectionToString(SectionType sectionType, AbstractSection section) {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                return ((SimpleTextSection) section).getDescription();
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return String.join("\n", ((ListOfTextSection) section).getItems());
        }
        return null;
    }
}
