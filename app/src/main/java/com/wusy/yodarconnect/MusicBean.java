package com.wusy.yodarconnect;

import java.util.List;

public class MusicBean {

    /**
     * ack : list.dirNodeList
     * arg : {"begin":0,"id":1,"nodeList":[{"album":"mmcblk0p1","id":0,"name":"DJ Okawari - Flower Dance"},{"album":"mmcblk0p1","id":1,"name":"Elvins.J - HearthRock聽(鎽囨粴鐗堢倝鐭充紶璇翠富棰樻洸)"},{"album":"mmcblk0p1","id":2,"name":"FIELD OF VIEW - DAN DAN 蹇冮瓍銇嬨倢銇︺亸"},{"album":"mmcblk0p1","id":3,"name":"John The Whistler - Wild Wild Web"},{"album":"mmcblk0p1","id":4,"name":"Linked Horizon - 蹇冭嚀銈掓崸銇掋倛!"},{"album":"mmcblk0p1","id":5,"name":"Mark Petrie - Go Time"}],"source":0,"total":22}
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

    public static class ArgBean {
        /**
         * begin : 0
         * id : 1
         * nodeList : [{"album":"mmcblk0p1","id":0,"name":"DJ Okawari - Flower Dance"},{"album":"mmcblk0p1","id":1,"name":"Elvins.J - HearthRock聽(鎽囨粴鐗堢倝鐭充紶璇翠富棰樻洸)"},{"album":"mmcblk0p1","id":2,"name":"FIELD OF VIEW - DAN DAN 蹇冮瓍銇嬨倢銇︺亸"},{"album":"mmcblk0p1","id":3,"name":"John The Whistler - Wild Wild Web"},{"album":"mmcblk0p1","id":4,"name":"Linked Horizon - 蹇冭嚀銈掓崸銇掋倛!"},{"album":"mmcblk0p1","id":5,"name":"Mark Petrie - Go Time"}]
         * source : 0
         * total : 22
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

        public static class NodeListBean {
            /**
             * album : mmcblk0p1
             * id : 0
             * name : DJ Okawari - Flower Dance
             */

            private String album;
            private int id;
            private String name;

            public String getAlbum() {
                return album;
            }

            public void setAlbum(String album) {
                this.album = album;
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
        }
    }
}
