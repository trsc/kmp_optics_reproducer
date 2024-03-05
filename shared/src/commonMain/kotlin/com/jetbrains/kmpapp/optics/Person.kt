package com.jetbrains.kmpapp.optics

import arrow.optics.optics

@optics
data class Address(val street: String, val number: Int, val city: String) {
    companion object
}

@optics
data class Person(val name: String, val age: Int, val address: Address) {
    companion object
}

class PersonService {
    fun updateStreet(person: Person, street: String): Person {
        return Person.address.street.set(person, street)
    }
}
