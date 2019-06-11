package com.wusy.yodarconnect.bean;

import java.io.Serializable;
import java.util.List;

public class TableBean implements Serializable {
    /**
     * ack : list.dirNodeList
     * arg : {"begin":0,"id":0,"nodeList":[{"id":1,"name":"新歌","type":1},{"id":2,"name":"排行榜","type":1},{"id":3,"name":"电台","type":1},{"id":4,"name":"歌单","type":1},{"id":5,"name":"分类","type":1},{"id":6,"name":"歌手","type":1}],"source":5,"total":8}
     */

    private String ack;
    private ArgBean arg;

    public String getAck() {
        return ack;
    }

    public void setAck(String ack) {
        this.ack = ack;
    }

    public ArgBean getArg() {
        return arg;
    }

    public void setArg(ArgBean arg) {
        this.arg = arg;
    }

    public static class ArgBean implements Serializable {
        /**
         * begin : 0
         * id : 0
         * nodeList : [{"id":1,"name":"新歌","type":1},{"id":2,"name":"排行榜","type":1},{"id":3,"name":"电台","type":1},{"id":4,"name":"歌单","type":1},{"id":5,"name":"分类","type":1},{"id":6,"name":"歌手","type":1}]
         * source : 5
         * total : 8
         */

        private int begin;
        private int id;
        private int source;
        private int total;
        private List<NodeListBean> nodeList;

        public int getBegin() {
            return begin;
        }

        public void setBegin(int begin) {
            this.begin = begin;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<NodeListBean> getNodeList() {
            return nodeList;
        }

        public void setNodeList(List<NodeListBean> nodeList) {
            this.nodeList = nodeList;
        }

        public static class NodeListBean implements Serializable {
            /**
             * id : 1
             * name : 新歌
             * type : 1
             */

            private int id;
            private String name;
            private int type;
            private String picUrl;
            private String artist;

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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getArtist() {
                return artist;
            }

            public void setArtist(String artist) {
                this.artist = artist;
            }
        }
    }
}
