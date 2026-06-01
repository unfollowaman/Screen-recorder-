package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "recordings")
data class RecordingEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val filePath: String,
    val durationMs: Long,
    val resolution: String,
    val fps: Int,
    val codec: String,
    val fileSize: Long,
    val createdAt: Long = System.currentTimeMillis(),
    val isRecovered: Boolean = false
)
