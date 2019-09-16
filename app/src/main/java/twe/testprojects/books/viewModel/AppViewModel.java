package twe.testprojects.books.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import twe.testprojects.books.model.Author;
import twe.testprojects.books.model.Book;

import java.util.List;

public class AppViewModel extends AndroidViewModel {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private LiveData<List<Book>> allNotes;
    private LiveData<List<Author>> allAuthors;

    public AppViewModel(@NonNull Application application) {
        super(application);
        bookRepository = new BookRepository(application);
        authorRepository = new AuthorRepository(application);
        allNotes = bookRepository.getAllNotes();
        allAuthors = authorRepository.getAllAuthors();
    }

    public void insert(Book book) {
        bookRepository.insert(book);
    }

    public void update(Book book) {
        bookRepository.update(book);
    }

    public void delete(Book book) {
        bookRepository.delete(book);
    }

    public void deleteAll() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    public LiveData<List<Book>> getAllNotes() {
        return allNotes;
    }

    public void insert(Author author) {
        authorRepository.insert(author);
    }

    public void update(Author author) {
        authorRepository.update(author);
    }

    public void delete(Author author) {
        authorRepository.delete(author);
    }

    public LiveData<List<Author>> getAllAuthors() {
        return allAuthors;
    }
}
