package mas.application

import mas.domain.Offer
import mas.dto.request.OfferRequestDto
import mas.infrastructure.CompanyRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.Collections

@RestController
@RequestMapping(path = ["/api/offers"])
class OfferController(
    val companyRepository: CompanyRepository
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postDecision(@RequestBody offerRequest: OfferRequestDto) {

        val company = companyRepository.findById(offerRequest.companyId).get()

        lateinit var offer: Offer
        if(offerRequest.isAccepted) {
            offer = Offer(
                id = company.offer?.id ?: -1,
                isConsidered = true,
                cars = offerRequest.cars.toMutableSet(),
                discount = offerRequest.discount
            )
        }
        else {
            offer = Offer(
                id = company.offer?.id ?: -1,
                isConsidered = true,
                cars = Collections.emptySet(),
                discount = 0,
                refusalDescription = offerRequest.refusalDescription
            )
        }

        company.addOfferBidirectionally(offer)
        companyRepository.save(company)
    }

}
