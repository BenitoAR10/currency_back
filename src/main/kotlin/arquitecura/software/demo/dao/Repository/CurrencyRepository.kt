package arquitecura.software.demo.dao.Repository

import org.springframework.data.repository.PagingAndSortingRepository
import arquitecura.software.demo.dao.Currency
import org.springframework.data.jpa.repository.Query
import java.math.BigDecimal

interface CurrencyRepository : PagingAndSortingRepository<Currency, Long>{
    @Query("SELECT COUNT(*) FROM Currency")
    fun countAllCurrencies(): Long
}
