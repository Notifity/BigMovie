package com.supercwn.player.model.show;

import java.util.List;

/**
 * Created by JinYu on 2017/4/16.
 */

public class DetailInfo {
    /**
     * id : 411
     * score : 3.0
     * title : 封神传奇
     * director : 许安
     * actor : 李连杰 / 范冰冰 / 黄晓明 / 杨颖 / 古天乐 / 文章 / 向佐 / 梁家辉 / 祖峰 /
     * info : 三千年前，商末，昏君纣王（梁家辉 饰）受妖妃妲己（范冰冰 饰）蛊惑，联手申公豹（古天乐 饰），试图召唤黑暗力量降生灭世黑龙，一时间妖孽横行，民不聊……
     * exptime : 2016-07-29
     * type : 动作剧情奇幻
     * area : 香港 / 中国大陆
     * img : http://www.lajiaovod.com:22435/static/uploads/20160910/1473463824.png
     * quality : 1080p
     * duration : 109
     * list : [{"title":"封神传奇","url":"http://cdn.xishiwed.com/movie/20160910/League of Gods 2016 2160p chs.mp4"}]
     * reclist : [{"id":"664","title":"江湖水 ","img":"http://www.lajiaovod.com:22435/static/uploads/20161208/1481194115.jpg"},{"id":"781","title":"三少爷的剑 ","img":"http://www.lajiaovod.com:22435/static/uploads/20170114/1484391492.jpg"},{"id":"661","title":"毁灭者 ","img":"http://www.lajiaovod.com:22435/static/uploads/20161207/1481107243.jpg"},{"id":"1093","title":"江湖之义字当先 ","img":"http://www.lajiaovod.com:22435/static/uploads/20170415/1492261004.jpg"},{"id":"1083","title":"西游伏妖篇 ","img":"http://www.lajiaovod.com:22435/static/uploads/20170414/1492176312.jpg"},{"id":"1086","title":"金刚狼3：殊死一战 ","img":"http://www.lajiaovod.com:22435/static/uploads/20170415/1492260442.jpg"},{"id":"1088","title":"游戏规则 ","img":"http://www.lajiaovod.com:22435/static/uploads/20170415/1492260480.jpg"},{"id":"1089","title":"守护者：世纪战元 ","img":"http://www.lajiaovod.com:22435/static/uploads/20170415/1492260549.jpg"}]
     */

    private String id;
    private String score;
    private String title;
    private String director;
    private String actor;
    private String info;
    private String exptime;
    private String type;
    private String area;
    private String img;
    private String quality;
    private String duration;
    private List<ListBean> list;
    private List<ReclistBean> reclist;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getExptime() {
        return exptime;
    }

    public void setExptime(String exptime) {
        this.exptime = exptime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<ReclistBean> getReclist() {
        return reclist;
    }

    public void setReclist(List<ReclistBean> reclist) {
        this.reclist = reclist;
    }

    public static class ListBean {
        /**
         * title : 封神传奇
         * url : http://cdn.xishiwed.com/movie/20160910/League of Gods 2016 2160p chs.mp4
         */

        private String title;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class ReclistBean {
        /**
         * id : 664
         * title : 江湖水
         * img : http://www.lajiaovod.com:22435/static/uploads/20161208/1481194115.jpg
         */

        private String id;
        private String title;
        private String img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
