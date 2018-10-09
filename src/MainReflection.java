import model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException,
            NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume("John");
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");

        System.out.println(r.toString());
        System.out.println(r.getClass().getDeclaredMethod("toString").invoke(r));
    }
}