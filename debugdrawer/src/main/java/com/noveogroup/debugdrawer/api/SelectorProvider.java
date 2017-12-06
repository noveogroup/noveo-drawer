package com.noveogroup.debugdrawer.api;

import java.util.List;

public interface SelectorProvider extends SettingsAggregatorProvider<String> {
    List<String> values(String name);
}
