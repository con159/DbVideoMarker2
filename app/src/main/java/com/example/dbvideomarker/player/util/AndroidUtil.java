package com.example.dbvideomarker.player.util;

import android.net.Uri;
import android.os.Build;

import java.io.File;
import java.util.Objects;

public class AndroidUtil {


    public static boolean isJellyBeanMR1OrLater() {
        return true;
    }

    public static boolean isJellyBeanMR2OrLater() {
        return true;
    }

    public static boolean isKitKatOrLater() {
        return true;
    }

    public static boolean isLolliPopOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isMarshMallowOrLater() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M;
    }

    public static File UriToFile(Uri uri) {
        return new File(Objects.requireNonNull(uri.getPath()).replaceFirst("file://", ""));
    }

    /**
     * Quickly converts path to URIs, which are mandatory in libVLC.
     *
     * @param path The path to be converted.
     * @return A URI representation of path
     */
    public static Uri PathToUri(String path) {
        return Uri.fromFile(new File(path));
    }

    public static Uri LocationToUri(String location) {
        Uri uri = Uri.parse(location);
        if (uri.getScheme() == null)
            throw new IllegalArgumentException("location has no scheme");
        return uri;
    }

    public static Uri FileToUri(File file) {
        return Uri.fromFile(file);
    }
}