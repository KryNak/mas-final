package mas.repositories

import mas.models.Car
import org.springframework.data.jpa.repository.JpaRepository

interface CarRepository: JpaRepository<Car, String> {
}
