package kordoghli.firas.troc.data

class ResponseClasses {

    data class Service (val id: Int, val titre: String, var description: String,
                        val categorie: String, var type: String, var idUser: String)
}