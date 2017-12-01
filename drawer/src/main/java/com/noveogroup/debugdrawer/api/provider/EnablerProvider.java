package com.noveogroup.debugdrawer.api.provider;

import java.util.Set;

public interface EnablerProvider {

    boolean isEnablerEnabled(String name);

    Set<String> getEnablers();
}
