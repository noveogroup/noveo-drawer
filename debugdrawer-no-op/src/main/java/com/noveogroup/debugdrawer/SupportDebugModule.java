package com.noveogroup.debugdrawer;

import io.palaima.debugdrawer.base.DebugModule;

@SuppressWarnings("PMD.EmptyMethodInAbstractClassShouldBeAbstract")
abstract class SupportDebugModule implements DebugModule {

    @Override
    public void onOpened() {
        //do nothing
    }

    @Override
    public void onClosed() {
        //do nothing
    }

    @Override
    public void onResume() {
        //do nothing
    }

    @Override
    public void onPause() {
        //do nothing
    }

    @Override
    public void onStart() {
        //do nothing
    }

    @Override
    public void onStop() {
        //do nothing
    }

}
