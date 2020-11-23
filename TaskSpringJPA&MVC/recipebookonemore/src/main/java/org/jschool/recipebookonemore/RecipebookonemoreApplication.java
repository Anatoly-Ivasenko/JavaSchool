package org.jschool.recipebookonemore;

import org.jschool.recipebookonemore.utils.InitData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class RecipebookonemoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipebookonemoreApplication.class, args);
//        InitData.initData();
    }

}
