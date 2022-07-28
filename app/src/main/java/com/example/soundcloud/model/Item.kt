package com.example.soundcloud.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Item(
    @SerializedName("collaborative")
    @Expose
     var collaborative : Boolean,
    @SerializedName("description")
    @Expose
     var description : String,
    @SerializedName("external_urls")
    @Expose
     var external_urls : ExternalUrls,
    @SerializedName("href")
    @Expose
     var href : String,
    @SerializedName("id")
    @Expose
     var id : String,
    @SerializedName("images")
    @Expose
     var images : List<Images>,
    @SerializedName("name")
    @Expose
     var name : String,
    @SerializedName("owner")
    @Expose
     var owner : Owner,
    @SerializedName("primary_color")
    @Expose
     var primary_color : String,
    @SerializedName("public")
    @Expose
     var public : String,
    @SerializedName("snapshot_id")
    @Expose
     var snapshot_id : String,
//    @SerializedName("tracks")
//    @Expose
//     var tracks : Tracks,
    @SerializedName("type")
    @Expose
     var type : String,
    @SerializedName("uri")
    @Expose
     var uri : String


) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readString().toString(),
        TODO("external_urls"),
        parcel.readString().toString(),
        parcel.readString().toString(),
        TODO("images"),
        parcel.readString().toString(),
        TODO("owner"),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (collaborative) 1 else 0)
        parcel.writeString(description)
        parcel.writeString(href)
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(primary_color)
        parcel.writeString(public)
        parcel.writeString(snapshot_id)
        parcel.writeString(type)
        parcel.writeString(uri)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}
