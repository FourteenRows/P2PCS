package com.fourteenrows.p2pcs.model

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.SignInMethodQueryResult
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.*

object ModelFirebase {

    fun addUserData(uid: String?, name: String, surname: String, email: String): Task<Void> {
        val user = User(name, surname, email, 0, 0, 0)
        return FirebaseFirestore.getInstance().collection("User").document("$uid").set(user)
    }

    fun authenticateUser(email: String, pwd: String): Task<AuthResult> {
        return FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pwd)
    }

    fun buildUser(it: DocumentSnapshot): User {
        return User(
            it.data!!["name"]!!.toString(),
            it.data!!["surname"]!!.toString(),
            it.data!!["mail"]!!.toString(),
            it.data!!["exp"] as Long,
            it.data!!["gaia_coins"] as Long,
            it.data!!["week_points"] as Long
        )
    }

    fun checkNewEmail(email: String): Task<SignInMethodQueryResult> {
        return FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email)
    }

    fun checkNewPlate(plate: String): Task<QuerySnapshot> {
        return FirebaseFirestore.getInstance().collection("Cars")
            .whereEqualTo("plate", plate)
            .get()
    }

    fun delete(collection: String, document: String): Task<Void> {
        return FirebaseFirestore.getInstance().collection(collection).document(document)
            .delete()
    }

    fun editVehicle(car: FetchedVehicle) {
        val toInsert =
            Vehicle(car.final_availability, car.model, car.owner, car.plate, car.seats, car.instant_availability)
        FirebaseFirestore.getInstance().collection("Cars").document(car.cid).set(toInsert)
    }

    fun fetchBusyVehicleOf(date: Long, timeSlot: String): Task<QuerySnapshot> {
        return FirebaseFirestore.getInstance().collection("Dates")
            .whereEqualTo("start_slot", ModelDates.setSlotToDate(Date(date), timeSlot))
            .get()
    }

    fun fetchAvailableVehicles(date: Date): Task<QuerySnapshot> {
        return FirebaseFirestore.getInstance().collection("Cars")
            .whereEqualTo("instant_availability", true)
            .whereGreaterThan("final_availability", date)
            .get()
    }

    fun getCarReservations(carId: String): Task<QuerySnapshot> {
        return FirebaseFirestore.getInstance().collection("Dates")
            .whereEqualTo("car", carId)
            //.whereGreaterThanOrEqualTo("start_slot", 0L)
            .get()
    }

    fun getReservation(rid: String): Task<DocumentSnapshot> {
        return FirebaseFirestore.getInstance().collection("Dates")
            .document(rid)
            .get()
    }

    fun getUid(): String? {
        return FirebaseAuth.getInstance().uid
    }

    fun getUserDocument(): Task<DocumentSnapshot> {
        return FirebaseFirestore.getInstance().collection("User").document(getUid()!!).get()
    }

    fun getUserVechicles(): Task<QuerySnapshot> {
        return FirebaseFirestore.getInstance().collection("Cars").whereEqualTo("owner", getUid()).get()
    }

    fun insertReservation(reservation: Reservation): Task<Void> {
        return FirebaseFirestore.getInstance().collection("Dates").document().set(reservation)
    }

    fun insertVehicle(car: Vehicle) {
        FirebaseFirestore.getInstance().collection("Cars").document().set(car)
    }

    fun insertUser(email: String, pwd: String): Task<AuthResult> {
        return FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pwd)
    }

    fun isEmailVerified(): Boolean {
        return FirebaseAuth.getInstance().currentUser!!.isEmailVerified
    }

    fun sendResetEmail(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
    }

    fun sendResetEmailKnown(): Task<Void> {
        val email = FirebaseAuth.getInstance().currentUser!!.email!!
        return FirebaseAuth.getInstance().sendPasswordResetEmail(email)
    }

    fun sendVerificationEmail() {
        FirebaseAuth.getInstance().currentUser!!.sendEmailVerification()
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

    fun updateField(field: String, value: String): Task<Void> {
        return FirebaseFirestore.getInstance().collection("User").document(getUid()!!).update(field, value)
    }

}