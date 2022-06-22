export{Company}

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
