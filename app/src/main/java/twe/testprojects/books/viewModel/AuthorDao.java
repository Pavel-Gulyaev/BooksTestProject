package twe.testprojects.books.viewModel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import twe.testprojects.books.model.Author;

import java.util.List;

@Dao
public interface AuthorDao {
    @Insert
    void insert(Author author);

    @Update
    void update(Author author);

    @Delete
    void delete(Author author);

    @Query("DELETE FROM author")
    void deleteAll();

    @Query("SELECT * FROM author ORDER BY id")
    LiveData<List<Author>> getAllAuthors();
}
