package br.edu.ifsp.scl.sharedlist.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
class Task (
    @PrimaryKey val id: String = "",
    @NonNull val title: String = "",
    @NonNull var description: String = "",
    @NonNull val createAt: String = "",
    @NonNull val createBy: String = "",
    @NonNull var estimatedFinishDate: String = "",
    var finishedAt: String = "",
    var finishedBy: String = "",
): Parcelable