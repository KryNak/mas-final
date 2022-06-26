package mas.dto.request

data class OfferRequestDto(

    val companyId: Int,
    val isAccepted: Boolean,
    val discount: Int = 0,
    val cars: Set<OfferCar>,
    val refusalDescription: String = ""

) {



    data class OfferCar(val vin: String, val name: String)
}
