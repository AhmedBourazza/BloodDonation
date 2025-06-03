package com.example.projetblooddonation

class Request {
    @JvmField
    var email: String? = null
    @JvmField
    var tel: String? = null
    @JvmField
    var blood_type: String? = null
    @JvmField
    var city: String? = null
    @JvmField
    var content: String? = null

    constructor(
        email: String?,
        tel: String?,
        blood_type: String?,
        city: String?,
        content: String?
    ) {
        this.email = email
        this.tel = tel
        this.blood_type = blood_type
        this.city = city
        this.content = content
    }

    constructor()
}
