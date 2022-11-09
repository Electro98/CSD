package webapp.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import webapp.entity.User;

import javax.sql.DataSource;
import java.util.List;

@Component
public class UserDAO {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<>(User.class));
    }

    public User findById(int id) {
        return jdbcTemplate.query("SELECT * FROM users WHERE id = ?", new BeanPropertyRowMapper<>(User.class), id).stream().findAny().orElse(null);
    }

    public User findByName(String username) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM users WHERE username = ?", new BeanPropertyRowMapper<>(User.class), username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public boolean exist(String username) {
        return findByName(username) != null;
    }

    public int insert(User user) {
        return jdbcTemplate.update(
                "insert into users (username, password, role) VALUES (?, ?, ?)",
                user.getUsername(),
                user.getPassword(),
                user.getRole());
    }

    public int update(User user) {
        return jdbcTemplate.update(
                "UPDATE users SET username = ?, password = ?, role = ? WHERE id = ?",
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.getId());
    }

    public int delete(int user_id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id = ?", user_id);
    }

    public void checkTableCreated() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS users");
        jdbcTemplate.execute("""
                CREATE TABLE users (
                    id integer NOT NULL PRIMARY KEY,
                    username varchar NOT NULL UNIQUE,
                    password varchar NOT NULL,
                    role varchar NOT NULL)""");
    }

    public void insertBasicUsers() {
        User[] users = {
                // password is 'admin'
                new User("admin", "$2a$10$/1oM2ocQOwNur2rbRGWyE.yydJ1DVvIjMMwSG27TBAWAIlX19QyU6", User.UserRole.ADMIN.toRole()),
                // password is 'user'
                new User("user", "$2a$10$MBsVQIeKwlZIpaY6cJsHN.OE899XSAGhGSS3lMlqxEM12cZJOwNzy", User.UserRole.USER.toRole()),
        };
        for (User user: users)
            insert(user);
    }
}
