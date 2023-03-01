package viacheslav.chugunov.spy.internal.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import viacheslav.chugunov.spy.R

class DeleteDialogFragment : BottomSheetDialogFragment() {

    private lateinit var buttonAgree: AppCompatButton
    private lateinit var buttonDisagree: AppCompatButton

    private var listener: Listener? = null

    interface Listener {
        fun onAgreeButtonClick()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        listener = activity?.supportFragmentManager?.fragments?.get(0) as Listener
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.spy_res_delete_dialog_fragment, container, false)
    }

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

    override fun onDestroy() {
        super.onDestroy()
        listener = null
    }
}