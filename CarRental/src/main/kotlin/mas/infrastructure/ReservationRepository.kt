package mas.infrastructure

import mas.domain.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepository:JpaRepository<Reservation, Int> {
}
