package mas

import mas.domain.*
import mas.enum.CarType
import mas.enum.EventType
import mas.enum.RentalStatus
import mas.infrastructure.CarRepository
import mas.infrastructure.CompanyRepository
import mas.infrastructure.RepairRepository
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
    val carRepository: CarRepository,
    val companyRepository: CompanyRepository,
    val repairRepository: RepairRepository
): CommandLineRunner {

    override fun run(vararg args: String?) {
        val rentalCompany = RentalCompany(
            3_000_000
        )
        val company1 = Company(
            name = "Daniel & Pep",
            phoneNumber = "777 888 222",
            description = "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            image = "https://www.thestreet.com/.image/ar_16:9%2Cc_fill%2Ccs_srgb%2Cg_faces:center%2Cq_auto:good%2Cw_768/MTY3NTM5MzU5MDg3MjczODcw/business-structure-which-type-is-best-for-your-business.png",
            relations = 80
        )

        val offer1 = Offer()
        offer1.addCompanyBidirectionally(company1)

        val company2 = Company(
            name = "Pizner",
            phoneNumber = "111 222 222",
            description = "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            image = "https://www.jacobsallen.co.uk/wp-content/uploads/2017/07/Limited-Companies-Word-Cloud.png",
            relations = 56
        )

        val offer2 = Offer()
        offer2.addCompanyBidirectionally(company2)

        val company3 = Company(
            name = "Teplo",
            phoneNumber = "333 567 222",
            description = "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            image = "https://cdn.dribbble.com/users/1578582/screenshots/14442481/35l_37_34.jpg",
            relations = 34
        )

        val offer3 = Offer()
        offer3.addCompanyBidirectionally(company3)

        val car1 = Car(
            vin = "2FMDA52443BA42908",
            make = "BMW Model S",
            price = BigDecimal(325_950),
            rentalStatus = RentalStatus.AVAILABLE,
            carTypes = EnumSet.of(CarType.SPORT),
            instalmentAmount = BigDecimal("1000")
        )

        Repair(
            "some damage",
            "we have done some rest period",
            car1
        )

        car1.addRentalCompanyBidirectionally(rentalCompany)
        carRepository.save(car1)

        val car2 = Car(
            vin = "JKAZXCC134A058137",
            make = "Mercedes Punto",
            price = BigDecimal(43_000),
            rentalStatus = RentalStatus.AVAILABLE,
            carTypes = EnumSet.of(CarType.SPORT, CarType.LUXURY),
            instalmentAmount = BigDecimal("1000")
        )

        car2.addRentalCompanyBidirectionally(rentalCompany)

        Repair(
            "some damage",
            "repairDesc",
            car2
        )

        carRepository.save(car2)

        Repair(
            "some damage",
            "repairDesc",
            car2
        )

        carRepository.save(car2)

        Repair(
            "some damage",
            "repairDesc",
            car2
        )

        carRepository.save(car2)

//        val event = Event(
//            beginDate = LocalDate.now(),
//            eventDuration = Duration.of(1, ChronoUnit.DAYS).toKotlinDuration(),
//            eventDescription = "najs event ziom",
//            eventType = EventType.WEEDING
//        )
//        val reservation = Reservation(
//            dateFrom = LocalDate.now(),
//            dateTo = LocalDate.now().plusDays(6)
//        )


        companyRepository.save(company1)
        companyRepository.save(company2)
        companyRepository.save(company3)
    }

}
