package mas.domain

import javax.persistence.*

@Entity
@Table
class RentalCompany(capital: Int) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0

    val capital: Int = capital

    @OneToMany(mappedBy = "rentalCompany", cascade = [CascadeType.PERSIST, CascadeType.PERSIST])
    private val cars: MutableSet<Car> = mutableSetOf()

    fun addCarUnidirectionally(car: Car){
        cars.add(car)
    }

    fun addCarBidirectionally(car: Car) {
        car.addRentalCompanyUnidirectionally(this)
        cars.add(car)
    }


    @OneToMany(mappedBy = "rentalCompany", cascade = [CascadeType.PERSIST, CascadeType.PERSIST])
    private val employees: MutableSet<Employee> = mutableSetOf()

    fun addEmployeeUnidirectionally(employee: Employee) {
        employees.add(employee)
    }

    fun addEmployeeBidirectionally(employee: Employee) {
        employee.addRentalCompanyUnidirectionally(this)
        employees.add(employee)
    }

}
