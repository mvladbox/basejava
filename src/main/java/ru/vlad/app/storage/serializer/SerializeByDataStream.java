package ru.vlad.app.storage.serializer;

import ru.vlad.app.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SerializeByDataStream implements SerializeStrategy {

    @FunctionalInterface
    public interface ThrowingConsumer<T> extends Consumer<T> {
        @Override
        default void accept(T t) {
            try {
                acceptThrows(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        void acceptThrows(T t) throws IOException;
    }

    @FunctionalInterface
    public interface ThrowingIntConsumer extends IntConsumer {

        @Override
        default void accept(int i) {
            try {
                acceptThrows(i);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        void acceptThrows(int i) throws IOException;
    }

    @FunctionalInterface
    public interface ThrowingBiConsumer<T, U> extends BiConsumer<T, U> {
        @Override
        default void accept(T t, U u) {
            try {
                acceptThrows(t, u);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        void acceptThrows(T t, U u) throws IOException;
    }

    @FunctionalInterface
    public interface ThrowingSupplier<T> extends Supplier<T> {
        @Override
        default T get() {
            try {
                return getThrows();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        T getThrows() throws IOException;
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            write(dos, resume.getUuid());
            write(dos, resume.getFullName());

            Collection<Contact> contacts = resume.getContacts().values();
            dos.writeInt(contacts.size());
            contacts.forEach((ThrowingConsumer<Contact>) (contact) -> {
                write(dos, contact.getType().name());
                write(dos, contact.getTitle());
                write(dos, contact.getValue());
            });

            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            sections.forEach((ThrowingBiConsumer<SectionType, AbstractSection>) (type, section) -> {
                write(dos, type.name());
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        write(dos, ((SimpleTextSection) section).getDescription());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> items = ((ListOfTextSection) section).getItems();
                        dos.writeInt(items.size());
                        items.forEach((ThrowingConsumer<String>) item -> write(dos, item));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Activity> activities = ((ActivitySection) section).getItems();
                        dos.writeInt(activities.size());
                        activities.forEach((ThrowingConsumer<Activity>) activity -> {
                            write(dos, activity.getOrganization().getName());
                            write(dos, activity.getOrganization().getContact());
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

            Stream.generate((ThrowingSupplier<Contact>) () -> new Contact(ContactType.valueOf(read(dis)), read(dis), read(dis)))
                    .limit(dis.readInt())
                    .forEach(resume::addContact);

            IntStream.range(0, dis.readInt())
                    .forEach((ThrowingIntConsumer) (int x) -> {
                                SectionType type = SectionType.valueOf(read(dis));
                                switch (type) {
                                    case OBJECTIVE:
                                    case PERSONAL:
                                        resume.addSection(type, new SimpleTextSection(read(dis)));
                                        break;
                                    case ACHIEVEMENT:
                                    case QUALIFICATIONS:
                                        resume.addSection(type, new ListOfTextSection(
                                                Stream.generate((ThrowingSupplier<String>) () -> read(dis))
                                                        .limit(dis.readInt())
                                                        .collect(Collectors.toList())));
                                        break;
                                    case EXPERIENCE:
                                    case EDUCATION:
                                        resume.addSection(type, new ActivitySection(
                                                Stream.generate((ThrowingSupplier<Activity>) () -> new Activity(
                                                        new Organization(read(dis), read(dis)),
                                                        readYearMonth(dis),
                                                        readYearMonth(dis),
                                                        read(dis),
                                                        read(dis)))
                                                        .limit(dis.readInt())
                                                        .collect(Collectors.toList())));
                                        break;
                                }
                            }
                    );

            return resume;
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
