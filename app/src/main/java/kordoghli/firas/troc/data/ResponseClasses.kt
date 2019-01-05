package kordoghli.firas.troc.data

class ResponseClasses {

    data class Service(var id: Int, var titre: String, var description: String,
        var categorie: String, var type: String, var idUser: String)

    data class Categorie(var id: Int, var categorie: String)

    data class Commentaire(var id: Int, var commentaire: String,var name:String, var idAnnonce: Int, var idUtilisateur:Int,var date: String)
}