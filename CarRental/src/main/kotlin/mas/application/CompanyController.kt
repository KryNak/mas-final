package mas.application

import mas.domain.Company
import mas.infrastructure.CompanyRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/companies"])
class CompanyController(
    val companyRepository: CompanyRepository
) {

    @GetMapping()
    fun getCompanies(): ResponseEntity<List<Company>> {
        return ResponseEntity.ok(companyRepository.findAll())
    }

    @GetMapping(path = ["/{id}"])
    fun getCompany(@PathVariable(name = "id") id: String): ResponseEntity<Company> {
        return ResponseEntity.ok(companyRepository.findById(id).get())
    }

}
