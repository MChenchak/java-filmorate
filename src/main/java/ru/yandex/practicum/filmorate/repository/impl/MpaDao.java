package ru.yandex.practicum.filmorate.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repository.MpaRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class MpaDao implements MpaRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Mpa> findById(int id) {
        SqlRowSet rows = jdbcTemplate.queryForRowSet(
                "select * from mpa where id = ?", id);
        if(rows.next()) {
            Mpa mpa = Mpa.builder()
                    .id(rows.getLong("id"))
                    .name(rows.getString("name"))
                    .build();
            return Optional.of(mpa);
        } else {
            throw new IllegalStateException("Mpa not Found");
        }
    }

    @Override
    public List<Mpa> findAll() {
        String query = "select * from mpa";
        return jdbcTemplate.query(query, this::mpaMapper);
    }

    private Mpa mpaMapper(ResultSet resultSet, int rowNum) throws SQLException {
        return Mpa.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
