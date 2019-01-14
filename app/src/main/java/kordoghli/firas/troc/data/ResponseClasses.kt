package kordoghli.firas.troc.data

import java.io.Serializable

class ResponseClasses {

    data class Service(
        var id: Int, var titre: String, var description: String, var categorie: String,
        var type: String, var idUser: String, var longitude: Float, var latitude: Float
    ) : Serializable {

        var image: String = ""

        constructor(
            id: Int, titre: String, description: String, categorie: String, type: String, idUser: String,
            longitude: Float, latitude: Float, image: String
        ) :
                this(id, titre, description, categorie, type, idUser, longitude, latitude) {
            this.image = image
        }
    }

    data class Categorie(var id: Int, var categorie: String)

    data class Commentaire(
        var id: Int,
        var commentaire: String,
        var name: String,
        var idAnnonce: Int,
        var idUtilisateur: Int,
        var date: String
    )
}
