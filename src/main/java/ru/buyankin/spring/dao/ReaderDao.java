package ru.buyankin.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.buyankin.spring.models.Reader;

import java.util.List;
import java.util.Optional;

@Component
public class ReaderDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReaderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reader> index() {
        return jdbcTemplate.query("SELECT * FROM reader order by name",
                new BeanPropertyRowMapper<>(Reader.class));
    }

    public Reader getReader(int id) {
        return jdbcTemplate.query("SELECT * FROM reader WHERE id=?",
                        new BeanPropertyRowMapper<>(Reader.class),
                        id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void update(int id, Reader reader) {
        jdbcTemplate.update("UPDATE reader SET name=?, birthyear=? WHERE id=?",
                reader.getName(),
                reader.getBirthYear(),
                id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM reader WHERE id=?", id);
    }
}
