package ru.vlad.app.storage.serializer;

import ru.vlad.app.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SerializeByDataStream implements SerializeStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            write(dos, resume.getUuid());
            write(dos, resume.getFullName());

            writeCollection(dos, resume.getContacts().entrySet(), map -> {
                Contact contact = map.getValue();
                write(dos, map.getKey().name());
                write(dos, contact.getValue());
            });

            writeCollection(dos, resume.getSections().entrySet(), map -> {
                AbstractSection section = map.getValue();
                write(dos, map.getKey().name());
                switch (map.getKey()) {
                    case OBJECTIVE:
                    case PERSONAL:
                        write(dos, ((SimpleTextSection) section).getDescription());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(dos, ((ListOfTextSection) section).getItems(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(dos, ((ActivitySection) section).getItems(), activity -> {
                            write(dos, activity.getOrganization().getName());
                            write(dos, activity.getOrganization().getUrl());
                            write(dos, activity.getStartDate());
                            write(dos, activity.getEndDate());
                            write(dos, activity.getTitle());
                            write(dos, activity.getDescription());
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(read(dis), read(dis));

            readCollection(dis, () -> resume.addContact(new Contact(ContactType.valueOf(read(dis)), read(dis))));

            readCollection(dis, () -> {
                SectionType type = SectionType.valueOf(read(dis));
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(type, new SimpleTextSection(read(dis)));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> items = new ArrayList<>();
                        readCollection(dis, () -> items.add(read(dis)));
                        resume.addSection(type, new ListOfTextSection(items));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Activity> activities = new ArrayList<>();
                        readCollection(dis, () -> activities.add(new Activity(
                                new Organization(read(dis), read(dis)),
                                readYearMonth(dis),
                                readYearMonth(dis),
                                read(dis),
                                read(dis))));
                        resume.addSection(type, new ActivitySection(activities));
                        break;
                }
            });

            return resume;
        }
    }

    @FunctionalInterface
    private interface Writer<T> {
        void write(T t) throws IOException;
    }

    @FunctionalInterface
    private interface Reader {
        void read() throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, Writer<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.write(element);
        }
    }

    private void readCollection(DataInputStream dis, Reader reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    private void write(DataOutputStream dos, String str) throws IOException {
        str = (str == null) ? "" : str;
        dos.writeUTF(str);
    }

    private void write(DataOutputStream dos, YearMonth date) throws IOException {
        write(dos, (date == null) ? null : date.toString());
    }

    private void write(DataOutputStream dos, Contact contact) throws IOException {
        write(dos, (contact == null) ? null : contact.getValue());
    }

    private String read(DataInputStream dis) throws IOException {
        String str = dis.readUTF();
        return (str.length() == 0) ? null : str;
    }

    private YearMonth readYearMonth(DataInputStream dis) throws IOException {
        String str = read(dis);
        return (str == null) ? null : YearMonth.parse(str);
    }
}
