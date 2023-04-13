package arquitecura.software.demo.bl

import arquitecura.software.demo.dto.ExchangeDto
import arquitecura.software.demo.dao.Repository.CurrencyRepository
import arquitecura.software.demo.dao.Currency
import arquitecura.software.demo.observer.Observable
import arquitecura.software.demo.observer.Observer
import arquitecura.software.demo.subscribers.Statistics
import arquitecura.software.demo.subscribers.Subscriber
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
import org.springframework.data.domain.PageRequest
import java.util.Date
import java.util.*


@Service
class CurrencyBl @Autowired constructor(private val currencyRepository: CurrencyRepository): Observable {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(CurrencyBl::class.java.name)
    }


    @Value("\${currency.url}")
    private val url: String? = null

    @Value("\${currency.key}")
    private val key: String? = null



    fun getExchange(from: String, to: String, amount: BigDecimal): ExchangeDto? {
        if (amount < BigDecimal.ZERO) {
            logger.error("El monto no puede ser negativo");
            throw IllegalArgumentException("El monto no puede ser negativo");
        }

        val client = OkHttpClient().newBuilder().build()
        val request = Request.Builder()
                .url("$url?to=$to&from=$from&amount=$amount")
                .addHeader("apikey", key)
                .build();

        val subscribe = Subscriber(this)
        val statistics = Statistics(currencyRepository, this)

        suscribe(subscribe)
        suscribe(statistics)

        try {
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
            notifyObservers()
            return currencyDto;
        } catch (e: Exception) {
            logger.error("Error al invocar la API", e);
            throw e;
        }

    }

    fun getPaginated(pageNumber: Int, pageSize: Int): Any {
        val pageRequest = currencyRepository.findAll(PageRequest.of(pageNumber, pageSize))
        return pageRequest;
    }

    fun getCurrencyExchagen(): List<Currency> {
        return currencyRepository.findAll().toList();
    }

    private var observers: Array<Observer> = arrayOf()

    override fun suscribe(o: Observer){
        observers += o
    }

    override fun unsuscribe(o: Observer){
        // Eliminar el observer de la lista
        //observers = observers.filter { it != o }.toTypedArray()
    }

    override fun notifyObservers(){
        // Notificar a todos los observers
        for (observer in observers) {
            observer.update()
        }
    }


}
