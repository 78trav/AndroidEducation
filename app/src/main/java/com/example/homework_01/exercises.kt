package com.example.homework_01

import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis


data class Person(val name: String, val surname: String) {
    var age: UInt = 0.toUInt()

    override fun equals(other: Any?): Boolean {
        return other?.let {
            if (other is Person)
                (compare2PersonsByNameSurname(this, other) == 0) && (compare2PersonsByAge(this, other) == 0)
            else
                false
        } ?: false
    }

    override fun hashCode(): Int {
        return super.hashCode() + age.toInt()
    }

    override fun toString(): String {
        return "$surname $name $age years old"
    }

    constructor (name: String, surname: String, age: UInt) : this(name, surname) {
        this.age = age
    }

}

fun compare2PersonsByNameSurname(person1: Person, person2: Person): Int
{
    val person1key: String = person1.name.uppercase() + person1.surname.uppercase()
    val person2key: String = person2.name.uppercase() + person2.surname.uppercase()
    return when
    {
        person1key == person2key -> 0
        person1key > person2key -> 1
        else -> -1
    }
}

fun compare2PersonsByAge(person1: Person, person2: Person): Int
{
    return when
    {
        person1.age == person2.age -> 0
        person1.age > person2.age -> 1
        else -> -1
    }
}

fun MutableList<Person>.sortByMethod(compareFunc: (Person, Person) -> Int, desc: Boolean = false)
{
    var f: Boolean = true
    while (f)
    {
        f = false
        for (i in 0..(this.count() - 2))
        {
            if (compareFunc(this[i], this[i + 1]) == if (desc) -1 else 1)
            {
                val p = this[i]
                this[i] = this[i + 1]
                this[i + 1] = p
                f = true
            }
        }
    }

}

fun MutableList<Person>.sortByAgeDescending()
{
    this.sortByMethod(::compare2PersonsByAge,true)
}

fun MutableList<Person>.sortByNameSurname()
{
    this.sortByMethod(::compare2PersonsByNameSurname)
}

fun List<Person>.sortedByNameSurname(): List<Person>
{
    return this.sortedWith(
        Comparator<Person>
        {
            p1, p2-> compare2PersonsByNameSurname(p1, p2)
        }
    )
}

fun List<Person>.sortedByAgeDescending(): List<Person>
{
    return this.sortedByDescending { it.age }
}

fun myHighOrderFun(action: () -> Unit): Long
{
    return measureTimeMillis{ action() }
    //return measureNanoTime{ action() }
}

fun main() {

    // exercise 1
    val myList: MutableList<Int> = mutableListOf()

    println("fill my list")

    for (i in 0..99) {
        myList.add(i)
        println("$i: ${myList[i]}")
    }

    println("work with my list")

    val total: Int = myList
        .filter { it % 2 == 0 }
        .slice(10..20)
        .map { it + 1 }
        .sum()

    println("total: $total")

    // exercise 2

    val person1: Person = with (Person("Sergey", "Petrov"))
        {
            age = 8u
            this
        }
    val person1a: Person = Person("Sergey", "Petrov", 5u)

    val person2: Person = with (Person("Sergey", "Ivanov"))
    {
        age = 4u
        this
    }

    val person3: Person = Person("Anna", "Sidorova", 2u)

    println("mutable list")
    val mutablePersonList: MutableList<Person> = mutableListOf(person2, person1, person3, Person("Anonymous", "Anonymous", 0u))
    mutablePersonList.sortByAgeDescending()
    println(mutablePersonList)
    mutablePersonList.sortByNameSurname()
    println(mutablePersonList)


    println("list")
    val personList: List<Person> = listOf(person2, person1, person3, Person("Anonymous", "Anonymous", 0u))
    println(personList.sortedByAgeDescending())
    println(personList.sortedByNameSurname())

    //println(person1 == person1a)

    // exercise 3

    var duration: Long = myHighOrderFun {
        println("Функция высшего порядка")
        println(personList.sortedByNameSurname())
    }
    println(duration)

}
