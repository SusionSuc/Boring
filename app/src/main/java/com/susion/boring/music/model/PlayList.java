package com.susion.boring.music.model;

import java.util.List;

/**
 * Created by susion on 17/2/23.
 */
public class PlayList {

    /**
     * subscribers : []
     * subscribed : false
     * creator : {"authStatus":0,"expertTags":["华语","流行","民谣"],"city":330100,"province":330000,"avatarImgId":109951162864773834,"backgroundImgId":109951162865270437,"accountStatus":0,"detailDescription":"","djStatus":10,"backgroundUrl":"http://p1.music.126.net/87YkW_d5aB-6FiyoUvgS8g==/109951162865270437.jpg","remarkName":null,"mutual":false,"gender":1,"birthday":592156800000,"defaultAvatar":false,"followed":false,"userId":487355,"nickname":"虎锐","avatarUrl":"http://p3.music.126.net/iBgxffThljaXrlKK2F1ZLw==/109951162864773834.jpg","userType":0,"vipType":0,"description":"","avatarImgIdStr":"109951162864773834","backgroundImgIdStr":"109951162865270437","signature":"深夜拜访","authority":0}
     * artists : null
     * tracks : null
     * trackCount : 28
     * highQuality : false
     * status : 0
     * subscribedCount : 1056
     * privacy : 0
     * coverImgId : 109951162865325531
     * trackUpdateTime : 1487750513614
     * newImported : false
     * tags : ["欧美","流行","治愈"]
     * userId : 487355
     * updateTime : 1487832118437
     * createTime : 1472773550381
     * totalDuration : 0
     * coverImgUrl : http://p3.music.126.net/b-moDpMvTpZ0D2DCbkBW6A==/109951162865325531.jpg
     * specialType : 0
     * commentThreadId : A_PL_0_456257645
     * adType : 0
     * playCount : 86703
     * trackNumberUpdateTime : 1487749921644
     * description : 这早春一场雨，薄霭中还未能闻到泥土芬芳。心间事，在这飘渺蓊郁的林间。仿若心自远，地自偏。
     梦总是突然醒的，就像泡沫一般，越吹越大，最后啪地破灭，什么也没有，除了空虚。东野圭吾说。
     * cloudTrackCount : 0
     * name : 「欧美」打开胸膛，心里有朵被偷的玫瑰
     * id : 456257645
     * shareCount : 14
     * coverImgId_str : 109951162865325531
     * commentCount : 30
     */

    private boolean subscribed;
    private CreatorBean creator;
//    private Object artists;
//    private Object tracks;
    private int trackCount;
    private boolean highQuality;
    private int status;
    private int subscribedCount;
    private int privacy;
    private long coverImgId;
    private long trackUpdateTime;
    private boolean newImported;
    private int userId;
    private long updateTime;
    private long createTime;
    private int totalDuration;
    private String coverImgUrl;
    private int specialType;
    private String commentThreadId;
    private int adType;
    private int playCount;
    private long trackNumberUpdateTime;
    private String description;
    private int cloudTrackCount;
    private String name;
    private int id;
    private int shareCount;
    private long coverImgId_str;
    private int commentCount;
//    private List<?> subscribers;
    private List<String> tags;

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public CreatorBean getCreator() {
        return creator;
    }

    public void setCreator(CreatorBean creator) {
        this.creator = creator;
    }
//
//    public Object getArtists() {
//        return artists;
//    }
//
//    public void setArtists(Object artists) {
//        this.artists = artists;
//    }
//
//    public Object getTracks() {
//        return tracks;
//    }
//
//    public void setTracks(Object tracks) {
//        this.tracks = tracks;
//    }

    public int getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }

    public boolean isHighQuality() {
        return highQuality;
    }

    public void setHighQuality(boolean highQuality) {
        this.highQuality = highQuality;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSubscribedCount() {
        return subscribedCount;
    }

    public void setSubscribedCount(int subscribedCount) {
        this.subscribedCount = subscribedCount;
    }

    public int getPrivacy() {
        return privacy;
    }

    public void setPrivacy(int privacy) {
        this.privacy = privacy;
    }

    public long getCoverImgId() {
        return coverImgId;
    }

    public void setCoverImgId(long coverImgId) {
        this.coverImgId = coverImgId;
    }

    public long getTrackUpdateTime() {
        return trackUpdateTime;
    }

    public void setTrackUpdateTime(long trackUpdateTime) {
        this.trackUpdateTime = trackUpdateTime;
    }

    public boolean isNewImported() {
        return newImported;
    }

    public void setNewImported(boolean newImported) {
        this.newImported = newImported;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public int getSpecialType() {
        return specialType;
    }

    public void setSpecialType(int specialType) {
        this.specialType = specialType;
    }

    public String getCommentThreadId() {
        return commentThreadId;
    }

    public void setCommentThreadId(String commentThreadId) {
        this.commentThreadId = commentThreadId;
    }

    public int getAdType() {
        return adType;
    }

    public void setAdType(int adType) {
        this.adType = adType;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public long getTrackNumberUpdateTime() {
        return trackNumberUpdateTime;
    }

    public void setTrackNumberUpdateTime(long trackNumberUpdateTime) {
        this.trackNumberUpdateTime = trackNumberUpdateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCloudTrackCount() {
        return cloudTrackCount;
    }

    public void setCloudTrackCount(int cloudTrackCount) {
        this.cloudTrackCount = cloudTrackCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public long getCoverImgId_str() {
        return coverImgId_str;
    }

    public void setCoverImgId_str(long coverImgId_str) {
        this.coverImgId_str = coverImgId_str;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

//    public List<?> getSubscribers() {
//        return subscribers;
//    }
//
//    public void setSubscribers(List<?> subscribers) {
//        this.subscribers = subscribers;
//    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public static class CreatorBean {
        /**
         * authStatus : 0
         * expertTags : ["华语","流行","民谣"]
         * city : 330100
         * province : 330000
         * avatarImgId : 109951162864773834
         * backgroundImgId : 109951162865270437
         * accountStatus : 0
         * detailDescription :
         * djStatus : 10
         * backgroundUrl : http://p1.music.126.net/87YkW_d5aB-6FiyoUvgS8g==/109951162865270437.jpg
         * remarkName : null
         * mutual : false
         * gender : 1
         * birthday : 592156800000
         * defaultAvatar : false
         * followed : false
         * userId : 487355
         * nickname : 虎锐
         * avatarUrl : http://p3.music.126.net/iBgxffThljaXrlKK2F1ZLw==/109951162864773834.jpg
         * userType : 0
         * vipType : 0
         * description :
         * avatarImgIdStr : 109951162864773834
         * backgroundImgIdStr : 109951162865270437
         * signature : 深夜拜访
         * authority : 0
         */

        private int authStatus;
        private int city;
        private int province;
        private long avatarImgId;
        private long backgroundImgId;
        private int accountStatus;
        private String detailDescription;
        private int djStatus;
        private String backgroundUrl;
        private Object remarkName;
        private boolean mutual;
        private int gender;
        private long birthday;
        private boolean defaultAvatar;
        private boolean followed;
        private int userId;
        private String nickname;
        private String avatarUrl;
        private int userType;
        private int vipType;
        private String description;
        private String avatarImgIdStr;
        private String backgroundImgIdStr;
        private String signature;
        private int authority;
        private List<String> expertTags;

        public int getAuthStatus() {
            return authStatus;
        }

        public void setAuthStatus(int authStatus) {
            this.authStatus = authStatus;
        }

        public int getCity() {
            return city;
        }

        public void setCity(int city) {
            this.city = city;
        }

        public int getProvince() {
            return province;
        }

        public void setProvince(int province) {
            this.province = province;
        }

        public long getAvatarImgId() {
            return avatarImgId;
        }

        public void setAvatarImgId(long avatarImgId) {
            this.avatarImgId = avatarImgId;
        }

        public long getBackgroundImgId() {
            return backgroundImgId;
        }

        public void setBackgroundImgId(long backgroundImgId) {
            this.backgroundImgId = backgroundImgId;
        }

        public int getAccountStatus() {
            return accountStatus;
        }

        public void setAccountStatus(int accountStatus) {
            this.accountStatus = accountStatus;
        }

        public String getDetailDescription() {
            return detailDescription;
        }

        public void setDetailDescription(String detailDescription) {
            this.detailDescription = detailDescription;
        }

        public int getDjStatus() {
            return djStatus;
        }

        public void setDjStatus(int djStatus) {
            this.djStatus = djStatus;
        }

        public String getBackgroundUrl() {
            return backgroundUrl;
        }

        public void setBackgroundUrl(String backgroundUrl) {
            this.backgroundUrl = backgroundUrl;
        }

        public Object getRemarkName() {
            return remarkName;
        }

        public void setRemarkName(Object remarkName) {
            this.remarkName = remarkName;
        }

        public boolean isMutual() {
            return mutual;
        }

        public void setMutual(boolean mutual) {
            this.mutual = mutual;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public long getBirthday() {
            return birthday;
        }

        public void setBirthday(long birthday) {
            this.birthday = birthday;
        }

        public boolean isDefaultAvatar() {
            return defaultAvatar;
        }

        public void setDefaultAvatar(boolean defaultAvatar) {
            this.defaultAvatar = defaultAvatar;
        }

        public boolean isFollowed() {
            return followed;
        }

        public void setFollowed(boolean followed) {
            this.followed = followed;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public int getVipType() {
            return vipType;
        }

        public void setVipType(int vipType) {
            this.vipType = vipType;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAvatarImgIdStr() {
            return avatarImgIdStr;
        }

        public void setAvatarImgIdStr(String avatarImgIdStr) {
            this.avatarImgIdStr = avatarImgIdStr;
        }

        public String getBackgroundImgIdStr() {
            return backgroundImgIdStr;
        }

        public void setBackgroundImgIdStr(String backgroundImgIdStr) {
            this.backgroundImgIdStr = backgroundImgIdStr;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public int getAuthority() {
            return authority;
        }

        public void setAuthority(int authority) {
            this.authority = authority;
        }

        public List<String> getExpertTags() {
            return expertTags;
        }

        public void setExpertTags(List<String> expertTags) {
            this.expertTags = expertTags;
        }
    }
}
