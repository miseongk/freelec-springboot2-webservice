package com.jojoldu.book.springboot.domain.posts;

import java.sql.PreparedStatement;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class PostsDao {

    private final JdbcTemplate jdbcTemplate;

    public PostsDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Posts> rowMapper = (resultSet, rowNum) -> Posts.builder()
            .title(resultSet.getString("title"))
            .content(resultSet.getString("content"))
            .author(resultSet.getString("author"))
            .build();

    public Long save(Posts posts) {
        String sql = "INSERT INTO POSTS(title, content, author) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
            ps.setString(1, posts.getTitle());
            ps.setString(2, posts.getContent());
            ps.setString(3, posts.getAuthor());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Posts> findAll() {
        String sql = "SELECT * FROM posts;";

        return jdbcTemplate.query(sql, rowMapper);
    }

    public void deleteAll() {
        String sql = "DELETE FROM posts WHERE id = ?";
        List<Posts> all = findAll();
        for (Posts posts : all) {
            jdbcTemplate.update(sql, posts.getId());
        }
    }
}
