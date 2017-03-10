package com.susion.boring.interesting.model;

import com.susion.boring.interesting.inf.TitleMark;

import java.util.List;

/**
 * Created by susion on 17/3/9.
 */
public class DailyNews {

    /**
     * date : 20170118
     * stories : [{"images":["http://pic3.zhimg.com/d626aee39080e15f9a24ff2fa56771ba.jpg"],"type":0,"id":9158833,"ga_prefix":"011822","title":"小事 · 人来人往，书店如常"},{"images":["http://pic1.zhimg.com/465dbc66387d660aca0f199cbed7d334.jpg"],"type":0,"id":9158678,"ga_prefix":"011821","title":"- 想看点剧，不要英美日韩的\r\n- 诶我这还真有几部"},{"images":["http://pic4.zhimg.com/0102ecaea6eaa9fdc5931a9616107007.jpg"],"type":0,"id":9159250,"ga_prefix":"011820","title":"「投入与产出之间是成比例的」，这是个错觉"},{"images":["http://pic4.zhimg.com/106134d16fde34453649fb18b92e992f.jpg"],"type":0,"id":9159129,"ga_prefix":"011818","title":"市值 260 亿，账面现金却有 600 亿，投资乐视的融创是家怎样的公司？"},{"images":["http://pic4.zhimg.com/abe723fa1b542d1b05361f33dbc13d1b.jpg"],"type":0,"id":9158869,"ga_prefix":"011818","title":"税务局如何知道企业是否偷税漏税？"},{"images":["http://pic1.zhimg.com/fb95a15688ad2773f294ac86fafbdccc.jpg"],"type":0,"id":9158638,"ga_prefix":"011817","title":"每个人都想要发泄，我们该「消化」别人的情绪？"},{"images":["http://pic1.zhimg.com/bcf6cfdc357f5fa0ac903faea6ad8818.jpg"],"type":0,"id":9157157,"ga_prefix":"011816","title":"学一学怎么做卤肉，过年招待亲戚朋友"},{"title":"看过《最后的晚餐》，但没看得这么细，也没想这么多","ga_prefix":"011815","images":["http://pic4.zhimg.com/c142b3aa2e0dde2bca093d28eb11dedb.jpg"],"multipic":true,"type":0,"id":9158470},{"images":["http://pic2.zhimg.com/a41fe01ade1134df747bd2df70eb229d.jpg"],"type":0,"id":9158645,"ga_prefix":"011815","title":"新员工学到了东西就想走，问题出在哪儿了？"},{"images":["http://pic3.zhimg.com/9e3c9862b6eeb15858c3a9c7f36cb40a.jpg"],"type":0,"id":9158405,"ga_prefix":"011813","title":"最近很多 Apple ID 被锁，如何才能让帐号更安全？"},{"images":["http://pic1.zhimg.com/5fa212babed81e295b97c5dfb83481a4.jpg"],"type":0,"id":9155342,"ga_prefix":"011812","title":"大误 · 人生「恢复出厂设置」"},{"images":["http://pic2.zhimg.com/7e540455b279150bbcee49c09bd02441.jpg"],"type":0,"id":9157797,"ga_prefix":"011811","title":"想载人登陆火星，一定要突破「化学能动力时代」才行吗？"},{"images":["http://pic3.zhimg.com/037b92cb6eb027c8512a9d626ab94a12.jpg"],"type":0,"id":9149471,"ga_prefix":"011810","title":"最好的幼儿园应该是什么样子？"},{"images":["http://pic1.zhimg.com/3923df57633b76c70f85a7a80ae3d21c.jpg"],"type":0,"id":9157232,"ga_prefix":"011809","title":"刚刚穿上新的身体，我该怎么控制自己？"},{"images":["http://pic4.zhimg.com/55973836a6885752cd52de7136e82dd7.jpg"],"type":0,"id":9156669,"ga_prefix":"011808","title":"三国鼎立里的博弈，就像是一条蜈蚣"},{"images":["http://pic1.zhimg.com/94bc8747ae95b8d2287e6f615e0561ac.jpg"],"type":0,"id":9157284,"ga_prefix":"011808","title":"「教练，我想打篮球」"},{"images":["http://pic4.zhimg.com/da4d5d51a98737293b2695ecf23344f3.jpg"],"type":0,"id":9157287,"ga_prefix":"011807","title":"熬夜很累，为什么反而还有一点点兴奋？"},{"images":["http://pic1.zhimg.com/d8270c7b711da0ddb64830374f0f6858.jpg"],"type":0,"id":9157394,"ga_prefix":"011807","title":"先不管是不是真爱，起码一夫一妻有利于后代"},{"images":["http://pic3.zhimg.com/84799c353f8c02bdbb2888d6e5e45f22.jpg"],"type":0,"id":9155327,"ga_prefix":"011806","title":"瞎扯 · 如何正确地吐槽"}]
     */

    private String date;
    private List<StoriesBean> stories;

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

    public static class StoriesBean extends TitleMark {
        /**
         * images : ["http://pic3.zhimg.com/d626aee39080e15f9a24ff2fa56771ba.jpg"]
         * type : 0
         * id : 9158833
         * ga_prefix : 011822
         * title : 小事 · 人来人往，书店如常
         * multipic : true
         */

        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private boolean multipic;
        private List<String> images;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public boolean isMultipic() {
            return multipic;
        }

        public void setMultipic(boolean multipic) {
            this.multipic = multipic;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }


    }
}
