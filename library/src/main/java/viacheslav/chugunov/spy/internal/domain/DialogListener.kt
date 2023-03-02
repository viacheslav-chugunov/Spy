package viacheslav.chugunov.spy.internal.domain

interface DialogListener {
    fun onCheckBoxesClicked(index: Int, isChecked: Boolean)
    fun onAgreeButtonClick()
}