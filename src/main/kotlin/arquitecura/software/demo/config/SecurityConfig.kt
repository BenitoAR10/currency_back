//package arquitecura.software.demo.config
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
//import org.springframework.security.core.userdetails.User
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.provisioning.InMemoryUserDetailsManager
//
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableWebSecurity
//
//class SecurityConfig: WebSecurityConfigurerAdapter(){
//    override fun configure(http: HttpSecurity){
//        http
//            .authorizeRequests()
//            .antMatchers("/api/v1/currency").permitAll()   // podemos especificar rutas especificas para que tengan un rol
//            .and()
//            .authorizeRequests()
//            .anyRequest()
//            .authenticated()
//            .and()
//            .httpBasic() // http basic te pasan un usuario y contraseñay lo envias en el header de la peticion
//    }
//
//    @Bean
//    fun users(): UserDetailsService{
//        val user = User.builder()
//            .username("user")
//            .password("{noop}user") // {noop} es para que no encripte la contraseña
//            .roles("USER")
//            .build()
//        val admin = User.builder()
//            .username("admin")
//            .password("{noop}admin")
//            .roles("USER", "ADMIN")
//            .build()
//
//        return InMemoryUserDetailsManager(user, admin)
//    }
//}