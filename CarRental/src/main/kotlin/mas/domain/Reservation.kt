package mas.domain

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import mas.utils.Json
import org.hibernate.Hibernate
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.persistence.*
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.validation.constraints.Size

@Entity
@Table(name = "reservations")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class Reservation(

    dateFrom: LocalDate,
    dateTo: LocalDate

) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0

    @Column(columnDefinition = "DATE")
    val dateFrom: LocalDate = dateFrom

    @Column(columnDefinition = "DATE")
    val dateTo: LocalDate = dateTo

    @Transient
    var duration: Long = Duration.between(dateFrom.atStartOfDay(), dateTo.atStartOfDay()).toDays()

    @PostLoad
    private fun setDuration() {
        duration = Duration.between(dateFrom.atStartOfDay(), dateTo.atStartOfDay()).toDays()
    }

    @ManyToMany(cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    @JoinTable(
        name = "reservation_car",
        joinColumns = [JoinColumn(name = "reservation_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "car_vin", referencedColumnName = "vin")]
    )
    @JsonIgnoreProperties("reservations")
    private val cars: MutableSet<Car> = mutableSetOf()

    @OneToMany(mappedBy = "reservation")
    @Size(max = 3)
    private val mediations: MutableSet<Mediation> = mutableSetOf()

    fun addMediationUnidirectionally(mediation: Mediation) {
        mediations.add(mediation)
    }

    fun addMediationBidirectionally(mediation: Mediation) {
        mediation.addReservationUnidirectionally(this)
        mediations.add(mediation)
    }

    fun addCarUnidirectionally(car: Car) {
        cars.add(car)
    }

    fun addCarBidirectionally(car: Car) {
        car.addReservationUnidirectionally(this)
        cars.add(car)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Reservation

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String {
        return Json.stringify(this)
    }

}
