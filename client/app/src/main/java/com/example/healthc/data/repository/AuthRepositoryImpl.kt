package com.example.healthc.data.repository

import com.example.healthc.data.entity.auth.UserEntity
import com.example.healthc.data.entity.auth.UserInfoEntity
import com.example.healthc.data.source.auth.SignInDataSource
import com.example.healthc.data.source.auth.SignUpDataSource
import com.example.healthc.domain.AuthRepository
import com.example.healthc.domain.model.auth.User
import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.utils.Resource
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val signInDataSource: SignInDataSource,
    private val signUpDataSource: SignUpDataSource
) : AuthRepository {
    override suspend fun signIn(user: User): Resource<FirebaseUser> =
        signInDataSource.signIn(UserEntity(user.email, user.password))

    override suspend fun signUp(user: User, userInfo: UserInfo): Resource<FirebaseUser> =
        signUpDataSource.signUp(UserEntity(user.email, user.password),
            UserInfoEntity(userInfo.name, userInfo.disease, userInfo.allergy)
        )

    override val currentUser: FirebaseUser?
        get() = TODO("Not yet implemented")

    override fun logout() {
        TODO("Not yet implemented")
    }
}