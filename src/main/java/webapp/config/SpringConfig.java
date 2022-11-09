package webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import webapp.spring.SQLiteUserDetailService;
import webapp.spring.StationeryDAO;
import webapp.spring.UserDAO;

import javax.sql.DataSource;

@Configuration
@ComponentScan("webapp")
@PropertySource("classpath:application.properties")
public class SpringConfig {
    @Autowired
    private Environment env;

    @Bean
    DataSource dataSource() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("datasource.driverClassName"));
        dataSource.setUrl(env.getRequiredProperty("datasource.url"));
        return dataSource;
    }

    @Bean
    StationeryDAO stationeryDAO() {
        return new StationeryDAO();
    }

    @Bean
    UserDAO userDAO() {
        return new UserDAO();
    }

    @Bean
    SQLiteUserDetailService userDetailsService() {
        return new SQLiteUserDetailService();
    }
}
