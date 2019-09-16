package twe.testprojects.books.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthorTest {

    Author author;

    @Before
    public void setUp(){
        author = new Author("test", "testtest");
    }

    @Test
    public void getId() {
        author.setId(1);
        assertEquals(1, author.getId());
    }

    @Test
    public void setId() {
        author.setId(1);
        assertEquals(1, author.getId());
    }

    @Test
    public void getName() {
        assertEquals("test", author.getName());
    }

    @Test
    public void setName() {
        author.setName("newtest");
        assertEquals("newtest", author.getName());
    }

    @Test
    public void getBirthdate() {
        assertEquals("testtest", author.getBirthdate());
    }

    @Test
    public void setBirthdate() {
        author.setBirthdate("newtest");
        assertEquals("newtest", author.getBirthdate());
    }
}