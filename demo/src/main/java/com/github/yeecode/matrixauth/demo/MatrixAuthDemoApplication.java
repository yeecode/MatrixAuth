package com.github.yeecode.matrixauth.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.github.yeecode.matrixauth.demo", "com.github.yeecode.matrixauth.client"})
public class MatrixAuthDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MatrixAuthDemoApplication.class, args);
        System.out.println(
                "\n" +
                        "  __  __       _        _          _         _   _     \n" +
                        " |  \\/  | __ _| |_ _ __(_)_  __   / \\  _   _| |_| |__  \n" +
                        " | |\\/| |/ _` | __| '__| \\ \\/ /  / _ \\| | | | __| '_ \\ \n" +
                        " | |  | | (_| | |_| |  | |>  <  / ___ \\ |_| | |_| | | |\n" +
                        " |_|  |_|\\__,_|\\__|_|  |_/_/\\_\\/_/   \\_\\__,_|\\__|_| |_|\n" +
                        "                                                       \n");
        System.out.println("MatrixAuth Demo starts successfully!");
        System.out.println("Visit the following address for more information:");
        System.out.println("http://127.0.0.1:12302");
    }
}
