package com.example.application.configuration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = PostgresTestConfiguration.Initializer.class)
public abstract class BaseTestClass {
}
