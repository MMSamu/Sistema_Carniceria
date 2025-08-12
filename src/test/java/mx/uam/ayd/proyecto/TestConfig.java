package mx.uam.ayd.proyecto;

import mx.uam.ayd.proyecto.presentacion.principal.VentanaPrincipal;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@TestConfiguration
@Profile("test")
@EnableTransactionManagement
public class TestConfig {

    @Bean
    @Primary
    public VentanaPrincipal ventanaPrincipal(ApplicationContext ctx) {
        //VentanaPrincipal requiere ApplicationContext en el constructor
        return new VentanaPrincipal(ctx);
    }

} 
