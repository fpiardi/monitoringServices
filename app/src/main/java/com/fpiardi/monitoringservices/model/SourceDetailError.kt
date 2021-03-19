package com.fpiardi.monitoringservices.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Entity(tableName = "source_detail_error",
    primaryKeys = ["source", "date"]
)
data class SourceDetailError(
    @ColumnInfo(name = "source")
    val source: String,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "name")
    val name: String
)