package mas.domain

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import mas.utils.Json
import org.hibernate.Hibernate
import java.time.LocalDate
import javax.annotation.PostConstruct
import javax.persistence.*

@Entity
@Table(name = "repairs")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class Repair (
    damageDescription: String,
    repairDescription: String,
    car: Car
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0

    val damageDescription: String = damageDescription
    val repairDescription: String = repairDescription

    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(name = "vin_fk", referencedColumnName = "vin", nullable = false)
    @JsonIgnoreProperties("repairs")
    private val car: Car = car

    var createdAt: LocalDate = LocalDate.now()

    init {
        car.addRepairUnidirectionally(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Repair

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String {
        return Json.stringify(this)
    }

}
