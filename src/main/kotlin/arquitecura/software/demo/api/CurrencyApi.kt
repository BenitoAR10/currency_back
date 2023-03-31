package arquitecura.software.demo.api

import arquitecura.software.demo.bl.CurrencyBl
import arquitecura.software.demo.dao.Currency
import arquitecura.software.demo.dto.ExchangeDto
import arquitecura.software.demo.dto.ResponseDto
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.slf4j.Logger
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
//import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.math.BigDecimal
import java.security.Principal


@RestController
@RequestMapping("/api/v1")
class CurrencyApi (val currencyBl: CurrencyBl){

    private val logger: Logger = LoggerFactory.getLogger(CurrencyApi::class.java)

    @GetMapping("/currency")
    fun getExchange(@RequestParam from: String, @RequestParam to: String, @RequestParam amount: BigDecimal): ResponseEntity<Any>{
        logger.info("Empezando la logica de negocio");
        val exchangeDto = currencyBl.getExchange(from, to, amount);
        //val responseDto = ResponseDto(exchangeDto, true, null);
        return ResponseEntity.ok(exchangeDto);
    }
//    @GetMapping("/user")
//    @PreAuthorize ("hasAuthority('ROLE_USER')")
//    fun getUser(): String {
//        logger.info("Iniciando servicio user")
//        return "ROLE_USER"
//    }
//
//    @GetMapping("/admin")
//    @PreAuthorize ("hasAuthority('ROLE_ADMIN')")
//    fun getAdmin(): String {
//        logger.info("Iniciando servicio admin")
//        return "ROLE_ADMIN"
//    }

    @GetMapping("/page")
    fun getCurrencies(@RequestParam pageNumber: Int, @RequestParam pageSize: Int): Any {
        val list = currencyBl.getPaginated(pageNumber, pageSize)
        return ResponseEntity.ok(list);
    }

    // Forma para hacelro en memorias para estar "loggeado"
    @GetMapping("/principal")
    fun info(principal: Principal): String{
        return principal.toString()
    }
}