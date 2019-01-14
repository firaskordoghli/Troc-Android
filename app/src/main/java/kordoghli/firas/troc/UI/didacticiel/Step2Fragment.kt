package kordoghli.firas.troc.UI.didacticiel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ivb.com.materialstepper.stepperFragment
import kordoghli.firas.troc.R

class Step2Fragment: stepperFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_didacticiel_2, container, false)
    }

    override fun onNextButtonHandler(): Boolean {
        return true
    }

    companion object {
        fun newInstance(): Step2Fragment =
            Step2Fragment()
    }
}