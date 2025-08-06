package mx.uam.ayd.proyecto;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import mx.uam.ayd.proyecto.presentacion.principal.VentanaPrincipal;

@TestConfiguration
@Profile("test")
@EnableTransactionManagement
public class TestConfig {

    @Bean
    @Primary
    public VentanaPrincipal ventanaPrincipal() {
        return new VentanaPrincipal();
    }

} 
