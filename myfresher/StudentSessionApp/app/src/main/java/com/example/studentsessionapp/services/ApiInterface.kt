package com.example.studentsessionapp.services



import com.example.studentsessionapp.models.Session
import com.example.studentsessionapp.models.Student
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface ApiInterface {
    @GET("api/sessions/")
    fun getSessionList(): Observable<List<Session>>
    @GET("api/sessions/{id}/")
    fun getSessionByID(@Path("id") id:Int):Observable<Session>

    //    @Headers("Media-Type: application/json")
    @POST("api/sessions/")
    fun addSession(@Body newSession: Session):Observable<Session>
    @GET("api/students/{username}/")
    fun getStudentByUsername(@Path("username") username:String):Observable<Student>

    @PATCH("api/students/{username}/")
    fun updateStudentSession(@Path("username") username: String, @Body student: Student):Observable<Student>
    @GET("api/students/")
    fun getStudentList():Observable<List<Student>>
    @DELETE("api/sessions/{id}/")
    fun deleteSession(@Path("id") id:Int):Completable

}