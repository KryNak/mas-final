package mas.infrastructure

import mas.domain.Car
import org.springframework.data.jpa.repository.JpaRepository

interface CarRepository: JpaRepository<Car, String> {
}
