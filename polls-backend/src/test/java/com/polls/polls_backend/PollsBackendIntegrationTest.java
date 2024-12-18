package com.polls.polls_backend;

import com.polls.polls_backend.PollsBackendApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SpringBootTest(classes = PollsBackendApplication.class)
@WebAppConfiguration
@Transactional
public @interface PollsBackendIntegrationTest {

}