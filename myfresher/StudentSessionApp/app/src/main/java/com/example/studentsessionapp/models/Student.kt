package com.example.studentsessionapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Student(
    @SerializedName("username")
    var username:String?=null,
    @SerializedName("name")
    var name:String?=null,
    @SerializedName("isadmin")
    var isadmin:Boolean,
    @SerializedName("sessions")
    var student_session_list:List<Int>
): Parcelable