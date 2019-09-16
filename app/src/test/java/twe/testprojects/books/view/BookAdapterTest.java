package twe.testprojects.books.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import twe.testprojects.books.model.Author;
import twe.testprojects.books.model.Book;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class BookAdapterTest {

    @Spy
    BookAdapter adapter = new BookAdapter();

    private Book book;
    private Author author;
    private List<Book> books;
    private List<Author> authors;

    @Before
    public void setUp(){
        books = new ArrayList<Book>();
        authors = new ArrayList<>();
        book = new Book("test", "test", 1);
        author = new Author("test2", "test");
        books.add(book);
        authors.add(author);
        author.setId(1);
    }

    @Test
    public void testAdapterBook() {

        doNothing().when(adapter).notifyChanged();
        adapter.setBooks(books);
        Book mBook = adapter.getNoteAt(0);
        assertEquals("test", mBook.getTitle());

    }

    @Test
    public void testAdapterAuthor() {
        setUp();

        doNothing().when(adapter).notifyChanged();
        adapter.setBooks(books);
        adapter.setAuthors(authors);
        Book mBook = adapter.getNoteAt(0);
        Author mAuthor = adapter.getAuthor(mBook.getAuthorId());
        assertEquals("test2", mAuthor.getName());

    }

}