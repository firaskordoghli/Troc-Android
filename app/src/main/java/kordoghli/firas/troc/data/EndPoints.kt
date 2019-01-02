package kordoghli.firas.troc.data

object EndPoints {
    //private val URL_ROOT = "http://10.0.2.2:3000/"
    private val URL_ROOT = "http://192.168.1.6:3000/"
    val URL_CREATE_ACCOUNT = URL_ROOT + "signup"
    val URL_UPDATE_ACCOUNT = URL_ROOT + "updateUser"
    val URL_LOGIN = URL_ROOT + "login"
    val URL_TROQUER = URL_ROOT + "addService"
    val URL_GET_SERVICE = URL_ROOT + "getService"
    val URL_GET_SERVICE_WITH_ID = URL_ROOT + "getServiceWithId/"
    val URL_GET_User_WITH_ID = URL_ROOT + "getUserWithId/"
    val URL_GET_All_SERVICE_USER = URL_ROOT + "getAllServiceForUser/"
    val URL_DELETE_SERVICE = URL_ROOT + "delServiceWithId/"
}