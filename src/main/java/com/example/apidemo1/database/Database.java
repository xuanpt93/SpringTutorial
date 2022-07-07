package com.example.apidemo1.database;

import com.example.apidemo1.models.Product;
import com.example.apidemo1.repositories.ProductRespository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class Database {
//    private static Logger logger = LoggerFactory.getLogger(Database.class);
    @Bean
    CommandLineRunner initDatabse(ProductRespository productRespository){
        return new CommandLineRunner() {
            @Override
            //apacher log4j
            public void run(String... args) throws Exception {
                Product productA = new Product("macbook pro 13inch",2020,599.0,"");
                Product product1 = new Product("macbook pro 15inch",2019,2400.0,"");
                log.info("inser data: "+productRespository.save(productA));
                log.info("inser data: "+productRespository.save(product1));

            }
        };
    }
}
