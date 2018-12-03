/**   
 * @Title: TabInfo.java 
 * @Package com.ryg.library_indicatorfragment 
 * @Description: TODO
 * @author 传奇丶小胖   
 * @date 2015-7-25 上午11:00:33 
 * @version V1.0
 * @Copyright(c) 2015 传奇公司-版权所有
 */ 
package com.ryg.library_indicatorfragment;

import java.lang.reflect.Constructor;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

/** 
 * @ClassName: TabInfo 
 * @Description: TODO
 * @author 传奇丶小胖
 * @date 2015-7-25 上午11:00:33 
 *  
 */
public class TabInfo implements Parcelable {
	private int id;
    private int icon;
    private int icon_selected;
    private String name = null;
    public boolean hasTips = false;
    public Fragment fragment = null;
    public boolean notifyChange = false;
    @SuppressWarnings("rawtypes")
    public Class fragmentClass = null;
    public int type = -1;

    @SuppressWarnings("rawtypes")
    public TabInfo(int id, String name, Class clazz) {
        this(id, name, 0, clazz);
    }

    @SuppressWarnings("rawtypes")
    public TabInfo(int id, String name, boolean hasTips, Class clazz) {
        this(id, name, 0, clazz);
        this.hasTips = hasTips;
    }
    @SuppressWarnings("rawtypes")
    public TabInfo(int id, String name, Class clazz,int type) {
        this(id, name, 0, clazz);
        this.type = type;
    }
    @SuppressWarnings("rawtypes")
    public TabInfo(int id, String name, int iconid, Class clazz) {
        super();

        this.name = name;
        this.id = id;
        icon = iconid;
        fragmentClass = clazz;
    }
    @SuppressWarnings("rawtypes")
    public TabInfo(int id, String name, int iconid,int icon_selected_id, Class clazz) {
        super();

        this.name = name;
        this.id = id;
        icon = iconid;
        this.icon_selected = icon_selected_id;
        fragmentClass = clazz;
    }
    public TabInfo(Parcel p) {
        this.id = p.readInt();
        this.name = p.readString();
        this.icon = p.readInt();
        this.notifyChange = p.readInt() == 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(int iconid) {
        icon = iconid;
    }

    public int getIcon() {
        return icon;
    }

    public int getIcon_selected() {
        return icon_selected;
    }

    public void setIcon_selected(int icon_selected) {
        this.icon_selected = icon_selected;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Fragment createFragment() {
        if (fragment == null) {
            Constructor constructor;
            try {
            	if(type==-1){
            		constructor = fragmentClass.getConstructor(new Class[0]);
                    fragment = (Fragment) constructor.newInstance(new Object[0]);
            	}else{
            		constructor = fragmentClass.getConstructor(Integer.class);
                    fragment = (Fragment) constructor.newInstance(type);
            	}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fragment;
    }

    public static final Creator<TabInfo> CREATOR = new Creator<TabInfo>() {
        public TabInfo createFromParcel(Parcel p) {
            return new TabInfo(p);
        }

        public TabInfo[] newArray(int size) {
            return new TabInfo[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel p, int flags) {
        p.writeInt(id);
        p.writeString(name);
        p.writeInt(icon);
        p.writeInt(notifyChange ? 1 : 0);
    }
}
