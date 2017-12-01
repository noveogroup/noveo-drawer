package com.noveogroup.debugdrawer.api.provider;

import java.util.Set;

public interface SelectorProvider {

    String getSelectorValue(String name);

    Set<String> getSelectors();
}
