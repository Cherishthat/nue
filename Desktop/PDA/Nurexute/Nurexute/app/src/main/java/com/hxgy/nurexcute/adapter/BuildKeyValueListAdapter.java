package com.hxgy.nurexcute.adapter;

/**
 * Created by sky on 2016/8/2.
 * function:实现对下拉列表实现键值对
 */
public class BuildKeyValueListAdapter {
    private String Key;
    private String Value;

    public BuildKeyValueListAdapter(String key,String value)
    {
        this.Key=key;
        this.Value=value;
    }

    public void setKey(String key) {
        Key = key;
    }

    public void setValue(String value) {
        Value = value;
    }
    public String getKey()
    {
        return Key;
    }
    public String getValue()
    {
        return Value;
    }
    public String toString()
    {
        return Value;
    }
}
