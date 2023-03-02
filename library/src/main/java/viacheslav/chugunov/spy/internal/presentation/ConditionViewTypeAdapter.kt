package viacheslav.chugunov.spy.internal.presentation

import android.os.Parcel
import android.os.Parcelable
import viacheslav.chugunov.spy.internal.presentation.list.SpyEventsAdapter

class ConditionViewTypeAdapter() : Parcelable {

    private val array = BooleanArray(4) { true }

    constructor(parcel: Parcel) : this()

    fun addCondition(index: Int, condition: Boolean) {
        array[index] = condition
    }

    fun getAllConditions(value: Int): Boolean {
        var result = false
        if (array[0]) result = result || value == SpyEventsAdapter.ViewType.SUCCESS
        if (array[1]) result = result || value == SpyEventsAdapter.ViewType.INFO
        if (array[2]) result = result || value == SpyEventsAdapter.ViewType.WARNING
        if (array[3]) result = result || value == SpyEventsAdapter.ViewType.ERROR
        return result
    }

    fun getAvailableIndex(): Int {
        for (i in array.indices) {
            if (array[i]) return i
        }
        return -1
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ConditionViewTypeAdapter> {
        override fun createFromParcel(parcel: Parcel): ConditionViewTypeAdapter {
            return ConditionViewTypeAdapter(parcel)
        }

        override fun newArray(size: Int): Array<ConditionViewTypeAdapter?> {
            return arrayOfNulls(size)
        }
    }
}