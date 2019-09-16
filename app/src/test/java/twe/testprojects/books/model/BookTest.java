package twe.testprojects.books.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BookTest {

    Book book;

    @Before
    public void setUp(){
        book = new Book("test", "testtest", 12);
    }


    @Test
    public void getId() {
        book.setId(1);
        assertEquals(1, book.getId());
    }

    @Test
    public void setId() {
        book.setId(1);
        assertEquals(1, book.getId());
    }

    @Test
    public void getTitle() {
        assertEquals("test", book.getTitle());
    }

    @Test
    public void setTitle() {
        book.setTitle("newtest");
        assertEquals("newtest", book.getTitle());
    }

    @Test
    public void getDescription() {
        assertEquals("testtest", book.getDescription());
    }

    @Test
    public void setDescription() {
        book.setDescription("newtest");
        assertEquals("newtest", book.getDescription());
    }

    @Test
    public void getAuthorId() {
        assertEquals(12, book.getAuthorId());
    }

    @Test
    public void setAuthorId() {
        book.setAuthorId(15);
        assertEquals(15, book.getAuthorId());
    }
}