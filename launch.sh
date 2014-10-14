#!/bin/bash

./gradlew clean assembleDebug

adb install -r app/build/outputs/apk/app-debug.apk