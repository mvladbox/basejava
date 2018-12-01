package ru.vlad.app.storage.serializer;

import ru.vlad.app.model.*;
import ru.vlad.app.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class SerializeByXml implements SerializeStrategy {

    private XmlParser xmlParser;

    public SerializeByXml() {
        xmlParser = new XmlParser(
                Resume.class, Organization.class, Activity.class, Contact.class,
                SimpleTextSection.class, ListOfTextSection.class, ActivitySection.class);
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(r, w);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }
}
