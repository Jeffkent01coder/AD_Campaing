package com.example.adcampaing.feature_post.data.repository

import android.net.Uri
import com.example.adcampaing.core.utils.Constants.COLLECTION_NAME_POSTS
import com.example.adcampaing.core.utils.Constants.ERROR_MESSAGE
import com.example.adcampaing.core.utils.Resource
import com.example.adcampaing.feature_auth.domain.repository.AuthRepository
import com.example.adcampaing.feature_post.domain.model.Post
import com.example.adcampaing.feature_post.domain.repository.PostRepository
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
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

class PostRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase,
    private val storage: FirebaseStorage,
    private val db: FirebaseFirestore,
    private val authRepository: AuthRepository
) : PostRepository {
    override suspend fun uploadPictureToFirebase(url: Uri): Flow<Resource<String>> = flow{
        try {
            emit(Resource.Loading())
            val uuidImage = UUID.randomUUID()
            val imageName = "posts/$uuidImage.jpg"
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

    override suspend fun createPostToFirebase(post: Post): Flow<Resource<Boolean>> = flow{
        try {
            emit(Resource.Loading())
            val userUUID = authRepository.currentUser?.uid.toString()
            val userEmail = authRepository.currentUser?.email.toString()
            val databaseReference =
                db.collection(COLLECTION_NAME_POSTS)

            val posts = hashMapOf<String, Any>()
            posts[userUUID] = post.postId
            posts["/profileUUID/"] = userUUID
            posts["/userEmail/"] = userEmail
            posts["/userName/"] = post.userName
            posts["/postImage/"] = post.postImage
            posts["/postDescription/"] = post.postDescription

            databaseReference.add(posts).addOnSuccessListener{
//                Log.d("postData", it.toString())
            }.await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: ERROR_MESSAGE))
        }
    }
    //    override suspend fun createPostRoomToFirebase(): Flow<Resource<String>> =
//        flow {
//            try {
//                emit(Resource.Loading())
//
//                val userUUID = authRepository.currentUser?.uid
//
//                val hashMapOfRequesterUUIDAndAcceptorUUID = hashMapOf<String, String>()
//                hashMapOfRequesterUUIDAndAcceptorUUID[userUUID!!] = userUUID
//
//                val databaseReference = database.getReference("Chat_Rooms")
//
//                val gson = Gson()
//                val postUUId =
//                    gson.toJson(hashMapOfRequesterUUIDAndAcceptorUUID)
//
//                databaseReference
//                    .child(postUUId)
//                    .setValue(true)
//                    .await()
//
//                emit(Resource.Success(postUUId))
//
//            } catch (e: Exception) {
//                emit(Resource.Error(e.message ?: ERROR_MESSAGE))
//            }
//        }
    override suspend fun loadPostFromFirebase(): Flow<Resource<Post>> = callbackFlow{
        try {
            this@callbackFlow.trySendBlocking(Resource.Loading())
            val userUUID = authRepository.currentUser?.uid
            val databaseReference = db.collection(COLLECTION_NAME_POSTS)
            databaseReference.get().addOnCompleteListener { snapshot->
                snapshot.addOnSuccessListener { doc->
                    if (!doc.isEmpty){
                        for (d in doc.documents){
                            val post = d.toObject(Post::class.java)?: Post()
                            this@callbackFlow.trySendBlocking(Resource.Success(post))
                        }
                    }
                }
            }.await()
            awaitClose {
                channel.close()
                cancel()
            }
        } catch (e: Exception) {
            this@callbackFlow.trySendBlocking(Resource.Error(e.message ?: ERROR_MESSAGE))
        }

    }
}