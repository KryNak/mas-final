package mas.models

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "company")
data class Company(

    @Id
    val id: UUID = UUID.randomUUID(),

    val name: String,

    @Column(name = "phone_number")
    val phoneNumber: String,

    val description: String,

    val image: String

)
