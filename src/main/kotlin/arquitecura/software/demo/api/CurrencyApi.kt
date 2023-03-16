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
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.math.BigDecimal


@RestController
@RequestMapping("/api/v1")
class CurrencyApi (val currencyBl: CurrencyBl){

    private val logger: Logger = LoggerFactory.getLogger(CurrencyApi::class.java)

    @GetMapping("/currency")
    fun getExchange(@RequestParam from: String, @RequestParam to: String, @RequestParam amount: BigDecimal): ResponseEntity<ResponseDto<ExchangeDto>>{
        logger.info("Empezando la logica de negocio");
        val exchangeDto = currencyBl.getExchange(from, to, amount);
        val responseDto = ResponseDto(exchangeDto, true, null);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/page")
    fun getCurrencies(@RequestParam pageNumber: Int, @RequestParam pageSize: Int): Page<Currency> {
        return currencyBl.getPaginated(pageNumber, pageSize)
    }
}