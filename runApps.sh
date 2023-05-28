#!/usr/bin/env bash

# desktop
echo starting desktop app
./gradlew :frontend:desktop:run &

# android
echo starting android app
./gradlew :frontend:android:installDebug
adb shell am start -n com.weesnerDevelopment.lavalamp.android/.MainActivity

# terminal
echo starting terminal app
./gradlew :frontend:terminal:run
