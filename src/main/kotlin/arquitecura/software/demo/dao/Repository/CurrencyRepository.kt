package arquitecura.software.demo.dao.Repository

import org.springframework.data.repository.PagingAndSortingRepository
import arquitecura.software.demo.dao.Currency

interface CurrencyRepository : PagingAndSortingRepository<Currency, Long>