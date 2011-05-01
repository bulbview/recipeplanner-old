package com.bulbview.recipeplanner.persistence;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyOpts;

public class TestObjectifyFactory extends ObjectifyFactory {

    @Override
    public Objectify begin(final ObjectifyOpts opts) {
        // This can be used to enable/disable the memory cache globally.
        opts.setGlobalCache(true);
        // This can be used to enable/disable the session caching objectify
        // Note that it will break several unit tests that check for
        // transmutation when entities are run through the DB (ie, unknown
        // List types become ArrayList). These failures are ok.
        opts.setSessionCache(false);
        return super.begin(opts);
    }
}
