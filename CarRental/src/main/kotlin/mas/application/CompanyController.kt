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
    fun getCompaniesWithActiveOffer(): ResponseEntity<List<Company>> {
        return ResponseEntity.ok(companyRepository.findAll().filter { (it.offer?.isConsidered?.not()) ?: false })
    }

    @GetMapping(path = ["/{id}"])
    fun getCompany(@PathVariable(name = "id") id: Int): ResponseEntity<Company> {
        return ResponseEntity.ok(companyRepository.findById(id).get())
    }

}
