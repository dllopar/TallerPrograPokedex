package com.example.tpmultiplatformdaminallopar.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RandomData(
    val name: NameData,
    val picture: PictureData
)