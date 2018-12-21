package works.weave.socks.orders;

import java.io.FileReader;
import java.io.IOException;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@SpringBootApplication
@EnableSwagger2

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@EnableAsync
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
    
    @Bean
   	public AlwaysSampler defaultSampler() {
   	  return new AlwaysSampler();
   	}
   	
       @Bean
       public Docket api() throws IOException, XmlPullParserException {
           MavenXpp3Reader reader = new MavenXpp3Reader();
           Model model = reader.read(new FileReader("pom.xml"));
           return new Docket(DocumentationType.SWAGGER_2)  
             .select() 
             .apis(RequestHandlerSelectors.basePackage("works.weave.socks.orders.controllers"))
             .paths(PathSelectors.any())                          
             .build().apiInfo(new ApiInfo("ShippingServiceApplication Api Documentation", "Documentation automatically generated", model.getParent().getVersion(), null, new Contact("Kennedy Torkura", "kennedy.wordpress.com", "run2obtain@gmail.com"), null, null));                                           
       }
}
