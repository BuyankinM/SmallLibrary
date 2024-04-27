package ru.buyankin.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.buyankin.spring.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class BookDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book order by title, author",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getBook(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?",
                        new BeanPropertyRowMapper<>(Book.class),
                        id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET title=?, author=?, year=? WHERE id=?",
                book.getTitle(),
                book.getAuthor(),
                book.getYear(),
                id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(title, author, year) VALUES (?, ?, ?)",
                book.getTitle(),
                book.getAuthor(),
                book.getYear());
    }

    public String getReaderFIO(int id) {
        return jdbcTemplate.query("SELECT reader.name as name\n" +
                        "FROM book\n" +
                        "       INNER JOIN reader on reader.id = book.reader_id\n" +
                        "WHERE book.id = ?",
                rs -> rs.next() ? rs.getString("name") : null,
                id);
    }

    public void freeBook(int id) {
        jdbcTemplate.update("UPDATE book SET reader_id = NULL WHERE id=?", id);
    }
}
