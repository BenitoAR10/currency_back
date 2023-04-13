package arquitecura.software.demo.observer

interface Observable {
    // Pasamos el observador como parametro para que el observador tenga acceso a los datos del observable
    fun suscribe(o: Observer)
    fun unsuscribe(o: Observer)
    fun notifyObservers()
}