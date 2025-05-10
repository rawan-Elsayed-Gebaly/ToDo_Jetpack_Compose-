package com.example.data.mapper

import com.example.domain.model.User
import com.google.firebase.auth.FirebaseUser



fun FirebaseUser.toDomain():User{
    return User(
        userId = uid,
        name = displayName.orEmpty(),
        email = email.orEmpty(),
        password =  null,
        photoUrl = photoUrl.toString()
    )

}

