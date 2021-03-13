package com.abra.workout_at_home.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
class ExercisesData(
    @ColumnInfo val name: String,
    @ColumnInfo val description: String,
    @ColumnInfo val path: String,
    @ColumnInfo val type: String,
    @ColumnInfo val difficulty: String,
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString() as String,
        parcel.readString() as String,
        parcel.readString() as String,
        parcel.readString() as String,
        parcel.readString() as String
    ) {
        id = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(path)
        parcel.writeString(type)
        parcel.writeString(difficulty)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExercisesData> {
        override fun createFromParcel(parcel: Parcel): ExercisesData {
            return ExercisesData(parcel)
        }

        override fun newArray(size: Int): Array<ExercisesData?> {
            return arrayOfNulls(size)
        }
    }
}