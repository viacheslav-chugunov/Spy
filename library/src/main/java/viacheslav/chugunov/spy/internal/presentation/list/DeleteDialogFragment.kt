package viacheslav.chugunov.spy.internal.presentation.list

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.presentation.BaseDialog

class DeleteDialogFragment : BaseDialog(R.layout.spy_res_delete_dialog_fragment) {

    private lateinit var buttonAgree: AppCompatButton
    private lateinit var buttonDisagree: AppCompatButton


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

