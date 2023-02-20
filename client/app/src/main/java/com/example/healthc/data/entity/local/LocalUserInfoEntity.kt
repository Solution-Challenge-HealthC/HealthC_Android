package com.example.healthc.data.entity.local

import androidx.room.PrimaryKey
import androidx.room.Entity
import com.example.healthc.domain.model.auth.UserInfo

@Entity(
    tableName = "USER_INFO"
)
data class LocalUserInfoEntity(
    val name: String,
    val disease: List<String>,
    val allergy: List<String>,
    @PrimaryKey val id: Int? = null
) {
    constructor(): this("", emptyList(), emptyList())

    // to domain model
    fun toUserInfo(): UserInfo {
        return UserInfo(
            name = name,
            disease = disease,
            allergy = allergy
        )
    }
}