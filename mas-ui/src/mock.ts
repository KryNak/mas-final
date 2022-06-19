import {faker} from "@faker-js/faker";
export {Car, Database, Company}

class Car {

    vin: string
    make: string
    mileage: number

    constructor(vin: string, make: string, mileage: number) {
        this.vin = vin
        this.make = make
        this.mileage = mileage
    }

}

class Company {

    id: string
    name: string
    image: string
    phoneNumber: string
    description: string

    constructor(id: string, name: string, image: string, phoneNumber: string, description: string) {
        this.id = id
        this.name = name
        this.image = image
        this.phoneNumber = phoneNumber
        this.description = description
    }

}

class Database {

    static setup(): void {
        Array.from({length: 100}).forEach(() => {
            Database.companies.push(
                new Company(
                    faker.datatype.uuid(),
                    faker.company.companyName(),
                    faker.image.business(400, 400, true),
                    faker.phone.phoneNumber(),
                    faker.commerce.productDescription()
                )
            )

            Database.cars.push(
                new Car(faker.vehicle.vin(), faker.vehicle.model(), 1)
            )
        })
    }

    static cars: Car[] = []
    static companies: Company[] = []


}
