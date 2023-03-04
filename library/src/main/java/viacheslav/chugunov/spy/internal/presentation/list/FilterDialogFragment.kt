package viacheslav.chugunov.spy.internal.presentation.list

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.presentation.BaseDialog

internal class FilterDialogFragment : BaseDialog(R.layout.spy_res_dialog_fragment_filter_events) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val map = listener!!.provideFilters()

        val checkBoxSuccess: CheckBox = view.findViewById(R.id.cb_success)
        val checkBoxInfo: CheckBox = view.findViewById(R.id.cb_info)
        val checkBoxWarning: CheckBox = view.findViewById(R.id.cb_warning)
        val checkBoxError: CheckBox = view.findViewById(R.id.cb_error)

        checkBoxSuccess.isChecked = map[SpyEventsAdapter.ViewType.SUCCESS]!!
        checkBoxInfo.isChecked = map[SpyEventsAdapter.ViewType.INFO]!!
        checkBoxWarning.isChecked = map[SpyEventsAdapter.ViewType.WARNING]!!
        checkBoxError.isChecked = map[SpyEventsAdapter.ViewType.ERROR]!!

        checkBoxSuccess.setOnClickListener {
            listener?.onCheckBoxesClicked(
                SpyEventsAdapter.ViewType.SUCCESS,
                checkBoxSuccess.isChecked
            )
        }
        checkBoxInfo.setOnClickListener {
            listener?.onCheckBoxesClicked(
                SpyEventsAdapter.ViewType.INFO, checkBoxInfo.isChecked
            )
        }

        checkBoxWarning.setOnClickListener {
            listener?.onCheckBoxesClicked(
                SpyEventsAdapter.ViewType.WARNING,
                checkBoxWarning.isChecked
            )
        }
        checkBoxError.setOnClickListener {
            listener?.onCheckBoxesClicked(
                SpyEventsAdapter.ViewType.ERROR,
                checkBoxError.isChecked
            )
        }
    }
}