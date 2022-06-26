package mas.application

import mas.dto.request.OfferRequestDto
import mas.infrastructure.OfferRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/offers"])
class OfferController(
    val offerRepository: OfferRepository
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postDecision(@RequestBody offerRequest: OfferRequestDto) {
        println(offerRequest)
    }

}
