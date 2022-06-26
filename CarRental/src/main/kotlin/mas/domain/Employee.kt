package mas.domain

import javax.persistence.*

@Entity
@Table
class Employee() {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0

    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.PERSIST])
    @JoinColumn(name = "fk_rental_company", referencedColumnName = "id", nullable = false)
    private lateinit var rentalCompany: RentalCompany

    fun addRentalCompanyUnidirectionally(rentalCompany: RentalCompany) {
        this.rentalCompany = rentalCompany
    }

    fun addRentalCompanyBidirectionally(rentalCompany: RentalCompany) {
        rentalCompany.addEmployeeUnidirectionally(this)
        this.rentalCompany = rentalCompany
    }

}
