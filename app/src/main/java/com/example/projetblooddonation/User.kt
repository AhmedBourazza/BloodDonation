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

    constructor()

    constructor(
        email: String?,
        phone: String?,
        password: String?,
        city: String?,
        bloodgrp: String?
    ) {
        this.email = email
        this.phone = phone
        this.password = password
        this.city = city
        this.bloodgrp = bloodgrp
    }
}
