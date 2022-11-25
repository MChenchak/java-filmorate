package ru.yandex.practicum.filmorate.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class UserDao implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        String query = "select * from person";
        return jdbcTemplate.query(query, this::userMapper);
    }


    @Override
    public List<User> findUsersBySubscribers(User user) {
        return null;
    }

    @Override
    public User save(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("person")
                .usingGeneratedKeyColumns("id");

        Long userId = simpleJdbcInsert.executeAndReturnKey(user.toMap()).longValue();
        return findById(userId).get();
    }

    @Override
    public User update(User user) {
        findById(user.getId());
        String query = "update person set " +
                            "name = ?, login = ?, email =?, birthday = ? " +
                            "where id = ?";
        jdbcTemplate.update(query,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());
        return findById(user.getId()).get();
    }

    @Override
    public Optional<User> findById(Long id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(
                "select * from person where id = ?", id);

        if(userRows.next()) {
            User user = User.builder()
                    .id(userRows.getLong("id"))
                    .name(userRows.getString("name"))
                    .login(userRows.getString("login"))
                    .email(userRows.getString("email"))
                    .birthday(userRows.getDate("birthday").toLocalDate())
                    .build();
            return Optional.of(user);
        } else {
            throw new IllegalStateException("User not Found");
        }
    }

    public List<User> findCommonFriends(Long userId, Long otherUserId) {
        String query =
                "select * from person \n" +
                        "where person.id in (\n" +
                        "    select subscription_id from friendship\n" +
                        "       where subscriber_id in (?,?)\n" +
                        "       group by subscription_id\n" +
                        "       having count(subscription_id)>1\n" +
                        ")";
        return jdbcTemplate.query(query, this::userMapper, userId, otherUserId);

    }

    private User userMapper(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .email(resultSet.getString("email"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }

    public void addFriend(Long userId, Long friendId) {
        String query = "insert into friendship (subscription_id, subscriber_id)\n" +
                "values (?, ?)";
        jdbcTemplate.update(query, friendId, userId);
    }

    public List<User> getFriends(Long userId) {
        String query =
                "select * from person \n" +
                        "where person.id in (\n" +
                        "    select distinct subscription_id from friendship\n" +
                        "       where subscriber_id = ?" +
                        ")";
        return jdbcTemplate.query(query, this::userMapper, userId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        String query =
                "delete from friendship " +
                        "where subscription_id = ? " +
                        "   and subscriber_id = ?";
        jdbcTemplate.update(query, friendId, userId);
    }
}
