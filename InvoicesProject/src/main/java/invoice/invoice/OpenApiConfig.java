package invoice.invoice;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
@Configuration
public class OpenApiConfig {
	

	@Bean
	public OpenAPI myOpenAPI() {
		Server devServer = new Server();
		devServer.setUrl("http://localhost:8082");
		devServer.setDescription("Server url in dev environment:");
		
		Server prodServer = new Server();
		prodServer.setUrl("http://localhost:8082");
		prodServer.setDescription("Server url in prod environment:");
		
		Contact contact = new Contact();
		contact.setEmail("k73206548@gmail.com");
		contact.setName("Invoice");
		contact.setUrl("http://localhost:8082");
		
		License mitLicense = new License();
		mitLicense.name("MIT License");
		mitLicense.url("");
		
		Info info = new Info();
		info.title("Invoice Management API");
		info.contact(contact);
		info.description("This API exposes endpoints to manage invoices");
		info.termsOfService("");
		info.license(mitLicense);
		return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
		
		
		
	}

}
