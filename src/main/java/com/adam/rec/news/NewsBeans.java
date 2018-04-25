package com.adam.rec.news;

import com.adam.rec.jdbc.JdbcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Adam
 * Created at 2018/4/5 12:47.
 */

@Component
public class NewsBeans {

    private NewsCategories newsCategories;
    private JdbcUtil jdbcUtil;

    @Autowired
    public NewsBeans(JdbcUtil jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    @Bean
    public NewsCategories getNewsCategories() {
        if(newsCategories != null) {
            newsCategories = new NewsCategories();
        }
        return newsCategories;
    }


    @Bean
    @Qualifier("windowInterval")
    public int getWindowInterval() {
        return 10;
    }

    @Bean
    @Qualifier("maxPage")
    public int getMaxPage() {
        int allSize = -1;
        try {
            String sql = "SELECT COUNT(*) FROM news";
            ResultSet resultSet = jdbcUtil.executeQuery(sql);
            if(resultSet.next()) {
                allSize = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(allSize==-1) return 0;
        else return (allSize%10>0)?(allSize/10+1):allSize/10;
    }

}
