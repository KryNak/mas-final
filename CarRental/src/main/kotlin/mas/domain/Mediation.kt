package mas.domain

import java.time.LocalDate
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table
class Mediation(
    val employeeEvaluation: Int,
    val realizationTime: Int,
    val serviceDate: LocalDate
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0

    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(name = "reservation_fk", referencedColumnName = "id", nullable = false)
    private lateinit var reservation: Reservation

    fun addReservationUnidirectionally(reservation: Reservation) {
        this.reservation = reservation
    }

    fun addReservationBidirectionally(reservation: Reservation) {
        this.reservation = reservation
    }

    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(name = "employee_fk", referencedColumnName = "id", nullable = false)
    private lateinit var employee: Employee

    fun addEmployeeUnidirectionally(employee: Employee) {
        this.employee = employee
    }

    fun addEmployeeBidirectionally(employee: Employee) {
        this.employee = employee
    }

}
