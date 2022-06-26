package mas.domain

import javax.persistence.*

@Entity
@Table
class Offer(
    isConsidered: Boolean,
    cars: MutableSet<Car>
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0

    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.PERSIST])
    @JoinColumn(name = "fk_company", referencedColumnName = "id")
    private lateinit var company: Company

    fun addCompanyUnidirectionally(company: Company) {
        this.company = company
    }

    fun addCompanyBidirectionally(company: Company) {
        company.addOfferUnidirectionally(this)
        this.company = company
    }

}
