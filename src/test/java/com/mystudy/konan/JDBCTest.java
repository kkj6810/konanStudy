package com.mystudy.konan;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class JDBCTest {

    private static final Logger logger = LoggerFactory.getLogger(JDBCTest.class);

    @Inject
    private SqlSessionFactory sqlFactory;

    @Test
    public void testFactory() {
        System.out.println(sqlFactory);
    }
    @Test
    public void testSession() {
        try (SqlSession session = sqlFactory.openSession()){
            System.out.println(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


