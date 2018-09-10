package com.international.wtw.lottery.json;

/**
 * Created by XIAOYAN on 2017/10/6.
 */

public class PreferentialProBean {

    private String image;
    private String text;
    private String link;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public PreferentialProBean() {
        super();
    }

    public PreferentialProBean(String image, String text, String link) {
        this.image = image;
        this.text = text;
        this.link = link;
    }

    @Override
    public String toString() {
        return "PreferentialProBean{" +
                "image='" + image + '\'' +
                ", text='" + text + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
