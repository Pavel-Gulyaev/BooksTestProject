package twe.testprojects.books.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import twe.testprojects.books.model.Author;
import twe.testprojects.books.model.Book;
import twe.testprojects.books.R;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.Noteholder> {
    private List<Book> books = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public Noteholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new Noteholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Noteholder holder, final int position) {
        Book currentBook = books.get(position);
        Author currentAuthor = getAuthor(currentBook.getAuthorId());
        holder.tv_title.setText(currentBook.getTitle());
        holder.tv_author.setText(currentAuthor.getName());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public Book getNoteAt(int positon) {
        return books.get(positon);
    }

    public Author getAuthor(int id){
        for (Author author: authors){
            if (author.getId() == id){
                return author;
            }
        }
        return new Author("", "");
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyChanged();
    }


    public void setAuthors(List<Author> authors) {
        this.authors = authors;
        notifyChanged();
    }

    public void notifyChanged(){
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class Noteholder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_author;

        public Noteholder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(books.get(position));
                }
            });
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_author = itemView.findViewById(R.id.tv_author);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }
}
