package mas.domain

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import mas.enum.EventType
import mas.utils.Json
import org.hibernate.Hibernate
import java.time.LocalDate
import javax.persistence.*
import kotlin.time.Duration

@Entity
@Table(name = "events")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class Event(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,

    val beginDate: LocalDate,

    val eventDuration: Duration,

    val eventDescription: String,

    val eventType: EventType,

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "event_car",
        joinColumns = [JoinColumn(name = "id_event", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "vin_car", referencedColumnName = "vin")]
    )
    @JsonIgnoreProperties("events")
    private val cars: MutableSet<Car> = mutableSetOf(),

) {

    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(name = "fk_company", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties("events")
    private lateinit var company: Company

    fun addCarBidirectionally(car: Car) {
        car.addEventUnidirectionally(this)
        cars.add(car)
    }

    fun addCarUnidirectionally(car: Car) {
        cars.add(car)
    }

    fun addCompanyUnidirectionally(company: Company) {
        this.company = company
    }

    fun addCompanyBidirectionally(company: Company) {
        company.addEventUnidirectionally(this)
        this.company = company
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Event

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String {
        return Json.stringify(this)
    }

}
