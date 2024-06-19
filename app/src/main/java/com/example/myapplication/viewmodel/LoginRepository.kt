package com.example.myapplication.viewmodel

import android.util.Log
import com.example.myapplication.DB.Nutrient
import com.example.myapplication.DB.Person
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LoginRepository(private val db: FirebaseFirestore) {
    val date: LocalDate = LocalDate.now()
    fun checkLogin(userId: String, password: String): Flow<Person?> = flow {
        try {
            val dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
            val userRef = db.collection("users").document("ZXrdvGfY3ZLtJixdUBqD").collection(userId).document(userId)
            val intakeRef = userRef.collection("intake").document(dateStr)

            val userSnapshot = userRef.get().await()
            val intakeSnapshot = intakeRef.get().await()

            val person = userSnapshot.toObject(Person::class.java)
            val intakeData = intakeSnapshot.data as? Map<String, Map<String, Any>>

            // Convert Map<String, Any> to Map<String, Nutrient>
            person?.intake = intakeData?.mapValues { (_, value) ->
                Nutrient(
                carbohydrate = value["carbohydrate"] as? Double ?: 0.0,
                protein = value["protein"] as? Double ?: 0.0,
                fat = value["fat"] as? Double ?: 0.0
                )
            } ?: mapOf()

            if (person != null && person.pw == password) {
                Log.d("로그인", "성공")
                emit(person)  // Emit Person object if password matches
            } else {
                Log.d("로그인", "실패")
                emit(null)  // Emit null if password does not match or person is null
            }
        } catch (e: Exception) {
            Log.d("로그인", "$e")
            emit(null)  // Emit null on exception
        }
    }

//    fun addPersonIntake(intake: Map<String, Nutrient>) {
//        val dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
//        val intakeRef = db.collection("users").document("ZXrdvGfY3ZLtJixdUBqD").collection("test").document("test").collection("intake").document(dateStr)
//        intakeRef.set(intake)
//    }
//
//    fun updatePersonNutrient(kcal: Int, nutrient: Nutrient) {
//        val userRef = db.collection("users").document("ZXrdvGfY3ZLtJixdUBqD").collection("test").document("test")
//        userRef.update(
//            mapOf(
//                "kcal" to kcal,
//                "carbohydrate" to nutrient.carbohydrate,
//                "protein" to nutrient.protein,
//                "fat" to nutrient.fat
//            )
//        )
//    }

    fun checkIdDuplication(userId: String): Flow<Boolean> = flow {
        try {
            val userRef = db.collection("users").document("ZXrdvGfY3ZLtJixdUBqD").collection(userId).document(userId)
            val snapshot = userRef.get().await()

            // Emit true if the document exists (i.e., the userId is duplicated)
            emit(snapshot.exists())
        } catch (e: Exception) {
            Log.d("CheckIdDuplication", "Error checking ID 중복: $e")
            emit(false)  // Emit false on exception, assuming non-existence if there's an error
        }
    }
    fun signUp(name: String, id: String, pw: String): Flow<Boolean> = flow {
        try {
            // Create a reference to the user's document based on the provided ID
            val userCollectionRef = db.collection("users").document("ZXrdvGfY3ZLtJixdUBqD").collection(id)
            val userDocRef = userCollectionRef.document(id)

            // Check if user already exists to prevent duplication
            val snapshot = userDocRef.get().await()
            if (snapshot.exists()) {
                Log.d("SignUp", "실패 id = $id")
                emit(false) // User already exists, do not proceed with creation
                return@flow
            }

            // Create a new Person object with default values for nutritional information
            val person = Person(
                id = id,
                pw = pw,
                name = name,
                age = 0, // Default age if not provided
                height = 0.0, // Default height if not provided
                weight = 0.0, // Default weight if not provided
                kcal = 0, // Default kcal if not provided
                carbohydrate = 0.0, // Default carbohydrate if not provided
                protein = 0.0, // Default protein if not provided
                fat = 0.0, // Default fat if not provided
                intake = mapOf() // Default intake map
            )

            userDocRef.set(person).await()
            Log.d("SignUp", "성공")
            emit(true) // Successfully created new user
        } catch (e: Exception) {
            Log.d("SignUp", "Error during sign-up: $e")
            emit(false) // Emit false on exception
        }
    }
}