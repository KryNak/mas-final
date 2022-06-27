package mas.domain

import javax.persistence.*

@Entity
@Table
class OutsourcingCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0

    @OneToMany(mappedBy = "outsourcingCompany", cascade = [CascadeType.PERSIST, CascadeType.PERSIST])
    private val employees: MutableSet<Employee> = mutableSetOf()

    fun addEmployeeUnidirectionally(employee: Employee) {
        employees.add(employee)
    }

    fun addEmployeeBidirectionally(employee: Employee) {
        employee.addOutsourcingCompanyUnidirectionally(this)
        employees.add(employee)
    }

}
