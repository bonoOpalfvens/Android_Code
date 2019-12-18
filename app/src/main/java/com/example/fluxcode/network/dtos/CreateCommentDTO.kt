package com.example.fluxcode.network.dtos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateCommentDTO(
    val postId: Int,
    val content: String
) : Parcelable