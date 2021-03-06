import {Car} from './Car'
export {Offer}

class Offer {
    companyId: number
    isAccepted: boolean
    discount: number
    cars: Car[]
    refusalDescription: string | null

    constructor(companyId: number, isAccepted: boolean, discount: number, cars: Car[], refusalDescription: string | null) {
        this.companyId = companyId
        this.isAccepted = isAccepted
        this.discount = discount
        this.cars = cars
        this.refusalDescription = refusalDescription
    }

}
