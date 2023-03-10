package com.example.homework_01

data class Person(val name: String, val surname: String) {
    var age: UByte = 0u

    override fun equals(other: Any?): Boolean {
        return other?.let {
            if (other is Person)
                (compare2PersonsByNameSurname(this, other) == 0) && (compare2PersonsByAge(this, other) == 0)
            else
                false
        } ?: false
    }

    override fun hashCode(): Int = (surname.uppercase().hashCode() * 31 + name.uppercase().hashCode()) + age.toInt()

    override fun toString(): String {
        return "$surname $name $age years old"
    }

    constructor (name: String, surname: String, age: UByte) : this(name, surname) {
        this.age = age
    }

}

fun compare2PersonsByNameSurname(person1: Person, person2: Person): Int
{
    val person1key: String = (person1.name + person1.surname).uppercase()
    val person2key: String = (person2.name + person2.surname).uppercase()
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
    var f = true
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
    return this.sortedWith {
            p1, p2-> compare2PersonsByNameSurname(p1, p2)
        }

}

fun List<Person>.sortedByAgeDescending(): List<Person>
{
    return this.sortedByDescending { it.age }
}

fun myHighOrderFun(action: () -> Unit): Long
{
    val startTime = System.currentTimeMillis()
    action
    return System.currentTimeMillis() - startTime
    //return measureTimeMillis{ action() }
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
        .sumOf { it + 1 }

    println("total: $total")

    // exercise 2

    val person1 = with (Person("Sergey", "Petrov"))
        {
            age = 8u
            this
        }
    val person1a = Person("Sergey", "Petrov", 5u)
    val person1b = Person("Sergey", "Petrov", 5u)

    println(person1.hashCode())
    println(person1 == person1b)
    println(person1a.hashCode())
    println(person1b.hashCode())
    println(person1a == person1b)

    val person2 = with (Person("Sergey", "Ivanov"))
    {
        age = 4u
        this
    }

    val person3 = Person("Anna", "Sidorova", 2u)

    println("mutable list")
    val mutablePersonList: MutableList<Person> = mutableListOf(person2, person1, person3, Person("Anonymous", "Anonymous", 0u))
    mutablePersonList.sortByAgeDescending()
    println(mutablePersonList)
    mutablePersonList.sortByNameSurname()
    println(mutablePersonList)


    println("list")
    val personList: List<Person> = listOf(person2, person1, person3, Person("Anonymous", "Anonymous", 0u))
    //println(personList.sortedByAgeDescending())
    println(personList.sortedByDescending { it.age })
    println(personList.sortedBy { it.name.uppercase() + it.surname.uppercase() })
    //println(personList.sortedByNameSurname())

    //println(person1 == person1a)

    // exercise 3

    val duration: Long = myHighOrderFun {
        //println("?????????????? ?????????????? ??????????????")
        println(personList.sortedByNameSurname())
    }
    println(duration)

}
