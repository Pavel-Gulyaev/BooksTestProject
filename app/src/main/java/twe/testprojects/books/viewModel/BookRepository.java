package twe.testprojects.books.viewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import twe.testprojects.books.model.Book;

import java.util.List;

public class BookRepository {
    private BookDao bookDao;
    private LiveData<List<Book>> allNotes;

    public BookRepository(Application application) {
       BookDatabase mBookDatabase = BookDatabase.getInstance(application);
        bookDao = mBookDatabase.noteDao();
        allNotes = bookDao.getAllNotes();
    }

    void insert(Book book) {
        new InsertNoteAsyncTask(bookDao).execute(book);
    }

    void update(Book book) {
        new UpdateNoteAsyncTask(bookDao).execute(book);
    }

    void delete(Book book) {
        new DeleteNoteAsyncTask(bookDao).execute(book);
    }

    void deleteAll() {
        new DeleteAllNoteAsyncTask(bookDao).execute();
    }

    LiveData<List<Book>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Book, Void, Void> {
        private BookDao bookDao;

        private InsertNoteAsyncTask(BookDao bookDao) {
            this.bookDao = bookDao;
        }

        @Override
        protected Void doInBackground(Book... books) {
            bookDao.insert(books[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Book, Void, Void> {
        private BookDao bookDao;

        private UpdateNoteAsyncTask(BookDao bookDao) {
            this.bookDao = bookDao;
        }

        @Override
        protected Void doInBackground(Book... books) {
            bookDao.update(books[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Book, Void, Void> {
        private BookDao bookDao;

        private DeleteNoteAsyncTask(BookDao bookDao) {
            this.bookDao = bookDao;
        }

        @Override
        protected Void doInBackground(Book... books) {
            bookDao.delete(books[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {
        private BookDao bookDao;

        private DeleteAllNoteAsyncTask(BookDao bookDao) {
            this.bookDao = bookDao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            bookDao.deleteAll();
            return null;
        }
    }

}
