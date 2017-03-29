package qc.hengda.com.myapplication;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */

public class CarInfo {
    public int id;
    public String name;
    public String staticPath;
    public String pic;
    public String pinyin;
    public List<ChildrenBeanX> children;

    public static class ChildrenBeanX {
        /**
         * id : 13
         * name : 阿斯顿·马丁
         * children : [{"id":19,"name":"Rapide","staticPath":"/auto/12/13/19","pinyin":"a"},{"id":21,"name":"阿斯顿·马丁DB9","staticPath":"/auto/12/13/21","pinyin":"a"},{"id":22,"name":"阿斯顿·马丁DBS","staticPath":"/auto/12/13/22","pinyin":"a"},{"id":1743,"name":"Virage","staticPath":"/auto/12/13/1743","pinyin":"a"},{"id":20,"name":"V12 Vantage","staticPath":"/auto/12/13/20","pinyin":"a"},{"id":761,"name":"V8 Vantage","staticPath":"/auto/12/13/761","pinyin":"a"}]
         * staticPath : /auto/12/13
         * pinyin : a
         */

        public int id;
        public String name;
        public String staticPath;
        public String pinyin;
        public List<ChildrenBean> children;
    }
    public static class ChildrenBean {
        /**
         * id : 19
         * name : Rapide
         * staticPath : /auto/12/13/19
         * pinyin : a
         */

        public int id;
        public String name;
        public String staticPath;
        public String pinyin;
    }

    @Override
    public String toString() {
        return "CarInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", staticPath='" + staticPath + '\'' +
                ", pic='" + pic + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", children=" + children +
                '}';
    }
}
