package ru.vlad.app.web;

import ru.vlad.app.Config;
import ru.vlad.app.model.*;
import ru.vlad.app.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static ru.vlad.app.model.Activity.DATE_FORMAT;

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
        if (isEmpty(fullName)) {
            response.sendRedirect("resume");
            return;
        }
        Resume r = ("new".equals(uuid)) ? new Resume("") : storage.get(uuid);
        try {
            r.setFullName(fullName);
            for (ContactType type : ContactType.values()) {
                String value = request.getParameter(type.name());
                if (isEmpty(value)) {
                    r.getContacts().remove(type);
                } else {
                    r.addContact(new Contact(type, value));
                }
            }
            for (SectionType type : SectionType.values()) {
                AbstractSection section = getSectionFromRequest(request, type);
                if (section != null) {
                    r.addSection(type, section);
                } else {
                    r.getSections().remove(type);
                }
            }
            if ("new".equals(uuid)) {
                storage.save(r);
            } else {
                storage.update(r);
            }
        } catch (RuntimeException e) {
            request.setAttribute("resume", r);
            request.setAttribute("error", "Ошибка в заполнении полей!");
            fillParamsFromResume(request, r);
            request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
            return;
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
                r = ("new".equals(uuid)) ? new Resume("new", "") : storage.get(uuid);
                if (action.equals("edit")) {
                    fillParamsFromResume(request, r);
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

    private void fillParamsFromResume(HttpServletRequest request, Resume r) {
        for (SectionType type : SectionType.values()) {
            AbstractSection section = r.getSection(type);
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    if (section == null) {
                        section = new SimpleTextSection();
                    }
                    request.setAttribute(type.name(), ((SimpleTextSection) section).getDescription());
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    if (section == null) {
                        section = new ListOfTextSection();
                    }
                    request.setAttribute(type.name(), String.join("\n", ((ListOfTextSection) section).getItems()));
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    if (section == null) {
                        section = new ActivitySection();
                    }
                    List<String> listOrganizationName = new LinkedList<>();
                    List<String> listOrganizationUrl = new LinkedList<>();
                    List<String> listStartDate = new LinkedList<>();
                    List<String> listEndDate = new LinkedList<>();
                    List<String> listTitle = new LinkedList<>();
                    List<String> listDesc = new LinkedList<>();
                    for (Activity a : ((ActivitySection) section).getItems()) {
                        listOrganizationName.add(a.getOrganization().getName());
                        listOrganizationUrl.add(a.getOrganization().getUrl());
                        listStartDate.add(a.getStartDate().format(DATE_FORMAT));
                        listEndDate.add((a.getEndDate() != null) ? a.getEndDate().format(DATE_FORMAT) : "");
                        listTitle.add(a.getTitle());
                        listDesc.add(a.getDescription());
                    }
                    listOrganizationName.add("");
                    listOrganizationUrl.add("");
                    listStartDate.add("");
                    listEndDate.add("");
                    listTitle.add("");
                    listDesc.add("");
                    request.setAttribute(type.name() + "_organizationName", listOrganizationName);
                    request.setAttribute(type.name() + "_organizationUrl", listOrganizationUrl);
                    request.setAttribute(type.name() + "_startDate", listStartDate);
                    request.setAttribute(type.name() + "_endDate", listEndDate);
                    request.setAttribute(type.name() + "_title", listTitle);
                    request.setAttribute(type.name() + "_desc", listDesc);
            }
        }
    }

    private AbstractSection getSectionFromRequest(HttpServletRequest request, SectionType type) {
        switch (type) {
            case OBJECTIVE:
            case PERSONAL:
                return !isEmpty(request.getParameter(type.name())) ? new SimpleTextSection(request.getParameter(type.name())) : null;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return !isEmpty(request.getParameter(type.name())) ? new ListOfTextSection(request.getParameter(type.name()).split("\\s*\n+\\s*")) : null;
            case EXPERIENCE:
            case EDUCATION:
                Organization org;
                Map<String, Organization> orgs = new HashMap<>();
                String[] listOrganizationName = request.getParameterValues(type.name() + "_organizationName");
                String[] listOrganizationUrl = request.getParameterValues(type.name() + "_organizationUrl");
                String[] listStartDate = request.getParameterValues(type.name() + "_startDate");
                String[] listEndDate = request.getParameterValues(type.name() + "_endDate");
                String[] listTitle = request.getParameterValues(type.name() + "_title");
                String[] listDesc = request.getParameterValues(type.name() + "_desc");
                List<Activity> items = new LinkedList<>();
                for (int i = 0; i < listTitle.length; i++) {
                    org = orgs.get(listOrganizationName[i]);
                    if (org == null) {
                        org = new Organization(listOrganizationName[i], listOrganizationUrl[i]);
                        orgs.put(listOrganizationName[i], org);
                    }
                    if (listStartDate[i].trim().length() + listOrganizationName[i].trim().length() + listTitle[i].trim().length() > 0) {
                        items.add(new Activity(
                                org,
                                YearMonth.parse(listStartDate[i], DATE_FORMAT),
                                (listEndDate[i].trim().length() > 0) ? YearMonth.parse(listEndDate[i], DATE_FORMAT) : null,
                                listTitle[i],
                                listDesc[i]));
                    }
                }
                return (items.size() > 0) ? new ActivitySection(items) : null;
        }
        return null;
    }

    private boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}
