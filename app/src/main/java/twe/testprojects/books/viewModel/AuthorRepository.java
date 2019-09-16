package twe.testprojects.books.viewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import twe.testprojects.books.model.Author;

import java.util.List;

public class AuthorRepository {
    private AuthorDao authorDao;
    private LiveData<List<Author>> allAuthors;

    public AuthorRepository(Application application) {
        AuthorDatabase mAuthorDatabase = AuthorDatabase.getInstance(application);
        authorDao = mAuthorDatabase.authorDao();
        allAuthors = authorDao.getAllAuthors();
    }

    void insert(Author author) {
        new InsertAuthorAsyncTask(authorDao).execute(author);
    }

    void update(Author author) {
        new UpdateAuthorAsyncTask(authorDao).execute(author);
    }

    void delete(Author author) {
        new DeleteAuthorAsyncTask(authorDao).execute(author);
    }

    void deleteAll() {
        new DeleteAllAuthorAsyncTask(authorDao).execute();
    }

    LiveData<List<Author>> getAllAuthors() {
        return allAuthors;
    }

    private static class InsertAuthorAsyncTask extends AsyncTask<Author, Void, Void> {
        private AuthorDao authorDao;

        private InsertAuthorAsyncTask(AuthorDao authorDao) {
            this.authorDao = authorDao;
        }

        @Override
        protected Void doInBackground(Author... authors) {
            authorDao.insert(authors[0]);
            return null;
        }
    }

    private static class UpdateAuthorAsyncTask extends AsyncTask<Author, Void, Void> {
        private AuthorDao authorDao;

        private UpdateAuthorAsyncTask(AuthorDao authorDao) {
            this.authorDao = authorDao;
        }

        @Override
        protected Void doInBackground(Author... authors) {
            authorDao.update(authors[0]);
            return null;
        }
    }

    private static class DeleteAuthorAsyncTask extends AsyncTask<Author, Void, Void> {
        private AuthorDao authorDao;

        private DeleteAuthorAsyncTask(AuthorDao authorDao) {
            this.authorDao = authorDao;
        }

        @Override
        protected Void doInBackground(Author... authors) {
            authorDao.delete(authors[0]);
            return null;
        }
    }

    private static class DeleteAllAuthorAsyncTask extends AsyncTask<Void, Void, Void> {
        private AuthorDao authorDao;

        private DeleteAllAuthorAsyncTask(AuthorDao authorDao) {
            this.authorDao = authorDao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            authorDao.deleteAll();
            return null;
        }
    }

}

