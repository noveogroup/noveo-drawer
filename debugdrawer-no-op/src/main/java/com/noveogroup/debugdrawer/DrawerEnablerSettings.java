package com.noveogroup.debugdrawer;

import com.noveogroup.debugdrawer.api.EnablerProvider;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by avaytsekhovskiy on 06/12/2017.
 */
final class DrawerEnablerSettings implements EnablerProvider {

    DrawerEnablerSettings() {
        //do nothing
    }

    @Override
    public Set<String> names() {
        return new HashSet<>();
    }

    @Override
    public Boolean read(final String name) {
        return false;
    }

}
