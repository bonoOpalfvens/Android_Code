package com.example.fluxcode.network.dtos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginDTO(
    val email: String,
    val password: String
) : Parcelable