package twe.testprojects.books.viewModel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import twe.testprojects.books.model.Book;

@Database(entities = Book.class, version = 1,exportSchema = false)
abstract class BookDatabase extends RoomDatabase {
    private static BookDatabase instance;

    abstract BookDao noteDao();

    static synchronized BookDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, BookDatabase.class, "note_database")
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
        private BookDao bookDao;

        PopulateDBAsyncTask(BookDatabase db) {
            this.bookDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... Void) {
            return null;
        }
    }
}
