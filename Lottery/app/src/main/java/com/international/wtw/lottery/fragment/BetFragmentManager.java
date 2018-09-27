package com.international.wtw.lottery.fragment;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.util.SparseArray;

/**
 * 描述：使用工厂类创建和保存Fragment
 */

public class BetFragmentManager {

    private static ArrayMap<Integer, SparseArray<BetFragment>> sArrayMap = new ArrayMap<>();

    public static void setFragment(FragmentActivity context, @IdRes int container, int gameCode, int position, boolean isClosed) {
        SparseArray<BetFragment> sFragments = sArrayMap.get(gameCode);
        if (sFragments == null) {
            sFragments = new SparseArray<>();
        }
        BetFragment target = sFragments.get(position);
        if (target == null) {
            target = BetFragment.newInstance(gameCode, position, isClosed);
            sFragments.put(position, target);
            sArrayMap.put(gameCode, sFragments);
        }
        FragmentManager sManager = context.getSupportFragmentManager();
        sManager.executePendingTransactions();
        FragmentTransaction transaction = sManager.beginTransaction();
        if (!target.isAdded()) {
            transaction.add(container, target);
        }
        for (int i = 0; i < sFragments.size(); i++) {
            int key = sFragments.keyAt(i);
            BetFragment fragment = sFragments.get(key);
            if (fragment == target) {
                transaction.show(fragment);
            } else {
                transaction.hide(fragment);
            }
        }
        transaction.commit();
    }

    public static BetFragment getCurrentFragment(int gameCode, int position) {
        return sArrayMap.get(gameCode).get(position);
    }

    public static void clear(int gameCode) {
        SparseArray<BetFragment> sFragments = sArrayMap.get(gameCode);
        if (sFragments == null || sFragments.size() == 0) {
            return;
        }
        sFragments.clear();
        sArrayMap.remove(gameCode);
    }
}
