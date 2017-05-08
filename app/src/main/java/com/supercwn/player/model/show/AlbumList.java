package com.supercwn.player.model.show;

import java.util.List;

/**
 * Created by JinYu on 2017/4/17.
 */

public class AlbumList {

    private List<AlllistBean> alllist;

    public List<AlllistBean> getAlllist() {
        return alllist;
    }

    public void setAlllist(List<AlllistBean> alllist) {
        this.alllist = alllist;
    }

    public static class AlllistBean {
        /**
         * id : 70
         * title : 爱情碟中谍
         * img : http://www.lajiaovod.com:22435/static/uploads/20160822/1471838610.png
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
