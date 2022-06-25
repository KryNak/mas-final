package mas.infrastructure

import mas.domain.Repair
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RepairRepository: JpaRepository<Repair, Int> {
}
