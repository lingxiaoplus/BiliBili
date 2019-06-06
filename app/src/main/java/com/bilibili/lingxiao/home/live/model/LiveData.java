package com.bilibili.lingxiao.home.live.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class LiveData {
    private List<BannerBean> banner;
    private List<EntranceIconsBean> entranceIcons;
    private List<PartitionsBean> partitions;
    private RecommendDataBean recommend_data;

    public RecommendDataBean getRecommend_data() {
        return recommend_data;
    }

    public void setRecommend_data(RecommendDataBean recommend_data) {
        this.recommend_data = recommend_data;
    }

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<EntranceIconsBean> getEntranceIcons() {
        return entranceIcons;
    }

    public void setEntranceIcons(List<EntranceIconsBean> entranceIcons) {
        this.entranceIcons = entranceIcons;
    }

    public List<PartitionsBean> getPartitions() {
        return partitions;
    }

    public void setPartitions(List<PartitionsBean> partitions) {
        this.partitions = partitions;
    }



    public static class RecommendDataBean {

        private PartitionBean partition;
        private List<LivesBean> lives;
        private List<BannerDataBean> banner_data;

        public PartitionBean getPartition() {
            return partition;
        }

        public void setPartition(PartitionBean partition) {
            this.partition = partition;
        }

        public List<LivesBean> getLives() {
            return lives;
        }

        public void setLives(List<LivesBean> lives) {
            this.lives = lives;
        }

        public List<BannerDataBean> getBanner_data() {
            return banner_data;
        }

        public void setBanner_data(List<BannerDataBean> banner_data) {
            this.banner_data = banner_data;
        }

        public static class PartitionBean {
            /**
             * id : 0
             * name : 推荐主播
             * area : hot
             * sub_icon : {"src":"http://static.hdslb.com/live-static/images/mobile/android/small/xxhdpi/-1.png?20171116172700","height":"63","width":"63"}
             * count : 2718
             */

            private int id;
            private String name;
            private String area;
            private SubIconBean sub_icon;
            private int count;

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

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public SubIconBean getSub_icon() {
                return sub_icon;
            }

            public void setSub_icon(SubIconBean sub_icon) {
                this.sub_icon = sub_icon;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public static class SubIconBean {
                /**
                 * src : http://static.hdslb.com/live-static/images/mobile/android/small/xxhdpi/-1.png?20171116172700
                 * height : 63
                 * width : 63
                 */

                private String src;
                private String height;
                private String width;

                public String getSrc() {
                    return src;
                }

                public void setSrc(String src) {
                    this.src = src;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }
            }
        }

        public static class LivesBean {
            /**
             * owner : {"face":"http://i1.hdslb.com/bfs/face/8c49a758216f9bd14b0046afe48a3514f44126f0.jpg","mid":110631,"name":"宫本狗雨"}
             * cover : {"src":"http://i0.hdslb.com/bfs/live/63602c757dd6aaf2f498cb3d44b47fced6589a1e.jpg","height":180,"width":320}
             * room_id : 5279
             * check_version : 0
             * online : 137592
             * area : 电子竞技
             * area_id : 4
             * title : 新版本天使？
             * playurl : http://ws.live-play.acgvideo.com/live-ws/192264/live_110631_5953326.flv?wsSecret=af81b46037fbe718d61c86bd1d5f83e5&wsTime=1552361203&trid=5c8a82b44da94db4b26ce3e478acb5dd&sig=no
             * accept_quality : 4
             * broadcast_type : 0
             * is_tv : 0
             * area_v2_id : 86
             * area_v2_name : 英雄联盟
             * area_v2_parent_id : 2
             * area_v2_parent_name : 网游
             */

            private OwnerBean owner;
            private CoverBean cover;
            private int room_id;
            private int check_version;
            private int online;
            private String area;
            private int area_id;
            private String title;
            private String playurl;
            private String accept_quality;
            private int broadcast_type;
            private int is_tv;
            private int area_v2_id;
            private String area_v2_name;
            private int area_v2_parent_id;
            private String area_v2_parent_name;

            public OwnerBean getOwner() {
                return owner;
            }

            public void setOwner(OwnerBean owner) {
                this.owner = owner;
            }

            public CoverBean getCover() {
                return cover;
            }

            public void setCover(CoverBean cover) {
                this.cover = cover;
            }

            public int getRoom_id() {
                return room_id;
            }

            public void setRoom_id(int room_id) {
                this.room_id = room_id;
            }

            public int getCheck_version() {
                return check_version;
            }

            public void setCheck_version(int check_version) {
                this.check_version = check_version;
            }

            public int getOnline() {
                return online;
            }

            public void setOnline(int online) {
                this.online = online;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public int getArea_id() {
                return area_id;
            }

            public void setArea_id(int area_id) {
                this.area_id = area_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPlayurl() {
                return playurl;
            }

            public void setPlayurl(String playurl) {
                this.playurl = playurl;
            }

            public String getAccept_quality() {
                return accept_quality;
            }

            public void setAccept_quality(String accept_quality) {
                this.accept_quality = accept_quality;
            }

            public int getBroadcast_type() {
                return broadcast_type;
            }

            public void setBroadcast_type(int broadcast_type) {
                this.broadcast_type = broadcast_type;
            }

            public int getIs_tv() {
                return is_tv;
            }

            public void setIs_tv(int is_tv) {
                this.is_tv = is_tv;
            }

            public int getArea_v2_id() {
                return area_v2_id;
            }

            public void setArea_v2_id(int area_v2_id) {
                this.area_v2_id = area_v2_id;
            }

            public String getArea_v2_name() {
                return area_v2_name;
            }

            public void setArea_v2_name(String area_v2_name) {
                this.area_v2_name = area_v2_name;
            }

            public int getArea_v2_parent_id() {
                return area_v2_parent_id;
            }

            public void setArea_v2_parent_id(int area_v2_parent_id) {
                this.area_v2_parent_id = area_v2_parent_id;
            }

            public String getArea_v2_parent_name() {
                return area_v2_parent_name;
            }

            public void setArea_v2_parent_name(String area_v2_parent_name) {
                this.area_v2_parent_name = area_v2_parent_name;
            }

            public static class OwnerBean {
                /**
                 * face : http://i1.hdslb.com/bfs/face/8c49a758216f9bd14b0046afe48a3514f44126f0.jpg
                 * mid : 110631
                 * name : 宫本狗雨
                 */

                private String face;
                private int mid;
                private String name;

                public String getFace() {
                    return face;
                }

                public void setFace(String face) {
                    this.face = face;
                }

                public int getMid() {
                    return mid;
                }

                public void setMid(int mid) {
                    this.mid = mid;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class CoverBean {
                /**
                 * src : http://i0.hdslb.com/bfs/live/63602c757dd6aaf2f498cb3d44b47fced6589a1e.jpg
                 * height : 180
                 * width : 320
                 */

                private String src;
                private int height;
                private int width;

                public String getSrc() {
                    return src;
                }

                public void setSrc(String src) {
                    this.src = src;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }
            }
        }

        public static class BannerDataBean {
            /**
             * owner : {"face":"http://i2.hdslb.com/bfs/face/5d35da6e93fbfb1a77ad6d1f1004b08413913f9a.jpg","mid":11153765,"name":"3号直播间"}
             * cover : {"src":"http://i0.hdslb.com/bfs/live/98c268f907705241a3e3face25ae365e3120406b.png","height":180,"width":320}
             * room_id : 23058
             * check_version : 0
             * online : 10249
             * area : 放映厅
             * area_id : 7
             * title : 哔哩哔哩音悦台
             * playurl : http://qn.live-play.acgvideo.com/live-qn/686976/live_11153765_9369560.flv?wsSecret=6afca689c0b3f203f783b9fb0ad3ca7e&wsTime=1552361162&trid=876262f11acf4e668cf3b74967eb3fc1&sig=no
             * accept_quality : 4
             * broadcast_type : 0
             * is_tv : 0
             * area_v2_id : 34
             * area_v2_name : 音乐台
             * area_v2_parent_id : 1
             * area_v2_parent_name : 娱乐
             */

            private OwnerBeanX owner;
            private CoverBeanX cover;
            private int room_id;
            private int check_version;
            private int online;
            private String area;
            private int area_id;
            private String title;
            private String playurl;
            private String accept_quality;
            private int broadcast_type;
            private int is_tv;
            private int area_v2_id;
            private String area_v2_name;
            private int area_v2_parent_id;
            private String area_v2_parent_name;

            public OwnerBeanX getOwner() {
                return owner;
            }

            public void setOwner(OwnerBeanX owner) {
                this.owner = owner;
            }

            public CoverBeanX getCover() {
                return cover;
            }

            public void setCover(CoverBeanX cover) {
                this.cover = cover;
            }

            public int getRoom_id() {
                return room_id;
            }

            public void setRoom_id(int room_id) {
                this.room_id = room_id;
            }

            public int getCheck_version() {
                return check_version;
            }

            public void setCheck_version(int check_version) {
                this.check_version = check_version;
            }

            public int getOnline() {
                return online;
            }

            public void setOnline(int online) {
                this.online = online;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public int getArea_id() {
                return area_id;
            }

            public void setArea_id(int area_id) {
                this.area_id = area_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPlayurl() {
                return playurl;
            }

            public void setPlayurl(String playurl) {
                this.playurl = playurl;
            }

            public String getAccept_quality() {
                return accept_quality;
            }

            public void setAccept_quality(String accept_quality) {
                this.accept_quality = accept_quality;
            }

            public int getBroadcast_type() {
                return broadcast_type;
            }

            public void setBroadcast_type(int broadcast_type) {
                this.broadcast_type = broadcast_type;
            }

            public int getIs_tv() {
                return is_tv;
            }

            public void setIs_tv(int is_tv) {
                this.is_tv = is_tv;
            }

            public int getArea_v2_id() {
                return area_v2_id;
            }

            public void setArea_v2_id(int area_v2_id) {
                this.area_v2_id = area_v2_id;
            }

            public String getArea_v2_name() {
                return area_v2_name;
            }

            public void setArea_v2_name(String area_v2_name) {
                this.area_v2_name = area_v2_name;
            }

            public int getArea_v2_parent_id() {
                return area_v2_parent_id;
            }

            public void setArea_v2_parent_id(int area_v2_parent_id) {
                this.area_v2_parent_id = area_v2_parent_id;
            }

            public String getArea_v2_parent_name() {
                return area_v2_parent_name;
            }

            public void setArea_v2_parent_name(String area_v2_parent_name) {
                this.area_v2_parent_name = area_v2_parent_name;
            }

            public static class OwnerBeanX {
                /**
                 * face : http://i2.hdslb.com/bfs/face/5d35da6e93fbfb1a77ad6d1f1004b08413913f9a.jpg
                 * mid : 11153765
                 * name : 3号直播间
                 */

                private String face;
                private int mid;
                private String name;

                public String getFace() {
                    return face;
                }

                public void setFace(String face) {
                    this.face = face;
                }

                public int getMid() {
                    return mid;
                }

                public void setMid(int mid) {
                    this.mid = mid;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class CoverBeanX {
                /**
                 * src : http://i0.hdslb.com/bfs/live/98c268f907705241a3e3face25ae365e3120406b.png
                 * height : 180
                 * width : 320
                 */

                private String src;
                private int height;
                private int width;

                public String getSrc() {
                    return src;
                }

                public void setSrc(String src) {
                    this.src = src;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }
            }
        }
    }

    public static class BannerBean {
        /**
         * id : 913
         * pic : http://i0.hdslb.com/bfs/vc/7564c8b074a4ece54c23e10660c520e300cae782.jpg
         * link : https://www.bilibili.com/blackboard/activity-mdG8HVNeU.html
         * title : 超新星学员计划
         * position : 4
         * sort_num : 1
         * img : http://i0.hdslb.com/bfs/vc/7564c8b074a4ece54c23e10660c520e300cae782.jpg
         */

        private String id;
        private String pic;
        private String link;
        private String title;
        private String position;
        private String sort_num;
        private String img;
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getSort_num() {
            return sort_num;
        }

        public void setSort_num(String sort_num) {
            this.sort_num = sort_num;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }

    public static class EntranceIconsBean {
        /**
         * id : 9
         * name : 绘画专区
         * entrance_icon : {"src":"http://static.hdslb.com/live-static/images/mobile/android/big/xxhdpi/9_big.png?20171116172700","height":"132","width":"132"}
         */

        private int id;
        private String name;
        private EntranceIconBean entrance_icon;

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

        public EntranceIconBean getEntrance_icon() {
            return entrance_icon;
        }

        public void setEntrance_icon(EntranceIconBean entrance_icon) {
            this.entrance_icon = entrance_icon;
        }

        public static class EntranceIconBean {
            /**
             * src : http://static.hdslb.com/live-static/images/mobile/android/big/xxhdpi/9_big.png?20171116172700
             * height : 132
             * width : 132
             */

            private String src;
            private String height;
            private String width;

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }
        }
    }

    public static class PartitionsBean {
        /**
         * partition : {"id":9,"name":"绘画专区","area":"draw","sub_icon":{"src":"http://static.hdslb.com/live-static/images/mobile/android/small/xxhdpi/9.png?20171116172700","height":"63","width":"63"},"count":92}
         * lives : [{"owner":{"face":"http://i1.hdslb.com/bfs/face/6c80fa10147b4481688bcaee61ff052fde0b4bdc.jpg","mid":9871569,"name":"小逝lullaby"},"cover":{"src":"http://i0.hdslb.com/bfs/live/e02e3b4f71fa8c57793d70fbccb6edbd5883d14a.jpg","height":180,"width":320},"title":"封⭐面是我画的，不用问了\u2026","room_id":73088,"check_version":0,"online":487,"area":"绘画专区","area_id":9,"playurl":"http://ws.live-play.acgvideo.com/live-ws/150694/live_9871569_3091457.flv?wsSecret=4b788f2328d20d0c0b9437d71e2d4382&wsTime=1552361161&trid=63901c288ca44742ab2f55db64af616a&sig=no","accept_quality":"4","broadcast_type":0,"is_tv":0},{"owner":{"face":"http://i1.hdslb.com/bfs/face/8fdff55db668991462065f2507713939ff115bd3.jpg","mid":1668013,"name":"戴腕表的怒龙道"},"cover":{"src":"http://i0.hdslb.com/bfs/live/user_cover/5b6910d2bdedc73c15691ba3a89a91d208194016.jpg","height":180,"width":320},"title":"瞎播测试","room_id":1152924,"check_version":0,"online":10,"area":"绘画专区","area_id":9,"playurl":"http://ws.live-play.acgvideo.com/live-ws/102457/live_1668013_3398863.flv?wsSecret=72e9fb1cde73b74a0f71e63e9c04133a&wsTime=1552361161&trid=63901c288ca44742ab2f55db64af616a&sig=no","accept_quality":"4","broadcast_type":0,"is_tv":0},{"owner":{"face":"http://i1.hdslb.com/bfs/face/ce8af5ba39f59dcd898ed59dbc26e9d0021a3ce1.jpg","mid":35271215,"name":"PumpkinJakk-金鱼"},"cover":{"src":"http://i0.hdslb.com/bfs/live/bff83d0b5a86e4654fc6ac08c866aa667d22f1db.jpg","height":180,"width":320},"title":"【金鱼】玄羽恋歌","room_id":472538,"check_version":0,"online":176,"area":"绘画专区","area_id":9,"playurl":"http://ws.live-play.acgvideo.com/live-ws/422797/live_35271215_8431452.flv?wsSecret=a81c8b69bdfa4aed1d540c33dc0ff3d3&wsTime=1552361161&trid=63901c288ca44742ab2f55db64af616a&sig=no","accept_quality":"4","broadcast_type":0,"is_tv":0},{"owner":{"face":"http://i0.hdslb.com/bfs/face/a1818c874392a6f203d832f70a87fddc6bc6b1f7.jpg","mid":25972035,"name":"肉松馅的蛋烘糕"},"cover":{"src":"http://i0.hdslb.com/bfs/live/room_cover/db559be4044b11e2384907bb8402569eac10e8c3.jpg","height":180,"width":320},"title":"听歌画画\u2014战斗天使","room_id":4290727,"check_version":0,"online":68,"area":"绘画专区","area_id":9,"playurl":"http://ws.live-play.acgvideo.com/live-ws/165867/live_25972035_3513791.flv?wsSecret=16b19213250a04a234c1e815ff9ab24f&wsTime=1552361161&trid=63901c288ca44742ab2f55db64af616a&sig=no","accept_quality":"4","broadcast_type":0,"is_tv":0},{"owner":{"face":"http://i2.hdslb.com/bfs/face/1c3f961754cadf27dfc961b3b6692bea7e0bf269.jpg","mid":1972394,"name":"蒙塔基的钢蛋儿_"},"cover":{"src":"http://i0.hdslb.com/bfs/live/d28d9e54cd8e5a2fd6fa525a4dad323e3cccf720.jpg","height":180,"width":320},"title":"暴躁小画家","room_id":946340,"check_version":0,"online":52,"area":"绘画专区","area_id":9,"playurl":"http://ws.live-play.acgvideo.com/live-ws/700399/live_1972394_8085638.flv?wsSecret=cd9dfc564ddc77374897d4283a4dd17f&wsTime=1552361161&trid=63901c288ca44742ab2f55db64af616a&sig=no","accept_quality":"4","broadcast_type":0,"is_tv":0},{"owner":{"face":"http://i1.hdslb.com/bfs/face/c04066b823ee54bc9e33e0816c6427ada986c293.jpg","mid":534128,"name":"harrymiao"},"cover":{"src":"http://i0.hdslb.com/bfs/live/ce0e1b42ece824bc94100c2ee547d98613ba11b3.jpg","height":180,"width":320},"title":"harrymiao的直播间","room_id":936402,"check_version":0,"online":1294,"area":"绘画专区","area_id":9,"playurl":"http://ws.live-play.acgvideo.com/live-ws/126418/live_534128_3438840.flv?wsSecret=b4b95993ef9af3f16679246e141e5a4b&wsTime=1552361161&trid=63901c288ca44742ab2f55db64af616a&sig=no","accept_quality":"4","broadcast_type":0,"is_tv":0},{"owner":{"face":"http://i2.hdslb.com/bfs/face/7449c484755309bc89d6bd68fb5d73ff7f6316e2.jpg","mid":20002577,"name":"大河庄一世繁华"},"cover":{"src":"http://i0.hdslb.com/bfs/live/room_cover/edec2128180f074a95446dc4986c5e14274130ed.jpg","height":180,"width":320},"title":"南小鸟白丝--花嫁折纸","room_id":465495,"check_version":0,"online":2238,"area":"绘画专区","area_id":9,"playurl":"http://ws.live-play.acgvideo.com/live-ws/496273/live_20002577_3735661.flv?wsSecret=5aca8343597fa49f52f6f02727ea8944&wsTime=1552361161&trid=63901c288ca44742ab2f55db64af616a&sig=no","accept_quality":"4","broadcast_type":0,"is_tv":0},{"owner":{"face":"http://i0.hdslb.com/bfs/face/202a3aff95f7835e3992786ab61c325ba109030a.jpg","mid":26583489,"name":"云度动漫代咕咕"},"cover":{"src":"http://i0.hdslb.com/bfs/live/e6f273d1c54e8502b591c9aebce86e149ca5a805.jpg","height":180,"width":320},"title":"【昆明云度】画师之友电台","room_id":94946,"check_version":0,"online":7450,"area":"绘画专区","area_id":9,"playurl":"http://ws.live-play.acgvideo.com/live-ws/120126/live_26583489_8892733.flv?wsSecret=931e52a3554b73137a8be27cd981f720&wsTime=1552361161&trid=63901c288ca44742ab2f55db64af616a&sig=no","accept_quality":"4","broadcast_type":0,"is_tv":0},{"owner":{"face":"http://i0.hdslb.com/bfs/face/a2df382ad145f1f4418ea4e5123477656e4221a1.jpg","mid":233132995,"name":"大概是一颗葱油饼"},"cover":{"src":"http://i0.hdslb.com/bfs/live/room_cover/c06583ea7b61dd157e9d70f6d13a73cfec4e3337.jpg","height":180,"width":320},"title":"画累了，摸鱼","room_id":5923793,"check_version":0,"online":6,"area":"绘画专区","area_id":9,"playurl":"http://ws.live-play.acgvideo.com/live-ws/718773/live_233132995_8419718.flv?wsSecret=a7d1e7cf5d3e58350386e16edd51732d&wsTime=1552361161&trid=63901c288ca44742ab2f55db64af616a&sig=no","accept_quality":"4","broadcast_type":0,"is_tv":0},{"owner":{"face":"http://i1.hdslb.com/bfs/face/b507c4a5418eb91f6ada4795292594439f5c2864.jpg","mid":96760464,"name":"君_尹醉"},"cover":{"src":"http://i0.hdslb.com/bfs/live/user_cover/c06a52dd1f2c1fd019c7611fda828a5b21c16ada.jpg","height":180,"width":320},"title":"勤快如我，肝已爆","room_id":3965495,"check_version":0,"online":55,"area":"绘画专区","area_id":9,"playurl":"http://ws.live-play.acgvideo.com/live-ws/553886/live_96760464_7014326.flv?wsSecret=016aa9c8bc360d094c333cb0f4e0d4e6&wsTime=1552361161&trid=63901c288ca44742ab2f55db64af616a&sig=no","accept_quality":"4","broadcast_type":0,"is_tv":0}]
         */

        private RecommendDataBean.PartitionBean partition;
        private List<RecommendDataBean.LivesBean> lives;

        public RecommendDataBean.PartitionBean getPartition() {
            return partition;
        }

        public void setPartition(RecommendDataBean.PartitionBean partition) {
            this.partition = partition;
        }

        public List<RecommendDataBean.LivesBean> getLives() {
            return lives;
        }

        public void setLives(List<RecommendDataBean.LivesBean> lives) {
            this.lives = lives;
        }

        public static class PartitionBeanX {
            /**
             * id : 9
             * name : 绘画专区
             * area : draw
             * sub_icon : {"src":"http://static.hdslb.com/live-static/images/mobile/android/small/xxhdpi/9.png?20171116172700","height":"63","width":"63"}
             * count : 92
             */

            private int id;
            private String name;
            private String area;
            private SubIconBeanX sub_icon;
            private int count;

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

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public SubIconBeanX getSub_icon() {
                return sub_icon;
            }

            public void setSub_icon(SubIconBeanX sub_icon) {
                this.sub_icon = sub_icon;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public static class SubIconBeanX {
                /**
                 * src : http://static.hdslb.com/live-static/images/mobile/android/small/xxhdpi/9.png?20171116172700
                 * height : 63
                 * width : 63
                 */

                private String src;
                private String height;
                private String width;

                public String getSrc() {
                    return src;
                }

                public void setSrc(String src) {
                    this.src = src;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }
            }
        }

        public static class LivesBeanX {
            /**
             * owner : {"face":"http://i1.hdslb.com/bfs/face/6c80fa10147b4481688bcaee61ff052fde0b4bdc.jpg","mid":9871569,"name":"小逝lullaby"}
             * cover : {"src":"http://i0.hdslb.com/bfs/live/e02e3b4f71fa8c57793d70fbccb6edbd5883d14a.jpg","height":180,"width":320}
             * title : 封⭐面是我画的，不用问了…
             * room_id : 73088
             * check_version : 0
             * online : 487
             * area : 绘画专区
             * area_id : 9
             * playurl : http://ws.live-play.acgvideo.com/live-ws/150694/live_9871569_3091457.flv?wsSecret=4b788f2328d20d0c0b9437d71e2d4382&wsTime=1552361161&trid=63901c288ca44742ab2f55db64af616a&sig=no
             * accept_quality : 4
             * broadcast_type : 0
             * is_tv : 0
             */

            private OwnerBeanXX owner;
            private CoverBeanXX cover;
            private String title;
            private int room_id;
            private int check_version;
            private int online;
            private String area;
            private int area_id;
            private String playurl;
            private String accept_quality;
            private int broadcast_type;
            private int is_tv;

            public OwnerBeanXX getOwner() {
                return owner;
            }

            public void setOwner(OwnerBeanXX owner) {
                this.owner = owner;
            }

            public CoverBeanXX getCover() {
                return cover;
            }

            public void setCover(CoverBeanXX cover) {
                this.cover = cover;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getRoom_id() {
                return room_id;
            }

            public void setRoom_id(int room_id) {
                this.room_id = room_id;
            }

            public int getCheck_version() {
                return check_version;
            }

            public void setCheck_version(int check_version) {
                this.check_version = check_version;
            }

            public int getOnline() {
                return online;
            }

            public void setOnline(int online) {
                this.online = online;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public int getArea_id() {
                return area_id;
            }

            public void setArea_id(int area_id) {
                this.area_id = area_id;
            }

            public String getPlayurl() {
                return playurl;
            }

            public void setPlayurl(String playurl) {
                this.playurl = playurl;
            }

            public String getAccept_quality() {
                return accept_quality;
            }

            public void setAccept_quality(String accept_quality) {
                this.accept_quality = accept_quality;
            }

            public int getBroadcast_type() {
                return broadcast_type;
            }

            public void setBroadcast_type(int broadcast_type) {
                this.broadcast_type = broadcast_type;
            }

            public int getIs_tv() {
                return is_tv;
            }

            public void setIs_tv(int is_tv) {
                this.is_tv = is_tv;
            }

            public static class OwnerBeanXX {
                /**
                 * face : http://i1.hdslb.com/bfs/face/6c80fa10147b4481688bcaee61ff052fde0b4bdc.jpg
                 * mid : 9871569
                 * name : 小逝lullaby
                 */

                private String face;
                private int mid;
                private String name;

                public String getFace() {
                    return face;
                }

                public void setFace(String face) {
                    this.face = face;
                }

                public int getMid() {
                    return mid;
                }

                public void setMid(int mid) {
                    this.mid = mid;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class CoverBeanXX {
                /**
                 * src : http://i0.hdslb.com/bfs/live/e02e3b4f71fa8c57793d70fbccb6edbd5883d14a.jpg
                 * height : 180
                 * width : 320
                 */

                private String src;
                private int height;
                private int width;

                public String getSrc() {
                    return src;
                }

                public void setSrc(String src) {
                    this.src = src;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }
            }
        }
    }
}
