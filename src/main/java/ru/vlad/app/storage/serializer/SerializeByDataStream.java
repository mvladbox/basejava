package ru.vlad.app.storage.serializer;

import ru.vlad.app.model.Contact;
import ru.vlad.app.model.ContactType;
import ru.vlad.app.model.Resume;

import java.io.*;
import java.util.Map;

public class SerializeByDataStream implements SerializeStrategy {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, Contact> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, Contact> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                String title = entry.getValue().getTitle();
                title = (title == null) ? "" : title;
                dos.writeUTF(title);
                dos.writeUTF(entry.getValue().getValue());
            }
            // TODO implements sections
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                ContactType ct = ContactType.valueOf(dis.readUTF());
                String title = dis.readUTF();
                title = (title.length() == 0) ? null : title;
                String value = dis.readUTF();
                resume.addContact(new Contact(ct, title, value));
            }
            // TODO implements sections
            return resume;
        }
    }
}
