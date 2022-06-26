package mas

import mas.domain.*
import mas.enum.CarType
import mas.enum.EventType
import mas.enum.RentalStatus
import mas.infrastructure.CarRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.Duration
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.time.toKotlinDuration

@Component
class MainRunner(
    val carRepository: CarRepository
): CommandLineRunner {

    override fun run(vararg args: String?) {
        println("Application started...\n")

        val company = Company(
            name = "Google",
            phoneNumber = "777 888 222",
            description = "Najs firma",
            image = "https://google.com/image1",
            relations = 80
        )
        val car = Car(
            vin = "123",
            make = "BMW",
            price = BigDecimal("300000.67"),
            rentalStatus = RentalStatus.AVAILABLE,
            carTypes = EnumSet.of(CarType.SPORT),
            instalmentAmount = BigDecimal("1000")
        )

        val event = Event(
            beginDate = LocalDate.now(),
            eventDuration = Duration.of(1, ChronoUnit.DAYS).toKotlinDuration(),
            eventDescription = "najs event ziom",
            eventType = EventType.WEEDING
        )
        val reservation = Reservation(
            dateFrom = LocalDate.now(),
            dateTo = LocalDate.now().plusDays(6)
        )


        car.addReservationBidirectionally(reservation)
        car.addEventBidirectionally(event)
        event.addCompanyBidirectionally(company)

        Repair(
            damageDescription = "dent on left side of car",
            repairDescription = "sth",
            car = car
        )

        carRepository.save(car)
        println(car)
    }

}
