package com.example.adcampaing.feature_user.data.repository

import android.net.Uri
import com.example.adcampaing.core.utils.Constants.COLLECTION_NAME_USERS
import com.example.adcampaing.core.utils.Constants.ERROR_MESSAGE
import com.example.adcampaing.core.utils.Resource
import com.example.adcampaing.feature_auth.domain.repository.AuthRepository
import com.example.adcampaing.feature_user.domain.model.User
import com.example.adcampaing.feature_user.domain.repository.UserRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val auth: AuthRepository,
    private val database: FirebaseDatabase
) : UserRepository {
    override suspend fun uploadPictureToFirebase(url: Uri): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val uuidImage = UUID.randomUUID()
            val imageName = "images/$uuidImage.jpg"
            val storageRef = storage.reference.child(imageName)

            storageRef.putFile(url).apply {}.await()
            var downloadUrl = ""
            storageRef.downloadUrl.addOnSuccessListener {
                downloadUrl = it.toString()
            }.await()
            emit(Resource.Success(downloadUrl))


        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun createOrUpdateProfileToFirebase(user: User): Flow<Resource<Boolean>> =
        flow {
            try {
                emit(Resource.Loading())
                val userUUID = auth.currentUser?.uid.toString()
                val userEmail = auth.currentUser?.email.toString()

                val databaseReference =
                    database.getReference(COLLECTION_NAME_USERS).child(userUUID).child("user")

                val childUpdates = mutableMapOf<String, Any>()

                childUpdates["/profileUUID/"] = userUUID
                childUpdates["/userEmail/"] = userEmail

                if (user.userName != "") childUpdates["/userName/"] = user.userName
                if (user.imageUrl != "") childUpdates["/imageUrl/"] =
                    user.imageUrl
                if (user.userSurName != "") childUpdates["/userSurName/"] = user.userSurName
                if (user.totalPost != "") childUpdates["/totalPost/"] = user.totalPost
                if (user.bio != "") childUpdates["/bio/"] = user.bio
                if (user.userPhoneNumber != "") childUpdates["/userPhoneNumber/"] =
                    user.userPhoneNumber
                databaseReference.updateChildren(childUpdates).await()
                emit(Resource.Success(true))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: ERROR_MESSAGE))
            }
        }

    override suspend fun loadProfileFromFirebase(): Flow<Resource<User>> = callbackFlow {
        try {
            this@callbackFlow.trySendBlocking(Resource.Loading())
            val userUUID = auth.currentUser?.uid.toString()
            val databaseReference = database.getReference(COLLECTION_NAME_USERS)
            val postListener = databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userFromFirebaseDatabase =
                        snapshot.child(userUUID).child("user").getValue(User::class.java)
                            ?: User()
                    Timber.tag("UserData").d(userFromFirebaseDatabase.toString())
                    this@callbackFlow.trySendBlocking(Resource.Success(userFromFirebaseDatabase))
                }

                override fun onCancelled(error: DatabaseError) {
                    this@callbackFlow.trySendBlocking(Resource.Error(error.message))
                }
            }
            )
            databaseReference.addValueEventListener(postListener)
            awaitClose {
                databaseReference.removeEventListener(postListener)
                channel.close()
                cancel()
            }
        } catch (e: Exception) {
            this@callbackFlow.trySendBlocking(Resource.Error(e.message ?: ERROR_MESSAGE))
        }
    }


}
