package viacheslav.chugunov.spy.internal.presentation.list

import android.os.Bundle
import android.view.View
import android.widget.TextView
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.presentation.BaseDialog

internal class DeleteDialogFragment : BaseDialog(R.layout.spy_res_dialog_fragment_delete) {

    private lateinit var buttonAgree: TextView
    private lateinit var buttonDisagree: TextView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonAgree = view.findViewById(R.id.b_agree_delete_dialog)
        buttonDisagree = view.findViewById(R.id.b_disagree_delete_dialog)
        buttonAgree.setOnClickListener {
            listener?.onAgreeButtonClick()
            dismiss()
        }
        buttonDisagree.setOnClickListener { dismiss() }
    }
}

