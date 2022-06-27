package mas.domain

import com.vladmihalcea.hibernate.type.array.ListArrayType
import mas.enum.EmployeeType
import mas.exception.IllegalFieldAccessException
import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.*

@Entity
@Table
@TypeDef(name = "array", typeClass = ListArrayType::class)
class Employee(
    name: String,
    phoneNumber: String,
    employmentInfo: String,
    employeeType: EmployeeType
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0

    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.PERSIST])
    @JoinColumn(name = "fk_rental_company", referencedColumnName = "id", nullable = true)
    private var rentalCompany: RentalCompany? = null

    fun addRentalCompanyUnidirectionally(rentalCompany: RentalCompany) {
        if(outsourcingCompany == null) {
            this.rentalCompany = rentalCompany
        }
    }

    fun addRentalCompanyBidirectionally(rentalCompany: RentalCompany) {
        if(outsourcingCompany == null) {
            rentalCompany.addEmployeeUnidirectionally(this)
            this.rentalCompany = rentalCompany
        }
    }

    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.PERSIST])
    @JoinColumn(name = "fk_outsourcing_company", referencedColumnName = "id", nullable = true)
    private var outsourcingCompany: OutsourcingCompany? = null

    fun addOutsourcingCompanyUnidirectionally(outsourcingCompany: OutsourcingCompany) {
        if(rentalCompany == null) {
            this.outsourcingCompany = outsourcingCompany
        }
    }

    fun addOutsourcingCompanyBidirectionally(outsourcingCompany: OutsourcingCompany) {
        if(rentalCompany == null) {
            outsourcingCompany.addEmployeeUnidirectionally(this)
            this.outsourcingCompany = outsourcingCompany
        }
    }

    val name: String = name
    val phoneNumber: String = phoneNumber
    val employmentInfo: String = employmentInfo

    @Enumerated(EnumType.STRING)
    val employeeType: EmployeeType = employeeType

    @Type(type = "array")
    @Column(columnDefinition = "text[]")
    private val qualifications: MutableList<String> = mutableListOf()

    fun getQualificationsProp(): List<String> {
        if(employeeType != EmployeeType.QUALIFIED_EMPLOYEE) {
            throw IllegalFieldAccessException()
        }

        return qualifications.toList()
    }

    fun addQualification(qualification: String) {
        if(employeeType != EmployeeType.QUALIFIED_EMPLOYEE) {
            throw IllegalFieldAccessException()
        }

        qualifications.add(qualification)
    }

    val bonus: Int = when(employeeType) {
        EmployeeType.UNQUALIFIED_EMPLOYEE -> 0
        EmployeeType.QUALIFIED_EMPLOYEE -> 500
        EmployeeType.DIRECTOR -> 1000
    }

    @OneToMany(mappedBy = "employee")
    val mediations: MutableSet<Mediation> = mutableSetOf()

    fun addMediationUnidirectionally(mediation: Mediation) {
        mediations.add(mediation)
    }

    fun addMediationBidirectionally(mediation: Mediation) {
        mediation.addEmployeeUnidirectionally(this)
        mediations.add(mediation)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Employee

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    companion object {
        val salaryBase: Int = 2_500
    }

}
