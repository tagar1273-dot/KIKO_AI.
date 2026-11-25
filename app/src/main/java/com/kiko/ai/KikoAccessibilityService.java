package com.kiko.ai;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class KikoAccessibilityService extends AccessibilityService {
    private static KikoAccessibilityService instance;
    @Override protected void onServiceConnected() { super.onServiceConnected(); instance = this; }
    @Override public void onAccessibilityEvent(AccessibilityEvent event) { /* Events are not uploaded or logged. */ }
    @Override public void onInterrupt() { }
    @Override public void onDestroy() { instance = null; super.onDestroy(); }

    public static boolean performBack() { return instance != null && instance.performGlobalAction(GLOBAL_ACTION_BACK); }
    public static boolean performHome() { return instance != null && instance.performGlobalAction(GLOBAL_ACTION_HOME); }

    public static boolean clickVisibleText(String label) {
        if (instance == null || label == null) return false;
        AccessibilityNodeInfo root = instance.getRootInActiveWindow();
        if (root == null) return false;
        try {
            java.util.List<AccessibilityNodeInfo> nodes = root.findAccessibilityNodeInfosByText(label);
            for (AccessibilityNodeInfo node : nodes) {
                if (node.isClickable()) return node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                AccessibilityNodeInfo parent = node.getParent();
                if (parent != null && parent.isClickable()) return parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        } finally { root.recycle(); }
        return false;
    }
}
