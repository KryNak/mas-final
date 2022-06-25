package mas.domain

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import mas.utils.Json
import org.hibernate.Hibernate
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "reservations")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class Reservation(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,

    @Column(columnDefinition = "DATE")
    val dateFrom: LocalDate,

    @Column(columnDefinition = "DATE")
    val dateTo: LocalDate

) {

    @ManyToMany(cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    @JoinTable(
        name = "reservation_car",
        joinColumns = [JoinColumn(name = "reservation_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "car_vin", referencedColumnName = "vin")]
    )
    @JsonIgnoreProperties("reservations")
    private val cars: MutableSet<Car> = mutableSetOf()


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
