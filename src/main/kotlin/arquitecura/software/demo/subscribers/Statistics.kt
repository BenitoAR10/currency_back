package arquitecura.software.demo.subscribers

import arquitecura.software.demo.bl.CurrencyBl
import arquitecura.software.demo.dao.Repository.CurrencyRepository
import arquitecura.software.demo.observer.Observer

class Statistics (private val currencyRepository: CurrencyRepository, private val currencyBl: CurrencyBl) : Observer{

   fun getMostFrequentCurrency(): List<String> {
       val exchanges = currencyBl.getCurrencyExchagen()
       val exchangeCounts = exchanges.groupingBy { "${it.currencyFrom}-${it.currencyTo}" }.eachCount()
       val mostFrequent = exchangeCounts.maxByOrNull { it.value }
       return exchangeCounts.filter { it.value == mostFrequent?.value }.keys.toList()
   }

   override fun update(){
       val totalChanges = currencyRepository.countAllCurrencies()
       println("Total de cambios: $totalChanges")
       val mostFrequent = getMostFrequentCurrency()
       println("Monedas mas frecuentes: $mostFrequent")
   }
}