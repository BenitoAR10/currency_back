package arquitecura.software.demo.bl

import arquitecura.software.demo.dto.ExchangeDto
import arquitecura.software.demo.dao.Repository.CurrencyRepository
import arquitecura.software.demo.dao.Currency
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.math.BigDecimal
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Value
import java.lang.Exception
import org.springframework.beans.factory.annotation.Autowired
import java.util.Date
import java.util.*


@Service

class CurrencyBl @Autowired constructor(private val currencyRepository: CurrencyRepository){

    companion object {
        val logger: Logger = LoggerFactory.getLogger(CurrencyBl::class.java.name)
    }

    @Value("\${currency.url}")
    private val url: String?=null

    @Value("\${currency.key}")
    private val key: String?=null


    fun getExchange(from: String, to: String, amount: BigDecimal): ExchangeDto?{
        if (amount < BigDecimal.ZERO) {
            logger.error("El monto no puede ser negativo");
            throw IllegalArgumentException("El monto no puede ser negativo");
        }

        val client = OkHttpClient().newBuilder().build()
        val request = Request.Builder()
            .url("$url?to=$to&from=$from&amount=$amount")
            .addHeader("apikey", key)
            .build();

        try{
            logger.info("Invocando a la API")
            val response = client.newCall(request).execute()
            logger.info("Procesando la respuesta")
            val responseBody = response.body().string();
            val objectMapper = ObjectMapper();
            //println(responseBody)
            val currencyDto = objectMapper.readValue(responseBody, ExchangeDto::class.java);
            val currency: Currency = Currency()
            currency.currencyFrom = from
            currency.currencyTo = to
            currency.amount = amount
            currency.result = currencyDto.result!!
            currency.date = Date()
            currencyRepository.save(currency)
            logger.info("Se guardo la informacion en la base de datos: ${responseBody}")
            return currencyDto;
        } catch (e: Exception){
            logger.error("Error al invocar la API", e);
            throw e;
        }


    }
}