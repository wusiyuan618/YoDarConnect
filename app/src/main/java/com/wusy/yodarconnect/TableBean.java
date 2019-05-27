package com.wusy.yodarconnect;

import java.util.List;

public class TableBean {

    /**
     * ack : list.dirNodeList
     * arg : {"begin":0,"id":0,"nodeList":[{"id":1,"name":"SD(mmcblk0p1)","type":1}],"source":0,"total":1}
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
         * id : 0
         * nodeList : [{"id":1,"name":"SD(mmcblk0p1)","type":1}]
         * source : 0
         * total : 1
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
             * id : 1
             * name : SD(mmcblk0p1)
             * type : 1
             */

            private int id;
            private String name;
            private int type;

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
        }
    }
}
