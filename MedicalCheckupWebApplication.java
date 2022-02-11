package edu.xj.medicalcheckupweb;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @author zyhsna
 */
@SpringBootApplication
@MapperScan("edu.xj.medicalcheckupweb.dao")
@CrossOrigin
public class MedicalCheckupWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalCheckupWebApplication.class, args);
    }

}
