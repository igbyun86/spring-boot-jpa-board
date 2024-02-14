package com.example.springbootjpa.biz.user.repository;

import com.example.springbootjpa.biz.user.model.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.springbootjpa.biz.user.model.QUser.user;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Test
    void insertUserTest() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        List<User> users = queryFactory
                .selectFrom(user)
                .where(user.userId.eq("john_doe"))
                .fetch();
    }

}
