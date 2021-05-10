package com.junyu.lesson87;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * @author haojunsheng
 * @date 2021/5/10 20:20
 */
public class MyBatisDemo {
    public static void main(String[] args) throws IOException {
        Reader reader = Resources.getResourceAsReader("lesson87/mybatis.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sessionFactory.openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        UserDo userDo = userMapper.selectById(8);
        System.out.println(userDo);
        //...
    }
}
