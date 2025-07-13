package cl.bci.ejercicio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Configuración de Swagger para la documentación automática de la API.
 * 
 * Esta clase configura Swagger/OpenAPI para generar documentación interactiva
 * de todos los endpoints de la API REST.
 * 
 * @author BCI
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configura el bean Docket para Swagger.
     * 
     * Define la configuración principal de Swagger incluyendo:
     * - Los paquetes a escanear para encontrar controladores
     * - Los paths a incluir en la documentación
     * - La información general de la API
     * 
     * @return Docket configurado para la documentación de la API
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cl.bci.ejercicio.controller"))
                .paths(PathSelectors.regex("/v1/bci.*"))
                .build()
                .apiInfo(apiInfo());
    }

    /**
     * Configura la información general de la API.
     * 
     * Define metadatos como título, descripción, versión y contacto
     * que aparecerán en la documentación de Swagger.
     * 
     * @return ApiInfo con la información de la API
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("BCI API REST")
                .description("API REST para gestión de usuarios del proyecto BCI. " +
                           "Permite registrar usuarios con validaciones de email y contraseña, " +
                           "así como realizar login mediante tokens JWT.")
                .version("1.0.0")
                .contact(new Contact("BCI Team", "https://www.bci.cl", "soporte@bci.cl"))
                .license("MIT License")
                .licenseUrl("https://opensource.org/licenses/MIT")
                .build();
    }
} 