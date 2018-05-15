package info.tkt989.day

class ValidateObject(val code: Int, val message: String)

class Validation<T> {

    var value: T
    set(value) {
        field = value
        validation = null
    }
    var validation: ValidateObject?

    constructor(value: T, validation: ValidateObject?) {
        this.value = value
        this.validation = validation
    }
}
