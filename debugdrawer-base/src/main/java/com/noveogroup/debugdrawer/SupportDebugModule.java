package com.noveogroup.debugdrawer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.palaima.debugdrawer.base.DebugModule;

@SuppressWarnings("PMD.EmptyMethodInAbstractClassShouldBeAbstract")
abstract class SupportDebugModule implements DebugModule {
    final Logger logger = LoggerFactory.getLogger(getClass());

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
        Utils.registerModule(this);
    }

    @Override
    public void onStop() {
        //do nothing
    }

    public abstract String getDebugInfo();

}
