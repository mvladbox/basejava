package ru.vlad.app;

import ru.vlad.app.model.*;

import java.time.YearMonth;

import static ru.vlad.app.model.ContactType.*;
import static ru.vlad.app.model.SectionType.*;

public class ResumeTestData {

    public static final String UUID_2 = "uuid2";
    public static final String UUID_3 = "uuid3";
    public static final String UUID_4 = "uuid4";
    public static final String UUID_DUMMY = "dummy";
    public static final String FULL_NAME_1;
    public static final String FULL_NAME_2 = "Fullname Two";
    public static final String FULL_NAME_3 = "First";
    public static final String FULL_NAME_4;
    public static final String FULL_NAME_DUMMY = "Fullname Dummy";

    public static final Resume R1;
    public static final Resume R2;
    public static final Resume R3;
    public static final Resume R4;
    public static final Resume R_DUMMY;

    static {
        R1 = new Resume("Григорий Кислин");

        R1.addContact(new Contact(PHONE, "+7(921) 855-0482"));
        R1.addContact(new Contact(SKYPE, "grigory.kislin"));
        R1.addContact(new Contact(EMAIL, "gkislin@yandex.ru"));
        R1.addContact(new Contact(LINKEDIN, "https://www.linkedin.com/in/gkislin"));
        R1.addContact(new Contact(GITHUB, "https://github.com/gkislin"));
        R1.addContact(new Contact(STACKOVERFLOW, "https://stackoverflow.com/users/548473"));
        R1.addContact(new Contact(HOMEPAGE, "http://gkislin.ru/"));

        R1.addSection(OBJECTIVE, new SimpleTextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        R1.addSection(PERSONAL, new SimpleTextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры"));

        R1.addSection(ACHIEVEMENT, new ListOfTextSection(
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
        ));
        R1.addSection(QUALIFICATIONS, new ListOfTextSection(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle",
                "MySQL, SQLite, MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements)",
                "Python: Django",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT",
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix",
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer",
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов",
                "проектрирования, архитектурных шаблонов, UML, функционального",
                "программирования",
                "Родной русский, английский \"upper intermediate\""
        ));

        R1.addSection(EXPERIENCE, new ActivitySection(
                new Activity(new Organization("Java Online Projects", "http://javaops.ru/"),
                        YearMonth.of(2013, 10),
                        null,
                        "Автор проекта",
                        "Создание, организация и проведение Java онлайн проектов и стажировок"),
                new Activity(new Organization("Wrike", "https://www.wrike.com/"),
                        YearMonth.of(2014, 10),
                        YearMonth.of(2016, 1),
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."),
                new Activity(new Organization("RIT Center"),
                        YearMonth.of(2012, 4),
                        YearMonth.of(2014, 10),
                        "Java архитектор",
                        "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"),
                new Activity(new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/"),
                        YearMonth.of(2010, 12),
                        YearMonth.of(2012, 4),
                        "Ведущий программист",
                        "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."),
                new Activity(new Organization("Yota", "https://www.yota.ru/"),
                        YearMonth.of(2008, 6),
                        YearMonth.of(2010, 12),
                        "Ведущий специалист",
                        "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"),
                new Activity(new Organization("Enkata", "http://enkata.com/"),
                        YearMonth.of(2007, 3),
                        YearMonth.of(2008, 6),
                        "Разработчик ПО",
                        "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."),
                new Activity(new Organization("Siemens AG", "https://www.siemens.com/ru/ru/home.html"),
                        YearMonth.of(2005, 1),
                        YearMonth.of(2007, 2),
                        "Разработчик ПО",
                        "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."),
                new Activity(new Organization("Alcatel", "http://www.alcatel.ru/"),
                        YearMonth.of(1997, 9),
                        YearMonth.of(2005, 1),
                        "Инженер по аппаратному и программному тестированию",
                        "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).")
        ));
        Organization org = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru");
        R1.addSection(EDUCATION, new ActivitySection(
                new Activity(new Organization("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru"),
                        YearMonth.of(1984, 9),
                        YearMonth.of(1987, 6),
                        "Закончил с отличием"),
                new Activity(org,
                        YearMonth.of(1987, 9),
                        YearMonth.of(1993, 7),
                        "Инженер (программист Fortran, C)"),
                new Activity(org,
                        YearMonth.of(1993, 9),
                        YearMonth.of(1996, 7),
                        "Аспирантура (программист С, С++)"),
                new Activity(new Organization("Alcatel", "http://www.alcatel.ru"),
                        YearMonth.of(1997, 9),
                        YearMonth.of(1998, 3),
                        "6 месяцев обучения цифровым телефонным сетям (Москва)"),
                new Activity(new Organization("Siemens AG", "http://www.siemens.ru/"),
                        YearMonth.of(2005, 1),
                        YearMonth.of(2005, 4),
                        "3 месяца обучения мобильным IN сетям (Берлин)"),
                new Activity(new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"),
                        YearMonth.of(2011, 3),
                        YearMonth.of(2011, 4),
                        "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""),
                new Activity(new Organization("Coursera", "https://www.coursera.org/course/progfun"),
                        YearMonth.of(2013, 3),
                        YearMonth.of(2013, 5),
                        "\"Functional Programming Principles in Scala\" by Martin Odersky")
        ));

        FULL_NAME_1 = R1.getFullName();
        FULL_NAME_4 = FULL_NAME_1;
        R2 = new Resume(UUID_2, FULL_NAME_2);
        R3 = new Resume(UUID_3, FULL_NAME_3);
        R4 = new Resume(UUID_4, FULL_NAME_4);
        R_DUMMY = new Resume(UUID_DUMMY, FULL_NAME_DUMMY);
    }

    public static void main(String[] args) {
        System.out.println(R1);
    }
}
