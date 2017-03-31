package com.susion.boring.read.mvp.model;

import java.util.List;

/**
 * Created by susion on 17/3/26.
 */
public class PictureCategoryResult {


    private int showapi_res_code;
    private String showapi_res_error;
    private CategoryList showapi_res_body;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public CategoryList getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(CategoryList showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class CategoryList {
        /**
         * ret_code : 0
         * list : [{"name":"社会百态","list":[{"id":1001,"name":"社会新闻"},{"id":1002,"name":"国内新闻"},{"id":1003,"name":"环球动态"},{"id":1004,"name":"军事新闻"}]},{"name":"明星写真","list":[{"id":2001,"name":"中国明星"},{"id":2002,"name":"欧美明星"},{"id":2003,"name":"中国女明星"},{"id":2004,"name":"中国男明星"},{"id":2005,"name":"韩国明星"},{"id":2006,"name":"欧美女明星"},{"id":2007,"name":"欧美男明星"}]},{"name":"娱乐八卦","list":[{"id":3001,"name":"娱乐组图"},{"id":3002,"name":"八卦爆料"},{"id":3003,"name":"电影海报"},{"id":3004,"name":"影视剧照"},{"id":3005,"name":"活动现场"}]},{"name":"美女图片","list":[{"id":4001,"name":"清纯"},{"id":4002,"name":"气质"},{"id":4003,"name":"萌女"},{"id":4004,"name":"校花"},{"id":4005,"name":"婚纱"},{"id":4006,"name":"街拍"},{"id":4007,"name":"非主流"},{"id":4008,"name":"美腿"},{"id":4009,"name":"性感"},{"id":4010,"name":"车模"},{"id":4011,"name":"男色图片"},{"id":4012,"name":"模特美女"},{"id":4013,"name":"美女魅惑"},{"id":4014,"name":"日韩美女"}]},{"name":"时尚伊人","list":[{"id":5001,"name":"秀场"},{"id":5002,"name":"霓裳"},{"id":5003,"name":"鞋包"},{"id":5004,"name":"婚嫁"},{"id":5005,"name":"妆容"},{"id":5006,"name":"广告大片"}]},{"name":"生活趣味","list":[{"id":6001,"name":"居家"},{"id":6002,"name":"萌宠"},{"id":6003,"name":"美食图片"},{"id":6004,"name":"美丽风景"},{"id":6005,"name":"奇趣自然"},{"id":6006,"name":"植物花卉"}]},{"name":"猎奇图片","list":[{"id":7001,"name":"猎奇图片"},{"id":7002,"name":"世界溶洞奇观"},{"id":7003,"name":"3D博物馆"},{"id":7004,"name":"日本绳缚艺术"}]}]
         */

        private int ret_code;
        private List<PictureCategory> list;

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public List<PictureCategory> getList() {
            return list;
        }

        public void setList(List<PictureCategory> list) {
            this.list = list;
        }

        public static class PictureCategory {
            /**
             * name : 社会百态
             * list : [{"id":1001,"name":"社会新闻"},{"id":1002,"name":"国内新闻"},{"id":1003,"name":"环球动态"},{"id":1004,"name":"军事新闻"}]
             */

            private String name;
            private List<Column> list;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<Column> getList() {
                return list;
            }

            public void setList(List<Column> list) {
                this.list = list;
            }

            public static class Column {
                /**
                 * id : 1001
                 * name : 社会新闻
                 */

                private String id;
                private String name;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
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
}
