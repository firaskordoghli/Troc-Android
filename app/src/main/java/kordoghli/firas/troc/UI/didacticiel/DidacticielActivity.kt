package kordoghli.firas.troc.UI.didacticiel

import ivb.com.materialstepper.progressMobileStepper
import java.util.ArrayList

class DidacticielActivity: progressMobileStepper() {

    internal var stepperFragmentList: MutableList<Class<*>> = ArrayList()

    override fun init(): MutableList<Class<*>> {
        stepperFragmentList.add(Step1Fragment::class.java)
        stepperFragmentList.add(Step2Fragment::class.java)
        stepperFragmentList.add(Step3Fragment::class.java)
        stepperFragmentList.add(Step4Fragment::class.java)


        return stepperFragmentList
    }
    override fun onStepperCompleted() {
        this.finish()
    }


}