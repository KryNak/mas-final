package mas.domain

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import mas.enum.RentalStatus
import mas.utils.Json
import org.hibernate.Hibernate
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "cars")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class Car(

    @Id
    val vin: String,

    val description: String? = null,

    val make: String,

    @Enumerated(value = EnumType.STRING)
    val rentalStatus: RentalStatus,

    val mileage: Int = 0,

    val price: BigDecimal,

    @ManyToMany(mappedBy = "cars", cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE], fetch = FetchType.EAGER)
    @JsonIgnoreProperties("cars")
    private val events: MutableSet<Event> = mutableSetOf(),

    @OneToMany(mappedBy = "car", cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE], fetch = FetchType.EAGER)
    @JsonIgnoreProperties("car")
    val repairs: MutableSet<Repair> = mutableSetOf(),

    @ManyToMany(mappedBy = "cars", cascade = [CascadeType.PERSIST, CascadeType.MERGE], fetch = FetchType.EAGER)
    @JsonIgnoreProperties("cars")
    val reservations: MutableSet<Reservation> = mutableSetOf()

) {

    fun addEventBidirectionally(event: Event) {
        event.addCarUnidirectionally(this)
        events.add(event)
    }

    fun addEventUnidirectionally(event: Event) {
        events.add(event)
    }

    fun addRepairUnidirectionally(repair: Repair) {
        repairs.add(repair)
    }

    fun addReservationUnidirectionally(reservation: Reservation) {
        reservations.add(reservation)
    }

    fun addReservationBidirectionally(reservation: Reservation) {
        reservation.addCarUnidirectionally(this)
        reservations.add(reservation)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Car

        return vin != null && vin == other.vin
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String {
        return Json.stringify(this)
    }

}
