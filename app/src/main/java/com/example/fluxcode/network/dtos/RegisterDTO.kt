package com.example.fluxcode.network.dtos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegisterDTO(
    val email: String,
    val userName: String,
    val password: String
) : Parcelable