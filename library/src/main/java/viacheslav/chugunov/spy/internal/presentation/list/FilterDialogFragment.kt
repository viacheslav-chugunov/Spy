package viacheslav.chugunov.spy.internal.presentation.list

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.CheckBox
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.presentation.BaseDialog

class FilterDialogFragment() : BaseDialog(R.layout.spy_res_filter_events_fragment), Parcelable {

    private var successChecked = true
    private var infoChecked = true
    private var warningChecked = true
    private var errorChecked = true

    constructor(parcel: Parcel) : this() {
        successChecked = parcel.readByte() != 0.toByte()
        infoChecked = parcel.readByte() != 0.toByte()
        warningChecked = parcel.readByte() != 0.toByte()
        errorChecked = parcel.readByte() != 0.toByte()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        successChecked = savedInstanceState?.getBoolean("success") ?: true
        infoChecked = savedInstanceState?.getBoolean("info") ?: true
        warningChecked = savedInstanceState?.getBoolean("warning") ?: true
        errorChecked = savedInstanceState?.getBoolean("error") ?: true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val checkBoxSuccess: CheckBox = view.findViewById(R.id.cb_success)
        val checkBoxInfo: CheckBox = view.findViewById(R.id.cb_info)
        val checkBoxWarning: CheckBox = view.findViewById(R.id.cb_warning)
        val checkBoxError: CheckBox = view.findViewById(R.id.cb_error)

        checkBoxSuccess.isChecked = successChecked
        checkBoxInfo.isChecked = infoChecked
        checkBoxWarning.isChecked = warningChecked
        checkBoxError.isChecked = errorChecked

        checkBoxSuccess.setOnClickListener {
            listener?.onCheckBoxesClicked(
                0,
                checkBoxSuccess.isChecked
            )
        }
        checkBoxInfo.setOnClickListener { listener?.onCheckBoxesClicked(1, checkBoxInfo.isChecked) }
        checkBoxWarning.setOnClickListener {
            listener?.onCheckBoxesClicked(
                2,
                checkBoxWarning.isChecked
            )
        }
        checkBoxError.setOnClickListener {
            listener?.onCheckBoxesClicked(
                3,
                checkBoxError.isChecked
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("success", successChecked)
        outState.putBoolean("info", infoChecked)
        outState.putBoolean("warning", warningChecked)
        outState.putBoolean("error", errorChecked)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (successChecked) 1 else 0)
        parcel.writeByte(if (infoChecked) 1 else 0)
        parcel.writeByte(if (warningChecked) 1 else 0)
        parcel.writeByte(if (errorChecked) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FilterDialogFragment> {
        override fun createFromParcel(parcel: Parcel): FilterDialogFragment {
            return FilterDialogFragment(parcel)
        }

        override fun newArray(size: Int): Array<FilterDialogFragment?> {
            return arrayOfNulls(size)
        }
    }

}