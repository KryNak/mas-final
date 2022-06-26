export {Car}

class Car {

    vin: string
    name: string
    repairAmount: number

    constructor(vin: string, name: string, repairAmount: number) {
        this.vin = vin
        this.name = name
        this.repairAmount = repairAmount
    }

}
