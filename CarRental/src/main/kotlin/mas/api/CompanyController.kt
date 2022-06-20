package mas.api

import mas.models.Company
import mas.repositories.CompanyRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping(path = ["/api"])
class CompanyController(
    val companyRepository: CompanyRepository
) {

    @GetMapping(path = ["/companies"])
    fun getCompanies(): ResponseEntity<List<Company>> {
        return ResponseEntity.ok(companyRepository.findAll())
    }

    @GetMapping(path = ["/companies/{id}"])
    fun getCompany(@PathVariable(name = "id") id: String): ResponseEntity<Company> {
        return ResponseEntity.ok(companyRepository.findById(UUID.fromString(id)).get())
    }

}
