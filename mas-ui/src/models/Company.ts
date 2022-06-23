export{Company}

class Company {

    id: string
    name: string
    image: string
    phoneNumber: string
    description: string
    relations: number

    constructor(id: string, name: string, image: string, phoneNumber: string, description: string, relations: number) {
        this.id = id
        this.name = name
        this.image = image
        this.phoneNumber = phoneNumber
        this.description = description
        this.relations = relations
    }

}
