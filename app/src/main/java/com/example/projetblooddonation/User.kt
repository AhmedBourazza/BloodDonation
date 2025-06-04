package com.example.projetblooddonation


class User {
    @JvmField
    var email: String? = null
    @JvmField
    var phone: String? = null
    var password: String? = null
    @JvmField
    var city: String? = null
    @JvmField
    var bloodgrp: String? = null
    @JvmField
    var name: String? = null
    @JvmField
    var imageBase64: String? = null


    constructor()

    constructor(
        email: String?,
        phone: String?,
        password: String?,
        city: String?,
        bloodgrp: String?,
        name: String?,
        imageBase64: String?
    ) {
        this.email = email
        this.phone = phone
        this.password = password
        this.city = city
        this.bloodgrp = bloodgrp
        this.name = name
        this.imageBase64 = imageBase64

    }
}
