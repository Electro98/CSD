package webapp.spring;

import webapp.entity.Stationery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


import javax.sql.DataSource;
import java.util.List;

@Component
public class StationeryDAO {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Stationery> findAll() {
        return jdbcTemplate.query("SELECT * FROM stationery", new BeanPropertyRowMapper<>(Stationery.class));
    }

    public Stationery findById(int id) {
        return jdbcTemplate.query("SELECT * FROM stationery WHERE id = ?", new BeanPropertyRowMapper<>(Stationery.class), id).stream().findAny().orElse(null);
    }

    public List<Stationery> findByName(String nameLike) {
        return jdbcTemplate.query("SELECT * FROM stationery WHERE name LIKE ?", new BeanPropertyRowMapper<>(Stationery.class), nameLike);
    }

    public int insert(Stationery stationery) {
        return jdbcTemplate.update(
                "insert into stationery (name, type, price, num_in_box) VALUES (?, ?, ?, ?)",
                stationery.getName(),
                stationery.getType(),
                stationery.getPrice(),
                stationery.getNum_in_box());
    }

    public int update(Stationery stationery) {
        return jdbcTemplate.update(
                "UPDATE stationery SET name = ?, type = ?, price = ?, num_in_box = ? WHERE id = ?",
                stationery.getName(),
                stationery.getType(),
                stationery.getPrice(),
                stationery.getNum_in_box(),
                stationery.getId());
    }

    public int delete(int stationery_id) {
        return jdbcTemplate.update("DELETE FROM stationery WHERE id = ?", stationery_id);
    }

    public void checkTableCreated() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS stationery");
        jdbcTemplate.execute("CREATE TABLE stationery (id integer NOT NULL PRIMARY KEY, name varchar NOT NULL, type varchar NOT NULL, price real NOT NULL, num_in_box int NOT NULL)");
    }

    public void insertExampleData() {
        Stationery stationers[] = {
                new Stationery("Berlingo xGold 0.7 Blue", "pen", 55.5, 120),
                new Stationery("Berlingo xGold 0.7 Red", "pen", 56.5, 120),
                new Stationery("Berlingo Instinct", "eraser", 40.1, 50),
                new Stationery("Cat - pen cap", "pen cap", 10.1, 20),
                new Stationery("Good old eraser", "eraser", 5.42, 42),
        };
        for (Stationery stationery: stationers) {
            insert(stationery);
        }
    }
}
