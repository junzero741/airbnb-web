package com.example.airbnb.dao;

import com.example.airbnb.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    public UserDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Optional<User> findByUserId(String userId) {
        String sql = "SELECT * FROM user WHERE user_id = ?";
        List<User> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), userId);
        return result.stream().findAny();
    }

    public void save(User user) {
        String sql = "INSERT INTO user (user_id, email, `name`, token) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE token = ?";
        jdbcTemplate.update(sql, user.getUserId(), user.getEmail(), user.getName(), user.getToken(), user.getToken());
    }
}

