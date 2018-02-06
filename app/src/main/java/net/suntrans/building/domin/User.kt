package net.suntrans.building.domin

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Looney on 2018/1/31.
 * Des:
 */

@Entity(tableName = "user")
class User {
    @PrimaryKey
    var id: Int = 0
}
