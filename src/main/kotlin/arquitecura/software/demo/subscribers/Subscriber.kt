package arquitecura.software.demo.subscribers

import arquitecura.software.demo.observer.Observable
import arquitecura.software.demo.observer.Observer

class Subscriber(private val observable: Observable) : Observer {

    override fun update() {
        println("Se ha actualizado el observable")
    }
}
