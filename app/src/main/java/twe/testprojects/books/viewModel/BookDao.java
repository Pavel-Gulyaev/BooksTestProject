package twe.testprojects.books.viewModel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import twe.testprojects.books.model.Book;

import java.util.List;

@Dao
public interface BookDao {
    @Insert
    void insert(Book book);

    @Update
    void update(Book book);

    @Delete
    void delete(Book book);

    @Query("DELETE FROM Book")
    void deleteAll();

    @Query("SELECT * FROM Book ORDER BY priority_column DESC")
    LiveData<List<Book>> getAllNotes();
}
