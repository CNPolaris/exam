package com.polaris.exam;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author CNPolaris
 * @version 1.0
 */
@SpringBootApplication
@MapperScan("com.polaris.exam.mapper")
@EnableScheduling
@EnableTransactionManagement
public class ExamApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExamApplication.class, args);

        System.out.println("" +
                "  _____            _                  _       \n" +
                " |  __ \\          | |                (_)      \n" +
                " | |__) |   ___   | |   __ _   _ __   _   ___ \n" +
                " |  ___/   / _ \\  | |  / _` | | '__| | | / __|\n" +
                " | |      | (_) | | | | (_| | | |    | | \\__ \\\n" +
                " |_|       \\___/  |_|  \\__,_| |_|    |_| |___/\n" +
                "                                              \n" +
                "                                             ");

        System.out.println("" +
                "   ('-.   ) (`-.         ('-.      _   .-')    \n" +
                " _(  OO)   ( OO ).      ( OO ).-. ( '.( OO )_  \n" +
                "(,------. (_/.  \\_)-.   / . --. /  ,--.   ,--.)\n" +
                " |  .---'  \\  `.'  /    | \\-.  \\   |   `.'   | \n" +
                " |  |       \\     /\\  .-'-'  |  |  |         | \n" +
                "(|  '--.     \\   \\ |   \\| |_.'  |  |  |'.'|  | \n" +
                " |  .--'    .'    \\_)   |  .-.  |  |  |   |  | \n" +
                " |  `---.  /  .'.  \\    |  | |  |  |  |   |  | \n" +
                " `------' '--'   '--'   `--' `--'  `--'   `--' ");
    }
}
