package twe.testprojects.books.viewModel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import twe.testprojects.books.model.Author;

@Database(entities = Author.class, version = 1,exportSchema = false)
abstract class AuthorDatabase extends RoomDatabase {
    private static AuthorDatabase instance;

    abstract AuthorDao authorDao();

    static synchronized AuthorDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AuthorDatabase.class, "author")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private AuthorDao authorDao;

        PopulateDBAsyncTask(AuthorDatabase db) {
            this.authorDao = db.authorDao();
        }

        @Override
        protected Void doInBackground(Void... Void) {

            return null;
        }
    }
}
