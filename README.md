# Noveo Debug Drawer

based on [Android Debug Drawer](https://github.com/palaima/DebugDrawer) with ideas from [Jake Wharton's u2020](https://github.com/JakeWharton/u2020).

## What's new

1. GradleModule to make your QA happy.
2. EnablerModule & SelectorModule to replace build flavors in single apk.

## How to add

```groovy
//Extension: Build Info Module
api 'com.noveogroup:debugdrawer-buildinfo:0.0.1'

//Extension: Build Config Module
debugApi 'com.noveogroup:debugdrawer-buildconfig:0.0.1'
releaseApi 'com.noveogroup:debugdrawer-buildconfig-no-op:0.0.1'

//Debug Drawer https://github.com/palaima/DebugDrawer
debugApi   'io.palaima.debugdrawer:debugdrawer:0.7.0'
releaseApi 'io.palaima.debugdrawer:debugdrawer-no-op:0.7.0'
debugApi   'io.palaima.debugdrawer:debugdrawer-view:0.7.0'
releaseApi 'io.palaima.debugdrawer:debugdrawer-view-no-op:0.7.0'
```

## Build Info Module

| `BuildInfoModule` |  
| :---: | 
| ![Gradle Module Screenshot](images/gradle-module.png) | 
| Extracts build source information. Great for CI Integration | 

1. copy [drawer.gradle](/sample/drawer.gradle) to your app module
2. add these lines to your _app/build.gradle_:
```groovy
defaultConfig {
    ...
    buildConfigField 'String', noveoDrawer.who, noveoDrawer.builderInfo()
    buildConfigField 'String', noveoDrawer.date, noveoDrawer.buildDate()
}
```
3. add `new GradleModule(BuildConfig.class)` to your DebugDrawer 

or extract info manually.

1. add info manually `new GradleModule(source, date, flavor, buildType)`

## BuildConfig modules

| `SelectorModule` | `EnablerModule` |
| :---: | :---: |
| ![](images/selector-module.png) | ![](images/enabler-module.png) |
| Select variant from Spinner | Enable/Disable properties | 

The main feature of these modules is that selected properties will be loaded during `application.onCreate()`:

* You can switch between _production_ / _staging_ environments with single APK installed.
* It makes easy for you to implement [Jake Wharton's u2020](https://github.com/JakeWharton/u2020) behavior for [Stetho](http://facebook.github.io/stetho/) & [LeakCanary](https://github.com/square/leakcanary).
* See more in [sample app](sample)

### How to add

Create `DebugBuildConfiguration` and keep Application scoped reference on it.

```java
DebugBuildConfiguration configuration = DebugBuildConfiguration.init(application);
configuration.enableDebug(); //to enable Slf4j logging
```

### EnablerModule

```java
final Enabler stetho = Enabler.create(ENABLER_STETHO, enabled -> {
    if (enabled) Stetho.initializeWithDefaults(application);
});
final Enabler leak = Enabler.create(ENABLER_LEAK, enabled -> {
    if (enabled) LeakCanary.install(application);
});

stetho.setReleaseValue(true);

EnablerModule.init(configuration, stetho, leak, ...);
```

### SelectorModule

```java
Selector endpoint = new Selector(SELECTOR_ENDPOINT,
            "http://staging.noveogroup.com",
            "http://production.noveogroup.com",
            "http://mock.noveogroup.com");

//Use this value for release build always.
endpoint.setReleaseValue("http://production.noveogroup.com");

SelectorModule.init(configuration, endpoint, ...);
```

### Read Values in runtime

```java
DebugBuildConfiguration configuration;

String endpoint = configuration.getSelectorProvider().read(SELECTOR_ENDPOINT);
Boolean enabled = configuration.getEnablerProvider().read(ENABLER_STETHO);
```

Check more examples at [sample](sample)

## License

```text
Copyright (c) 2017 Noveo Group

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

Except as contained in this notice, the name(s) of the above copyright holders
shall not be used in advertising or otherwise to promote the sale, use or
other dealings in this Software without prior written authorization.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```