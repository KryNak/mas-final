package mas.configuration

import mas.domain.Car
import mas.domain.Event
import mas.infrastructure.CarRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class MainRunner(
    val carRepository: CarRepository
): CommandLineRunner {

    override fun run(vararg args: String?) {
        println("Application started...\n")

        val car = Car("123", "helo")
        val event = Event(description = "Jakies tam balety")

        car.events += event
        carRepository.save(car)
    }

}
