package com.example.cryptos.data.api.model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Article(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
    val source: @RawValue Source?
): Parcelable {
    val formattedAuthor
    get() = "creado por $author"
}