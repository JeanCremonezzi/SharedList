package br.edu.ifsp.scl.sharedlist.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
class Task (
    @PrimaryKey(autoGenerate = true) val id: Int? = -1,
    @NonNull var title: String = "",
    @NonNull var description: String = "",
    @NonNull var createAt: String = "",
    @NonNull var createBy: String = "",
    @NonNull var estimatedFinishDate: String = "",
    var finishedAt: String = "",
    var finishedBy: String = "",
): Parcelable