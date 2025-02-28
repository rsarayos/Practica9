package rayos.alejandro.practica9

class User(var firstName: String?=null,
           var lastName: String?=null,
           var age: String?=null){
    constructor() : this(null, null, null)
    override fun toString() = "$firstName\t$lastName\t$age"
}