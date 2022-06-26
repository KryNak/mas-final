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

        val rentalCompany = RentalCompany(
            3_000_000
        )
        val company = Company(
            name = "Daniel & Pep",
            phoneNumber = "777 888 222",
            description = "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            image = "https://www.thestreet.com/.image/ar_16:9%2Cc_fill%2Ccs_srgb%2Cg_faces:center%2Cq_auto:good%2Cw_768/MTY3NTM5MzU5MDg3MjczODcw/business-structure-which-type-is-best-for-your-business.png",
            relations = 80
        )

        val car1 = Car(
            vin = "",
            make = "BMW Model S",
            price = BigDecimal(325_950),
            rentalStatus = RentalStatus.AVAILABLE,
            carTypes = EnumSet.of(CarType.SPORT),
            instalmentAmount = BigDecimal("1000")
        )

        val car2 = Car(
            vin = "",
            make = "Mercedes Punto",
            price = BigDecimal(43_000),
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

        car1.addRentalCompanyBidirectionally(rentalCompany)
        car1.addReservationBidirectionally(reservation)
        car1.addEventBidirectionally(event)
        event.addCompanyBidirectionally(company)

        Repair(
            "some damage",
            "we have done some rest period",
            car1
        )

        carRepository.save(car1)
    }

}
