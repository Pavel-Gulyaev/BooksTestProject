package twe.testprojects.books.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import twe.testprojects.books.viewModel.AppViewModel;
import twe.testprojects.books.model.Author;
import twe.testprojects.books.model.Book;
import twe.testprojects.books.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String title;
    public static String description;
    public static int bookId;
    public static String authorName;
    public static String birthdate;
    public static int authorId;
    private static int nextId;


    private AppViewModel appViewModel;
    private RecyclerView rv_notes;
    private LinearLayout editViews;
    private CoordinatorLayout coordinatorLayout;
    private SharedPreferences sharedPref;
    private static final String ID = "id";
    private EditText et_title;
    private EditText et_description;
    private EditText et_author;
    private EditText et_birthdate;
    private Button saveBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews(){

        sharedPref = getSharedPreferences("text", MODE_PRIVATE);
        if (!(sharedPref.contains(ID))) {
            SharedPreferences.Editor editor = sharedPref.edit();
            nextId = 1;
            editor.putInt(ID, nextId);
            editor.apply();

        }
        nextId = sharedPref.getInt(ID, 1);
        editViews = findViewById(R.id.edit_views);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        final FloatingActionButton bt_addNote = findViewById(R.id.bt_addNote);
        bt_addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll();
                setText();
                editViews.setVisibility(View.VISIBLE);
                coordinatorLayout.setVisibility(View.INVISIBLE);

            }
        });

        rv_notes = findViewById(R.id.rv_notes);
        rv_notes.setLayoutManager(new LinearLayoutManager(this));
        rv_notes.setHasFixedSize(true);

        et_title = findViewById(R.id.et_title);
        et_description = findViewById(R.id.et_description);
        et_author = findViewById(R.id.et_author);
        et_birthdate = findViewById(R.id.et_birthdate);
        saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNotNull()){
                    title = et_title.getText().toString();
                    description = et_description.getText().toString();
                    authorName = et_author.getText().toString();
                    birthdate = et_birthdate.getText().toString();
                    if (bookId == 0){
                        newBook();
                    } else {
                        editBook();
                    }
                }

            }
        });


        final BookAdapter adapter = new BookAdapter();
        adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                Author author = adapter.getAuthor(book.getAuthorId());
                bookId = book.getId();
                title = book.getTitle();
                description = book.getDescription();
                authorId = book.getAuthorId();
                authorName = author.getName();
                birthdate = author.getBirthdate();
                setText();
                editViews.setVisibility(View.VISIBLE);
                coordinatorLayout.setVisibility(View.INVISIBLE);

            }
        });
        rv_notes.setAdapter(adapter);

        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        appViewModel.getAllNotes().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                adapter.setBooks(books);

            }
        });
        appViewModel.getAllAuthors().observe(this, new Observer<List<Author>>() {
            @Override
            public void onChanged(List<Author> authors) {
                adapter.setAuthors(authors);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                appViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(), "Book deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rv_notes);

    }

    private void clearAll(){
        bookId = 0;
        title = null;
        description = null;
        authorId = 0;
        authorName = null;
        birthdate = null;
    }

    private void setText(){
        et_title.setText(title);
        et_description.setText(description);
        et_author.setText(authorName);
        et_birthdate.setText(birthdate);
    }

    private boolean isNotNull(){
        return !(et_title.getText().toString().equals("")) &&
                !(et_author.getText().toString().equals("") &&
                        !(et_description.getText().toString().equals("")) &&
                        !(et_birthdate.getText().toString().equals("")));
    }

    @Override
    protected void onPause(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(ID, nextId);
        editor.apply();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.delete_all:
                appViewModel.deleteAll();
                nextId = 1;
                Toast.makeText(getApplicationContext(), "All Deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void newBook(){
        Author author = new Author(authorName, birthdate);
        author.setId(nextId++);
        appViewModel.insert(author);
        Book book = new Book(title, description, author.getId());
        appViewModel.insert(book);
        editViews.setVisibility(View.INVISIBLE);
        coordinatorLayout.setVisibility(View.VISIBLE);

    }

    private void editBook(){
        Author author = new Author(authorName, birthdate);
        author.setId(authorId);
        appViewModel.update(author);
        Book book = new Book(title, description, authorId);
        book.setId(bookId);
        appViewModel.update(book);
        editViews.setVisibility(View.INVISIBLE);
        coordinatorLayout.setVisibility(View.VISIBLE);
    }


}

