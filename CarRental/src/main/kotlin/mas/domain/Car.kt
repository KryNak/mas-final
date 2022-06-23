package mas.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "car")
data class Car(

    @Id
    val vin: String,

    val name: String

) {
}
