package com.susion.boring.read.mvp.entity;

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
