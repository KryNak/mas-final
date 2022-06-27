package mas.domain

import com.vladmihalcea.hibernate.type.json.JsonType
import mas.dto.OfferedCar
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.*

@Entity
@Table
@TypeDef(name = "json", typeClass = JsonType::class)
class Offer(
    id: Int = 0,
    isConsidered: Boolean = false,
    cars: MutableSet<OfferedCar> = mutableSetOf(),
    refusalDescription: String? = null,
    discount: Int = 0
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = id

    @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.PERSIST])
    @JoinColumn(name = "fk_company", referencedColumnName = "id")
    private lateinit var company: Company

    fun addCompanyUnidirectionally(company: Company) {
        this.company = company
    }

    fun addCompanyBidirectionally(company: Company) {
        company.addOfferUnidirectionally(this)
        this.company = company
    }

    val isConsidered: Boolean = isConsidered

    @Type(type = "json")
    @Column(columnDefinition = "json", nullable = true)
    val cars: MutableSet<OfferedCar> = cars

    @Column(nullable = true)
    val refusalDescription: String? = refusalDescription

    val discount: Int = discount

}
