
adb remount

adb push Tbox/build/outputs/apk/product/release/TBoxService-TBox-product-release.apk /system/app/B15CBD_TBoxService-TBox/B15CBD_TBoxService-TBox.apk

pause

adb shell stop;start