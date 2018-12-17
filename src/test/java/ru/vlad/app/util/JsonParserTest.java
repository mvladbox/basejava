package ru.vlad.app.util;

import org.junit.Assert;
import org.junit.Test;
import ru.vlad.app.model.AbstractSection;
import ru.vlad.app.model.Resume;
import ru.vlad.app.model.SimpleTextSection;

import static ru.vlad.app.ResumeTestData.R1;

public class JsonParserTest {

    @Test
    public void testResume() {
        String json = JsonParser.write(R1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(R1, resume);
    }

    @Test
    public void write() {
        AbstractSection section1 = new SimpleTextSection("Objective1");
        String json = JsonParser.write(section1, AbstractSection.class);
        System.out.println(json);
        AbstractSection section2 = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(section1, section2);
    }
}