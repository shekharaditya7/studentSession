package com.example.studentsessionapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Session(
    @SerializedName("id")
    var id : Int=0,
    @SerializedName("title")
    var title: String?=null,
    @SerializedName("description")
    var description: String?=null,
    @SerializedName("session_date")
    var session_date: String?=null,
    @SerializedName("slots")
    var slots: String?=null,
    @SerializedName("student_set")
    var students: List<Student>?=null
): Parcelable