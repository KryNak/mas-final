package mas.infrastructure

import mas.domain.Offer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OfferRepository: JpaRepository<Offer, Int> {
}
