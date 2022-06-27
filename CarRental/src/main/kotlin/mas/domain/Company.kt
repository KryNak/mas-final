package mas.domain

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import mas.utils.Json
import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "companies")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class Company(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,

    val name: String,

    @Column(name = "phone_number")
    val phoneNumber: String,

    val description: String,

    val image: String,

    val relations: Int

) {

    @OneToMany(mappedBy = "company", cascade = [CascadeType.PERSIST, CascadeType.PERSIST])
    @JsonIgnoreProperties("company")
    private val events: MutableSet<Event> = mutableSetOf()

    fun addEventUnidirectionally(event: Event) {
        events.add(event)
    }

    fun addEventBidirectionally(event: Event) {
        event.addCompanyUnidirectionally(this)
        events.add(event)
    }

    @OneToOne(mappedBy = "company", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    var offer: Offer? = null
        private set

    fun addOfferUnidirectionally(offer: Offer) {
        this.offer = offer
    }

    fun addOfferBidirectionally(offer: Offer) {
        offer.addCompanyUnidirectionally(this)
        this.offer = offer
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Company

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String {
        return Json.stringify(this)
    }

}
