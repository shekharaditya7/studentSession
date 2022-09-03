package com.example.studentsessionapp.services

import com.example.studentsessionapp.models.Session
import com.example.studentsessionapp.models.Student
import io.reactivex.Completable
import io.reactivex.Observable

class ApiClient {
    val client: ApiInterface
    init {
        client = ServiceBuilder.buildService(ApiInterface::class.java)
    }
    fun loadSessionData(): Observable<List<Session>> {
        return client.getSessionList()
    }
    fun loadSessionDataByID(id:Int): Observable<Session> {
        return client.getSessionByID(id)
    }
    fun addNewSession(session : Session) : Observable<Session> {
        return client.addSession(session)
    }
    fun loadStudentDataByUsername(username:String): Observable<Student> {
        return client.getStudentByUsername(username)
    }

    fun updateStudentSessionList(username:String ,student: Student): Observable<Student> {
        return client.updateStudentSession(username, student)
    }
    fun loadStudentListData():Observable<List<Student>>{
        return client.getStudentList()
    }
    fun deleteSessionById(id :Int):Completable{
        return client.deleteSession(id)
    }
}