package com.adacode.impalawriter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.EnableLoadTimeWeaving;

@SpringBootApplication
//@EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
public class ImpalaWriterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImpalaWriterApplication.class, args);
	}

}
