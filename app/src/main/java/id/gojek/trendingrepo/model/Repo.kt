package id.gojek.trendingrepo.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Repo(@SerializedName("forks")
                val forks: Int = 0,
                @SerializedName("builtBy")
                val builtBy: List<BuiltByItem>?,
                @SerializedName("author")
                val author: String = "",
                @SerializedName("name")
                val name: String = "",
                @SerializedName("description")
                val description: String = "",
                @SerializedName("avatar")
                val avatar: String = "",
                @SerializedName("stars")
                val stars: Int = 0,
                @SerializedName("url")
                val url: String = "",
                @SerializedName("language")
                val language: String = "",
                @SerializedName("languageColor")
                val languageColor: String = "",
                @SerializedName("currentPeriodStars")
                val currentPeriodStars: Int = 0,
                @SerializedName("isExpanded")
                var isExpanded: Boolean = false) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        TODO("builtBy"),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(forks)
        parcel.writeString(author)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(avatar)
        parcel.writeInt(stars)
        parcel.writeString(url)
        parcel.writeString(language)
        parcel.writeString(languageColor)
        parcel.writeInt(currentPeriodStars)
        parcel.writeByte(if (isExpanded) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Repo> {
        override fun createFromParcel(parcel: Parcel): Repo {
            return Repo(parcel)
        }

        override fun newArray(size: Int): Array<Repo?> {
            return arrayOfNulls(size)
        }
    }
}

