package com.fourteenrows.p2pcs.registration

import androidx.test.rule.ActivityTestRule
import com.fourteenrows.p2pcs.activities.authentication.registration.RegistrationView
import com.fourteenrows.p2pcs.model.ModelFirebase

class RegistartionRule<A : RegistrationView>(activityClass: Class<A>) : ActivityTestRule<A>(activityClass) {

    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()
        ModelFirebase.signOut()
        Thread.sleep(3000)
    }

    /*
override fun afterActivityFinished() {
    super.afterActivityFinished()
    ModelFirebase.signOut()
}

override fun getActivityIntent(): Intent {
// add some custom extras and stuff
    return Intent()
}

override fun afterActivityLaunched() {
    super.afterActivityLaunched()
    // maybe you want to do something here
}
*/
}