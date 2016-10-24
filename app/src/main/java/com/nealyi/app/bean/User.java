package com.nealyi.app.bean;

/**
 * Created by nealyi on 16/10/21.
 */
public class User {
    private String muserName;

    private String muserNick;

    private int mavatarId;

    private String mavatarPath;

    private String mavatarSuffix;

    private int mavatarType;

    private String mavatarLastUpdateTime;

    public User() {
    }

    public User(String muserName, String muserNick, int mavatarId, String mavatarPath, String mavatarSuffix, int mavatarType, String mavatarLastUpdateTime) {
        this.muserName = muserName;
        this.muserNick = muserNick;
        this.mavatarId = mavatarId;
        this.mavatarPath = mavatarPath;
        this.mavatarSuffix = mavatarSuffix;
        this.mavatarType = mavatarType;
        this.mavatarLastUpdateTime = mavatarLastUpdateTime;
    }

    public void setMuserName(String muserName) {
        this.muserName = muserName;
    }

    public String getMuserName() {
        return this.muserName;
    }

    public void setMuserNick(String muserNick) {
        this.muserNick = muserNick;
    }

    public String getMuserNick() {
        return this.muserNick;
    }

    public void setMavatarId(int mavatarId) {
        this.mavatarId = mavatarId;
    }

    public int getMavatarId() {
        return this.mavatarId;
    }

    public void setMavatarPath(String mavatarPath) {
        this.mavatarPath = mavatarPath;
    }

    public String getMavatarPath() {
        return this.mavatarPath;
    }

    public void setMavatarSuffix(String mavatarSuffix) {
        this.mavatarSuffix = mavatarSuffix;
    }

    public String getMavatarSuffix() {
        return this.mavatarSuffix;
    }

    public void setMavatarType(int mavatarType) {
        this.mavatarType = mavatarType;
    }

    public int getMavatarType() {
        return this.mavatarType;
    }

    public void setMavatarLastUpdateTime(String mavatarLastUpdateTime) {
        this.mavatarLastUpdateTime = mavatarLastUpdateTime;
    }

    public String getMavatarLastUpdateTime() {
        return this.mavatarLastUpdateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "muserName='" + muserName + '\'' +
                ", muserNick='" + muserNick + '\'' +
                ", mavatarId=" + mavatarId +
                ", mavatarPath='" + mavatarPath + '\'' +
                ", mavatarSuffix='" + mavatarSuffix + '\'' +
                ", mavatarType=" + mavatarType +
                ", mavatarLastUpdateTime='" + mavatarLastUpdateTime + '\'' +
                '}';
    }
}
