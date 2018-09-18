package com.international.wtw.lottery.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;


import com.international.wtw.lottery.newJason.GetUserinfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class ShareUtil {
    /**
     *  存对象
     * @param context
     * @param preferenceName
     * @param key
     * @param user
     * @throws Exception
     */
    public static void saveUser(Context context, String preferenceName, String key, ArrayList<GetUserinfo> user) throws Exception {
        if(user instanceof Serializable) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName, context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(user);//把对象写到流里
                String temp = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
                editor.putString(key, temp);
                editor.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            throw new Exception("User must implements Serializable");
        }
    }


    /**
     * 取对象
     * @param context
     * @param preferenceName
     * @param key
     * @return
     */
    public static ArrayList<GetUserinfo> getUser(Context context, String preferenceName, String key) {
        SharedPreferences sharedPreferences=context.getSharedPreferences(preferenceName,context.MODE_PRIVATE);
        String temp = sharedPreferences.getString(key, "");
        ByteArrayInputStream bais =  new ByteArrayInputStream(Base64.decode(temp.getBytes(), Base64.DEFAULT));
        ArrayList<GetUserinfo> user = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            user = (ArrayList<GetUserinfo>) ois.readObject();
        } catch (IOException e) {
        }catch(ClassNotFoundException e1) {

        }
        return user;
    }

}
