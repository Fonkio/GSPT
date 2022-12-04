package fr.fonkio.gspt.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tractor(
    @PrimaryKey(autoGenerate = true) var tractorId: Long = 0,
    var model: String,
    var brand: String,
    var version: String
): Parcelable {
    constructor() : this(model = "", brand = "", version = "")
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tractor

        if (tractorId != other.tractorId) return false

        return true
    }

    override fun hashCode(): Int {
        return tractorId.hashCode()
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeLong(tractorId)
        dest?.writeString(model)
        dest?.writeString(brand)
        dest?.writeString(version)
    }

    companion object CREATOR : Parcelable.Creator<Tractor> {
        override fun createFromParcel(parcel: Parcel): Tractor {
            return Tractor(parcel)
        }

        override fun newArray(size: Int): Array<Tractor?> {
            return arrayOfNulls(size)
        }
    }

}
