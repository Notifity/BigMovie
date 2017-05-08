package com.supercwn.player.model.show;

import java.util.List;

/**
 * Created by JinYu on 2017/4/17.
 */

public class AlbumAllList {

    private List<MoviesBean> movies;
    private List<TvplaysBean> tvplays;

    public List<MoviesBean> getMovies() {
        return movies;
    }

    public void setMovies(List<MoviesBean> movies) {
        this.movies = movies;
    }

    public List<TvplaysBean> getTvplays() {
        return tvplays;
    }

    public void setTvplays(List<TvplaysBean> tvplays) {
        this.tvplays = tvplays;
    }

    public static class MoviesBean {
        /**
         * id : 1069
         * title : 金刚：骷髅岛
         * img : http://www.lajiaovod.com:22435/static/uploads/20170410/1491819230.jpg
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

    public static class TvplaysBean {
        /**
         * id : 108
         * title : 越狱 第五季 （02）
         * img : http://www.lajiaovod.com:22435/static/uploads/20170405/1491398322.jpg
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
