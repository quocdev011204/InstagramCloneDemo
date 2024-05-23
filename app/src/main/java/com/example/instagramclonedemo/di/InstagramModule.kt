package com.example.instagramclonedemo.di

import com.example.instagramclonedemo.data.AuthRepositoryImpl
import com.example.instagramclonedemo.data.FirebaseAuthenticator
import com.example.instagramclonedemo.domain.AuthRepository
import com.example.instagramclonedemo.domain.Authenticator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//import com.google.android.datatransport.runtime.dagger.Module
//import com.google.android.datatransport.runtime.dagger.Provides

//@Module
//@InstallIn(SingletonComponent::class)
//object InstagramModule {
//
//    @Singleton
//    @Provides
//    fun provieFirebaseAuthentication(): FirebaseAuth {
//        return FirebaseAuth.getInstance()
//    }
//
//    @Singleton
//    @Provides
//    fun provieFirebaseFirestore(): FirebaseFirestore {
//        return FirebaseFirestore.getInstance()
//    }
//
//    @Singleton
//    @Provides
//    fun provieFirebaseFireStorage(): FirebaseStorage {
//        return FirebaseStorage.getInstance()
//    }
//
//    @Singleton
//    @Provides
//    fun provideAuthenticationResponsitory(
//        auth: FirebaseAuth,
//        firestore: FirebaseFirestore
//    ): AuthenticationResponsitory {
//        return AuthenticationResponsitoryImpl(auth = auth, firestore = firestore)
//    }
//
//    @Singleton
//    @Provides
//    fun provideAuthUseCases(responsitory: AuthenticationResponsitoryImpl) = AuthenticationUseCases(
//        isUserAuthenticated = IsUserAuthenticated(responsitory = responsitory),
//        firebaseAuthState = FirebaseAuthState(responsitory = responsitory),
//        firebaseSignOut = FirebaseSignOut(responsitory = responsitory),
//        firebaseSignIn = FirebaseSignIn(responsitory = responsitory),
//        firebaseSignUp = FirebaseSignUp(responsitory = responsitory)
//    )
//}
@InstallIn(SingletonComponent::class)
@Module
object AuthModule {

    @Singleton
    @Provides
    fun provideAuthenticator(): Authenticator {
        return FirebaseAuthenticator()
    }

    @Singleton
    @Provides
    fun provideAuthRepository(authenticator: Authenticator): AuthRepository {
        return AuthRepositoryImpl(authenticator)
    }

    @Provides
    fun provideauthentication(): FirebaseAuth = Firebase.auth

    @Provides
    fun provideFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideStorage(): FirebaseStorage = Firebase.storage



    // Test
//    @Provides
//    @Singleton
//    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

//    @Provides
//    @Singleton
//    fun provideFirebaseFireStore(): FirebaseFirestore = FirebaseFirestore.getInstance()
//
//
//    @Provides
//    @Singleton
//    fun provideStorageReference(): StorageReference = FirebaseStorage.getInstance().getReference("Files")
//
//    @Provides
//    @Singleton
//    fun provideProfileBuilder(): UserProfileChangeRequest.Builder =
//        UserProfileChangeRequest.Builder()
//
//    @Provides
//    @Singleton
//    fun provideUser(): CreateUserDto = CreateUserDto("", "", "", "", "", "", null)
//
////    @Provides
////    @Singleton
////    fun provideAuthRepository(
////        firebaseAuth: FirebaseAuth,
////        fireStore: FirebaseFirestore,
////        profileUpdates: UserProfileChangeRequest.Builder,
////        userModel: CreateUserDto
////    ): AuthRepository =
////        AuthRepositoryImpl(firebaseAuth, fireStore,profileUpdates, userModel)
//
////    @Provides
////    @Singleton
////    fun provideUsersRepository(fireStore: FirebaseFirestore): UsersRepository =
////        UsersRepositoryImpl( fireStore)
//
//    @Provides
//    @Singleton
//    fun provideMessageRepository(fireStore: FirebaseFirestore, mStorageRef: StorageReference, application: Application, myNotificationManager : MyNotificationManager): MessageRepository =
//        MessageRepositoryImpl( fireStore , mStorageRef , application , myNotificationManager)
}