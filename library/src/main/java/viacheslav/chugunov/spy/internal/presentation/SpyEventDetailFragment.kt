package viacheslav.chugunov.spy.internal.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.data.SpyEvent

internal class SpyEventDetailFragment(event: SpyEvent) : BaseFragment(R.layout.fragment_spy_event_detail)