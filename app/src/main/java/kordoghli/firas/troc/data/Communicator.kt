package kordoghli.firas.troc.data

interface Communicator {
    fun etape1data(data: ResponseClasses.Service)
    fun etape2data(data: ResponseClasses.Service)
}