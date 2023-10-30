package kr.easw.lesson03;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class Lesson03Example {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Lesson03Example.class)
                .registerShutdownHook(true)
                .run(args);
    }
}
