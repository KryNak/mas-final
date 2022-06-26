package mas.application

import mas.domain.Car
import mas.dto.response.CarResponseDto
import mas.infrastructure.CarRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(path = ["/api/cars"])
class CarController(
    val carRepository: CarRepository
) {

    @GetMapping()
    fun getCars(): ResponseEntity<List<CarResponseDto>> {
        return ResponseEntity.ok(carRepository.findAll().map { CarResponseDto(it.vin, it.name, it.getRepairsNumber()) })
    }

    @GetMapping(path = ["/{id}"])
    fun getCar(@PathVariable id: String): ResponseEntity<Car> {
        return ResponseEntity.ok(carRepository.findById(id).get())
    }

}
