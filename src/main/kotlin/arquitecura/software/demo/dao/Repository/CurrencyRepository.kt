package arquitecura.software.demo.dao.Repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import arquitecura.software.demo.dao.Currency

interface CurrencyRepository : CrudRepository<Currency, Long>