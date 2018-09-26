/**
 * Test for your ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid2");
        Resume r3 = new Resume();
        r3.setUuid("uuid3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        System.out.println("\n");

        // Проверка: get("uuid2") ссылается на объект r2
        Resume r2_received = ARRAY_STORAGE.get("uuid2");
        System.out.println("r2 " + ((r2 == r2_received) ? "==" : "!=") + " get(\"" + r2_received.getUuid() + "\")");

        // Проверка: resumeNew является другим объектом, хотя имеет такой же UUID, как у r2
        Resume resumeNew = new Resume();
        resumeNew.setUuid("uuid2");
        System.out.println("r2 " + ((r2 == resumeNew) ? "==" : "!=") + " resumeNew (\"" + resumeNew.getUuid() + "\")");

        // Проверка: после обновления, get("uuid2") стал возвращать объект resumeNew, а не r2
        ARRAY_STORAGE.update(resumeNew);
        Resume resume = ARRAY_STORAGE.get("uuid2");
        System.out.println("After update:");
        System.out.println("resumeNew " + ((resumeNew == resume) ? "==" : "!=") + " get(\"" + resume.getUuid() + "\")");
        Resume r2_received2 = ARRAY_STORAGE.get("uuid2");
        System.out.println("r2 " + ((r2 == r2_received2) ? "==" : "!=") + " get(\"" + r2_received2.getUuid() + "\")");

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
