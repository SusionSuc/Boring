package com.susion.boring.interesting.mvp.model;

import com.susion.boring.base.mvp.model.TitleMark;

import java.util.Date;
import java.util.List;

/**
 * Created by susion on 17/3/9.
 */
public class DailyNews {


    /**
     * date : 20170315
     * stories : [{"images":["http://pic4.zhimg.com/c0832de2f69a627239a06bcc451939c7.jpg"],"type":0,"id":9287776,"ga_prefix":"031510","title":"对时间管理最大的误解，就是单纯地用来帮助自己提高效率"},{"images":["http://pic3.zhimg.com/092d20b56a499e16744198c35f07593a.jpg"],"type":0,"id":9288573,"ga_prefix":"031509","title":"画好眼妆很重要，连科学家都这么说"},{"images":["http://pic2.zhimg.com/861ca67fb3e75e48cd875c4ab6b8d065.jpg"],"type":0,"id":9287473,"ga_prefix":"031508","title":"现在掀起的这股反全球化浪潮，未必不是好事"},{"images":["http://pic4.zhimg.com/2d401282ca3da4b93aec0e830dddf237.jpg"],"type":0,"id":9286619,"ga_prefix":"031507","title":"153 亿美元，这是英特尔对自动驾驶愿景的最新押注"},{"images":["http://pic4.zhimg.com/def8f7267e98fa18dcfe5ab097a599ff.jpg"],"type":0,"id":9288561,"ga_prefix":"031507","title":"2017 年全球宏观市场可能会有哪些「黑天鹅」？"},{"images":["http://pic2.zhimg.com/7a9804d7c9281f6d8ac95879095bedc5.jpg"],"type":0,"id":9288332,"ga_prefix":"031507","title":"表面上放弃旗下电子商务控制权，乐视这步棋一举两得"},{"images":["http://pic4.zhimg.com/073e0477dc77b26c6324b2f448b7945f.jpg"],"type":0,"id":9287088,"ga_prefix":"031506","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic2.zhimg.com/57fbdb81e10eab15e1336f49f6c7d9e9.jpg","type":0,"id":9286619,"ga_prefix":"031507","title":"153 亿美元，这是英特尔对自动驾驶愿景的最新押注"},{"image":"http://pic3.zhimg.com/073209963e10d4e6f7a69a35259bfb96.jpg","type":0,"id":9288332,"ga_prefix":"031507","title":"表面上放弃旗下电子商务控制权，乐视这步棋一举两得"},{"image":"http://pic1.zhimg.com/c8e18dc255dda37265cc3598a9c7cf78.jpg","type":0,"id":9288573,"ga_prefix":"031509","title":"画好眼妆很重要，连科学家都这么说"},{"image":"http://pic2.zhimg.com/afdaf18bbc1b964aec95262bfa8be58d.jpg","type":0,"id":9286749,"ga_prefix":"031414","title":"腾讯刚借了 20 亿，怎么阿里巴巴也要借个 50 亿？"},{"image":"http://pic1.zhimg.com/48d590db8d0359b77584d1cfbf2ff3e8.jpg","type":0,"id":9285562,"ga_prefix":"031407","title":"京东到底赚没赚钱？看你怎么算"}]
     */

    private String date;
    private List<StoriesBean> stories;
    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean extends TitleMark{
        /**
         * images : ["http://pic4.zhimg.com/c0832de2f69a627239a06bcc451939c7.jpg"]
         * type : 0
         * id : 9287776
         * ga_prefix : 031510
         * title : 对时间管理最大的误解，就是单纯地用来帮助自己提高效率
         */

        private int type;
        private String id;
        private String ga_prefix;
        private String title;
        private List<String> images;

        public Date date;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesBean {
        /**
         * image : http://pic2.zhimg.com/57fbdb81e10eab15e1336f49f6c7d9e9.jpg
         * type : 0
         * id : 9286619
         * ga_prefix : 031507
         * title : 153 亿美元，这是英特尔对自动驾驶愿景的最新押注
         */

        private String image;
        private int type;
        private String id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
