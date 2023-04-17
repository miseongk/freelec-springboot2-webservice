package com.jojoldu.book.springboot.domain.posts;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

    public void save(Posts posts) {
        System.out.println(jdbcTemplate.getDataSource());
        String sql = "INSERT INTO POSTS(title, content, author) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, posts.getTitle(), posts.getContent(), posts.getAuthor());
    }

    public List<Posts> findAll() {
        String sql = "SELECT * FROM posts;";

        return jdbcTemplate.query(sql, rowMapper);
    }
}
