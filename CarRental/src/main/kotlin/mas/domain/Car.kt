package mas.domain

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import mas.enum.CarType
import mas.enum.DriveType
import mas.enum.OwningStatus
import mas.enum.RentalStatus
import mas.exception.IllegalFieldAccessException
import mas.utils.Json
import org.hibernate.Hibernate
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "cars")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class Car {

    constructor(
        vin: String,
        description: String? = null,
        make: String,
        rentalStatus: RentalStatus,
        mileage: Int = 0,
        price: BigDecimal,
        carTypes: Set<CarType>
    ) {
        this.vin = vin
        this.description = description
        this.name = make
        this.rentalStatus = rentalStatus
        this.mileage = mileage
        this.price = price
        this.carTypes = carTypes
        this.owningStatus = OwningStatus.OWN
        this.instalmentAmount = BigDecimal.ZERO
    }

    constructor(
        vin: String,
        description: String? = null,
        make: String,
        rentalStatus: RentalStatus,
        mileage: Int = 0,
        price: BigDecimal,
        carTypes: Set<CarType>,
        instalmentAmount: BigDecimal
    ) {
        this.vin = vin
        this.description = description
        this.name = make
        this.rentalStatus = rentalStatus
        this.mileage = mileage
        this.price = price
        this.carTypes = carTypes
        this.owningStatus = OwningStatus.LEASING
        this.instalmentAmount = instalmentAmount
    }

    @Id
    val vin: String

    val description: String?

    val name: String

    @Enumerated(value = EnumType.STRING)
    val rentalStatus: RentalStatus

    val mileage: Int

    val price: BigDecimal

    @Enumerated(EnumType.STRING)
    private var owningStatus: OwningStatus


    private var instalmentAmount: BigDecimal

    @JsonIgnore
    fun getInstalmentAmountProp(): BigDecimal {
        if (owningStatus != OwningStatus.LEASING) {
            throw IllegalFieldAccessException()
        }

        return instalmentAmount
    }

    fun setInstalmentAmountProp(value: BigDecimal)
    {
        if (owningStatus != OwningStatus.LEASING) {
            throw IllegalFieldAccessException()
        }

        instalmentAmount = value
    }

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "car_types", joinColumns = [JoinColumn(name = "fk_car", referencedColumnName = "vin")])
    @Column(name = "car_type")
    val carTypes: Set<CarType>

    @Column(nullable = true, name = "time_to_100")
    private var timeTo100: Int? = null

    @JsonIgnore
    fun getTimeTo100Prop(): Int? {
        if (!carTypes.contains(CarType.SPORT)) {
            throw IllegalFieldAccessException()
        }

        return timeTo100;
    }

    fun setTimeTo100Prop(value: Int?) {
        if (!carTypes.contains(CarType.SPORT)) {
            throw IllegalFieldAccessException()
        }

        timeTo100 = value
    }

    @Column(nullable = true)
    private var carBootSize: Int? = null

    @JsonIgnore
    fun getCarBootSizeProp(): Int? {
        if (!carTypes.contains(CarType.LUXURY)) {
            throw IllegalFieldAccessException()
        }

        return carBootSize;
    }

    fun setCarBootSizeProp(value: Int?) {
        if (!carTypes.contains(CarType.LUXURY)) {
            throw IllegalFieldAccessException()
        }

        carBootSize = value
    }

    @Column(nullable = true)
    private var tvNumber: Int? = null

    @JsonIgnore
    fun getTvNumberProp(): Int? {
        if (!carTypes.contains(CarType.LUXURY)) {
            throw IllegalFieldAccessException()
        }

        return tvNumber;
    }

    fun setTvNumberProp(value: Int?) {
        if (!carTypes.contains(CarType.LUXURY)) {
            throw IllegalFieldAccessException()
        }

        tvNumber = value
    }

    @Column(nullable = true)
    private var weight: Int? = null

    @JsonIgnore
    fun getWeightProp(): Int? {
        if (!carTypes.contains(CarType.OFF_ROAD)) {
            throw IllegalFieldAccessException()
        }

        return weight;
    }

    fun setWeightProp(value: Int?) {
        if (!carTypes.contains(CarType.OFF_ROAD)) {
            throw IllegalFieldAccessException()
        }

        weight = value
    }

    @Column(nullable = true)
    private var height: Int? = null

    @JsonIgnore
    fun getHeightProp(): Int? {
        if (!carTypes.contains(CarType.OFF_ROAD)) {
            throw IllegalFieldAccessException()
        }

        return height;
    }

    fun setHeightProp(value: Int?) {
        if (!carTypes.contains(CarType.OFF_ROAD)) {
            throw IllegalFieldAccessException()
        }

        height = value
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private var driveType: DriveType? = null

    @JsonIgnore
    fun getDriveTypeProp(): DriveType? {
        if (!carTypes.contains(CarType.OFF_ROAD)) {
            throw IllegalFieldAccessException()
        }

        return driveType;
    }

    fun setDriveTypeProp(value: DriveType?) {
        if (!carTypes.contains(CarType.OFF_ROAD)) {
            throw IllegalFieldAccessException()
        }

        driveType = value
    }

    @ManyToMany(
        mappedBy = "cars",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE],
        fetch = FetchType.EAGER
    )
    @JsonIgnoreProperties("cars")
    private val events: MutableSet<Event> = mutableSetOf()

    fun addEventBidirectionally(event: Event) {
        event.addCarUnidirectionally(this)
        events.add(event)
    }

    fun addEventUnidirectionally(event: Event) {
        events.add(event)
    }

    @OneToMany(
        mappedBy = "car",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE],
        fetch = FetchType.EAGER
    )
    @JsonIgnoreProperties("car")
    private val repairs: MutableSet<Repair> = mutableSetOf()

    fun addRepairUnidirectionally(repair: Repair) {
        repairs.add(repair)
    }

    fun getRepairsNumber(): Int {
        return repairs.count()
    }

    @ManyToMany(mappedBy = "cars", cascade = [CascadeType.PERSIST, CascadeType.MERGE], fetch = FetchType.EAGER)
    @JsonIgnoreProperties("cars")
    private val reservations: MutableSet<Reservation> = TreeSet { res1, res2 -> res1.dateFrom.compareTo(res2.dateTo) }

    fun addReservationUnidirectionally(reservation: Reservation) {
        reservations.add(reservation)
    }

    fun addReservationBidirectionally(reservation: Reservation) {
        reservation.addCarUnidirectionally(this)
        reservations.add(reservation)
    }

    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(name = "fk_rental_company", referencedColumnName = "id", nullable = false)
    private lateinit var rentalCompany: RentalCompany

    fun addRentalCompanyUnidirectionally(rentalCompany: RentalCompany) {
        this.rentalCompany = rentalCompany
    }

    fun addRentalCompanyBidirectionally(rentalCompany: RentalCompany) {
        rentalCompany.addCarUnidirectionally(this)
        this.rentalCompany = rentalCompany
    }

    fun changeOwningStatusToOwn() {
        this.owningStatus = OwningStatus.OWN
        this.instalmentAmount = BigDecimal.ZERO
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Car

        return vin != null && vin == other.vin
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String {
        return Json.stringify(this)
    }


}
