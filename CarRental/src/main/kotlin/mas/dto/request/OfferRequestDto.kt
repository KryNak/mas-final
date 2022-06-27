package mas.dto.request

import mas.dto.OfferedCar

data class OfferRequestDto(

    val companyId: Int,
    val isAccepted: Boolean,
    val discount: Int = 0,
    val cars: Set<OfferedCar>,
    val refusalDescription: String? = null

)
