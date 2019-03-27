package com.bilibili.lingxiao.home.recommend;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecommendData {

    private List<DataBean> data;
    public List<DataBean> getData() {
        return data;
    }
    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private String param;
        @SerializedName("goto")
        private String gotoX;
        private int idx;
        private String hash;
        private int autoplay_card;
        private String title;
        private String cover;
        private String uri;
        private String desc;
        private int play;
        private int danmaku;
        private int reply;
        private int favorite;
        private int coin;
        private int share;
        private int like;
        private int duration;
        private int cid;
        private int tid;
        private String tname;
        private int ctime;
        private int autoplay;
        private int mid;
        private String name;
        private String face;
        private OfficialBean official;
        private String request_id;
        private int creative_id;
        private int src_id;
        private boolean is_ad;
        private boolean is_ad_loc;
        private String ad_cb;
        private String client_ip;
        private int cm_mark;
        private ExtraBean extra;
        private int card_index;
        private List<BannerItemBean> banner_item;
        private List<DislikeReasonsBean> dislike_reasons;

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public String getGotoX() {
            return gotoX;
        }

        public void setGotoX(String gotoX) {
            this.gotoX = gotoX;
        }

        public int getIdx() {
            return idx;
        }

        public void setIdx(int idx) {
            this.idx = idx;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public int getAutoplay_card() {
            return autoplay_card;
        }

        public void setAutoplay_card(int autoplay_card) {
            this.autoplay_card = autoplay_card;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getPlay() {
            return play;
        }

        public void setPlay(int play) {
            this.play = play;
        }

        public int getDanmaku() {
            return danmaku;
        }

        public void setDanmaku(int danmaku) {
            this.danmaku = danmaku;
        }

        public int getReply() {
            return reply;
        }

        public void setReply(int reply) {
            this.reply = reply;
        }

        public int getFavorite() {
            return favorite;
        }

        public void setFavorite(int favorite) {
            this.favorite = favorite;
        }

        public int getCoin() {
            return coin;
        }

        public void setCoin(int coin) {
            this.coin = coin;
        }

        public int getShare() {
            return share;
        }

        public void setShare(int share) {
            this.share = share;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public int getCtime() {
            return ctime;
        }

        public void setCtime(int ctime) {
            this.ctime = ctime;
        }

        public int getAutoplay() {
            return autoplay;
        }

        public void setAutoplay(int autoplay) {
            this.autoplay = autoplay;
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

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public OfficialBean getOfficial() {
            return official;
        }

        public void setOfficial(OfficialBean official) {
            this.official = official;
        }

        public String getRequest_id() {
            return request_id;
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }

        public int getCreative_id() {
            return creative_id;
        }

        public void setCreative_id(int creative_id) {
            this.creative_id = creative_id;
        }

        public int getSrc_id() {
            return src_id;
        }

        public void setSrc_id(int src_id) {
            this.src_id = src_id;
        }

        public boolean isIs_ad() {
            return is_ad;
        }

        public void setIs_ad(boolean is_ad) {
            this.is_ad = is_ad;
        }

        public boolean isIs_ad_loc() {
            return is_ad_loc;
        }

        public void setIs_ad_loc(boolean is_ad_loc) {
            this.is_ad_loc = is_ad_loc;
        }

        public String getAd_cb() {
            return ad_cb;
        }

        public void setAd_cb(String ad_cb) {
            this.ad_cb = ad_cb;
        }

        public String getClient_ip() {
            return client_ip;
        }

        public void setClient_ip(String client_ip) {
            this.client_ip = client_ip;
        }

        public int getCm_mark() {
            return cm_mark;
        }

        public void setCm_mark(int cm_mark) {
            this.cm_mark = cm_mark;
        }

        public ExtraBean getExtra() {
            return extra;
        }

        public void setExtra(ExtraBean extra) {
            this.extra = extra;
        }

        public int getCard_index() {
            return card_index;
        }

        public void setCard_index(int card_index) {
            this.card_index = card_index;
        }

        public List<BannerItemBean> getBanner_item() {
            return banner_item;
        }

        public void setBanner_item(List<BannerItemBean> banner_item) {
            this.banner_item = banner_item;
        }

        public List<DislikeReasonsBean> getDislike_reasons() {
            return dislike_reasons;
        }

        public void setDislike_reasons(List<DislikeReasonsBean> dislike_reasons) {
            this.dislike_reasons = dislike_reasons;
        }

        public static class OfficialBean {
            /**
             * role : 2
             * title : 日本虚拟UP主
             */

            private int role;
            private String title;

            public int getRole() {
                return role;
            }

            public void setRole(int role) {
                this.role = role;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class ExtraBean {

            private boolean use_ad_web_v2;
            private CardBean card;
            private int report_time;
            private int sales_type;
            private boolean special_industry;
            private String special_industry_tips;
            private int preload_landingpage;
            private List<?> show_urls;
            private List<?> click_urls;
            private List<?> download_whitelist;
            private List<?> open_whitelist;

            public boolean isUse_ad_web_v2() {
                return use_ad_web_v2;
            }

            public void setUse_ad_web_v2(boolean use_ad_web_v2) {
                this.use_ad_web_v2 = use_ad_web_v2;
            }

            public CardBean getCard() {
                return card;
            }

            public void setCard(CardBean card) {
                this.card = card;
            }

            public int getReport_time() {
                return report_time;
            }

            public void setReport_time(int report_time) {
                this.report_time = report_time;
            }

            public int getSales_type() {
                return sales_type;
            }

            public void setSales_type(int sales_type) {
                this.sales_type = sales_type;
            }

            public boolean isSpecial_industry() {
                return special_industry;
            }

            public void setSpecial_industry(boolean special_industry) {
                this.special_industry = special_industry;
            }

            public String getSpecial_industry_tips() {
                return special_industry_tips;
            }

            public void setSpecial_industry_tips(String special_industry_tips) {
                this.special_industry_tips = special_industry_tips;
            }

            public int getPreload_landingpage() {
                return preload_landingpage;
            }

            public void setPreload_landingpage(int preload_landingpage) {
                this.preload_landingpage = preload_landingpage;
            }

            public List<?> getShow_urls() {
                return show_urls;
            }

            public void setShow_urls(List<?> show_urls) {
                this.show_urls = show_urls;
            }

            public List<?> getClick_urls() {
                return click_urls;
            }

            public void setClick_urls(List<?> click_urls) {
                this.click_urls = click_urls;
            }

            public List<?> getDownload_whitelist() {
                return download_whitelist;
            }

            public void setDownload_whitelist(List<?> download_whitelist) {
                this.download_whitelist = download_whitelist;
            }

            public List<?> getOpen_whitelist() {
                return open_whitelist;
            }

            public void setOpen_whitelist(List<?> open_whitelist) {
                this.open_whitelist = open_whitelist;
            }

            public static class CardBean {
                /**
                 * card_type : 3
                 * title : 刚来成都，想找个本地男友，单身进
                 * covers : [{"url":"https://i0.hdslb.com/bfs/sycp/creative_img/201903/13db5f84bab39962750c7dd409dafb35.jpg_640x400.jpg"}]
                 * jump_url : https://cm.bilibili.com/cm/api/fees/wise/redirect?ad_cb=CM3sBBDWxA4Yn%2FXnBCBHKAIwwQc4rQ9CHzE1NTM2NTQxNzYwMTBxMTcyYTIzYTU4YTE4M3E2MjBIirKF6JstUgbmiJDpg71aBuWbm%2BW3nWIG5Lit5Zu9aAFwAHiAgICA4ASAAQOIAQCSAQwyMTguODguMjEuNTCaAc0DYWxsOmNwY19jcm93ZF90YXJnZXQsZWNwbTpkZWZhdWx0LGNwY1RhZ0ZpbHRlcjp1bmRlZmluZWQsZW5oYW5jZUN0clFGYWN0b3I6ZGVmYXVsdCxhZE1lY2hhbmlzbU1vbml0b3I6b3BlbixwbGF5cGFnZWN0cjpkaXNhYmxlLHVwX3JlY19mbG93X2NvbnRyb2w6dW5kZWZpbmVkLGJydXNoX2R1cGxpY2F0ZTpkZWZhdWx0LHBjdHJfY3BtOmNwbSxwY3RyX3YyOmRmdCxkeW5hbWljX2Zsb3dfY29udHJvbDpzcGxpdCB0aGUgZmxvdyBieSBtaWQscGN2cjpib3RoX2FfMV9iXzAuMDVfY18xX2ZfMV8xLjUsZnJlcUxpbWl0OmRlZmF1bHQsc21hbGxDb25zdW1lVW5pdDpkZWZhdWx0LG91dGVyQmVhdElubmVyOmRlZmF1bHQsb3V0ZXJRdWl0OmRlZmF1bHQsZmRzX3J0dDpkZWZhdWx0LGVmZmVjdF90aHJlc2hvbGQ6bm9uZVRocmVzaG9sZCxjcGE6Y3BhLGluZGlzX1VWOnVuZGVmaW5lZCxkbXBfY3Jvd2RfZXhwOmRlZmF1bHSgAUeoAUuyASAxOqRXGMaoXMF0skxShmh31HFwXLEFahLUoLsMftwKJLoBJGh0dHBzOi8vbWFycnkxMjAubWlrZWNybS5jb20vNzFsVVFjSMIBczI3MDNfNzQzXzQ1MV8zOTNfMjQ1XzIyMV8xNzRfMTI3XzEyN18xMTJfMTEyXzExMl8xMDlfODZfNTJfNTJfNTJfNTJfNTJfNTJfNTJfNTJfNTJfNTJfNTJfNTJfNTJfNTJfNTJfNTJfNTJfNTJfNTFfNTHKAQDSAQDYASbgAbDUMugBsNQy8AEA%2BAE7gAICiAIAkgJZMjM0NjYyXzE1NTM0MTI0MzAsMjM1NTUyXzE1NTM0MTI0MzAsMjMxMDY0XzE1NTM0MTI0MzEsMjM3NjYwXzE1NTM1ODc0MjksMjM4MTA1XzE1NTM1ODg1MzGYAozMPKAC1T2oAuzMBbACtgW4AgDAAgDIAgDQAgDYAgDiAhIsLOaIkOmDvS3mrabkvq%2FljLrqAgA%3D
                 * desc : 恋爱交友
                 * callup_url :
                 * long_desc :
                 * ad_tag :
                 * extra_desc :
                 * ad_tag_style : {"type":2,"text":"广告","text_color":"#999999FF","bg_border_color":"#999999FF"}
                 */

                private int card_type;
                private String title;
                private String jump_url;
                private String desc;
                private String callup_url;
                private String long_desc;
                private String ad_tag;
                private String extra_desc;
                private AdTagStyleBean ad_tag_style;
                private List<CoversBean> covers;

                public int getCard_type() {
                    return card_type;
                }

                public void setCard_type(int card_type) {
                    this.card_type = card_type;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getJump_url() {
                    return jump_url;
                }

                public void setJump_url(String jump_url) {
                    this.jump_url = jump_url;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getCallup_url() {
                    return callup_url;
                }

                public void setCallup_url(String callup_url) {
                    this.callup_url = callup_url;
                }

                public String getLong_desc() {
                    return long_desc;
                }

                public void setLong_desc(String long_desc) {
                    this.long_desc = long_desc;
                }

                public String getAd_tag() {
                    return ad_tag;
                }

                public void setAd_tag(String ad_tag) {
                    this.ad_tag = ad_tag;
                }

                public String getExtra_desc() {
                    return extra_desc;
                }

                public void setExtra_desc(String extra_desc) {
                    this.extra_desc = extra_desc;
                }

                public AdTagStyleBean getAd_tag_style() {
                    return ad_tag_style;
                }

                public void setAd_tag_style(AdTagStyleBean ad_tag_style) {
                    this.ad_tag_style = ad_tag_style;
                }

                public List<CoversBean> getCovers() {
                    return covers;
                }

                public void setCovers(List<CoversBean> covers) {
                    this.covers = covers;
                }

                public static class AdTagStyleBean {
                    /**
                     * type : 2
                     * text : 广告
                     * text_color : #999999FF
                     * bg_border_color : #999999FF
                     */

                    private int type;
                    private String text;
                    private String text_color;
                    private String bg_border_color;

                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }

                    public String getText() {
                        return text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }

                    public String getText_color() {
                        return text_color;
                    }

                    public void setText_color(String text_color) {
                        this.text_color = text_color;
                    }

                    public String getBg_border_color() {
                        return bg_border_color;
                    }

                    public void setBg_border_color(String bg_border_color) {
                        this.bg_border_color = bg_border_color;
                    }
                }

                public static class CoversBean {
                    /**
                     * url : https://i0.hdslb.com/bfs/sycp/creative_img/201903/13db5f84bab39962750c7dd409dafb35.jpg_640x400.jpg
                     */

                    private String url;

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }
                }
            }
        }

        public static class BannerItemBean {
            /**
             * id : 251734
             * title : 快来测试你是哪种勇者！
             * image : http://i0.hdslb.com/bfs/archive/22131ac76e38a9ecf3f7b7a9ec1a17125fa6f1c6.jpg
             * hash : f3c18a322d5259a55cc1326822ebc27b
             * uri : https://www.bilibili.com/read/cv2300680
             * request_id : 1553654176016
             * server_type : 0
             * resource_id : 631
             * index : 1
             * cm_mark : 0
             * creative_id : 21512
             * src_id : 703
             * is_ad : true
             * is_ad_loc : true
             * ad_cb : CNeGAhAAGIioASAAKAAwAzi/BUIeMTU1MzY1NDE3NjAxMnExNzJhMjNhNjFhOTdxNjk4SIyyheibLVIG5oiQ6YO9Wgblm5vlt51iBuS4reWbvWgAcAB4gICAgBCAAQCIAYApkgEMMjE4Ljg4LjIxLjUwmgG4A2FsbDpjcGNfY3Jvd2RfdGFyZ2V0LGVjcG06ZGVmYXVsdCxjcGNUYWdGaWx0ZXI6dW5kZWZpbmVkLGVuaGFuY2VDdHJRRmFjdG9yOnNxdWFyZSxhZE1lY2hhbmlzbU1vbml0b3I6b3RoZXIscGxheXBhZ2VjdHI6ZGlzYWJsZSx1cF9yZWNfZmxvd19jb250cm9sOnVuZGVmaW5lZCxicnVzaF9kdXBsaWNhdGU6ZGVmYXVsdCxwY3RyX2NwbTpjcG0scGN0cl92MjprYWZrYSxkeW5hbWljX2Zsb3dfY29udHJvbDpzcGxpdCB0aGUgZmxvdyBieSBtaWQscGN2cjpkbGQsZnJlcUxpbWl0OmRlZmF1bHQsc21hbGxDb25zdW1lVW5pdDpkZWZhdWx0LG91dGVyQmVhdElubmVyOmVuYWJsZSxvdXRlclF1aXQ6ZGVmYXVsdCxmZHNfcnR0OmRlZmF1bHQsZWZmZWN0X3RocmVzaG9sZDpjcGNDdHJUaHJlc2hvbGQsY3BhOmNwYSxpbmRpc19VVjp1bmRlZmluZWQsZG1wX2Nyb3dkX2V4cDpkZWZhdWx0oAEAqAEAsgEgNax8+bT+hX5YzzLJUldiAD/pLhucQ5e+uvLbEYO1ddK6AVtodHRwczovL2dhbWUuYmlsaWJpbGkuY29tL3VuaGVhcmQvaDU/bXNvdXJjZT0xJnNvdXJjZT1hZmlkXzEwMTRjNGMwNGM5NzExZTk4ZGE3ZmE2YjZlMDQ0MmI2wgEAygEA0gEA2AEB4AEA6AEA8AEA+AEAgAIAiAIAuAIAwAKgrE/IAgDQAgDYAgDiAhIsLOaIkOmDvS3mrabkvq/ljLrqAgA=
             * click_url : https://ad-bili-data.biligame.com/api/mobile/clickBili?ad_plan_id=19333&mid=__MID__&os=0&idfa=__IDFA__&buvid=__BUVID__&android_id=__ANDROIDID__&imei=__IMEI__&mac=__MAC__&duid=__DUID__&ip=218.88.21.50&request_id=1553654176012q172a23a61a97q698&ts=__TS__&ua=__UA__
             * client_ip : 218.88.21.50
             * extra : {"use_ad_web_v2":false,"show_urls":[],"click_urls":["https://ad-bili-data.biligame.com/api/mobile/clickBili?ad_plan_id=19333&mid=__MID__&os=0&idfa=__IDFA__&buvid=__BUVID__&android_id=__ANDROIDID__&imei=__IMEI__&mac=__MAC__&duid=__DUID__&ip=218.88.21.50&request_id=1553654176012q172a23a61a97q698&ts=__TS__&ua=__UA__"],"open_whitelist":["dianping","kaola","vipshop","ctrip","bilibili","mqq","openapp.jdmobile","taobao","tbopen","tmall","weixin","alipays","sinaweibo","pinduoduo","SNKRS","booking"],"card":{"card_type":0,"title":"","covers":[{"url":"https://i0.hdslb.com/bfs/sycp/creative_img/201903/10559e40a81dae32cd1fb936d292362e.jpg"}],"jump_url":"https://game.bilibili.com/unheard/h5?msource=1&source=afid_1014c4c04c9711e98da7fa6b6e0442b6","desc":"","button":{"type":1,"text":"","jump_url":"https://game.bilibili.com/unheard/h5?msource=1&source=afid_1014c4c04c9711e98da7fa6b6e0442b6","report_urls":[],"dlsuc_callup_url":""},"callup_url":"","long_desc":"","ad_tag":"","extra_desc":"","ad_tag_style":{"type":2,"text":"广告","text_color":"#999999FF","bg_border_color":"#999999FF"}},"report_time":2000,"sales_type":31,"special_industry":false,"special_industry_tips":"","preload_landingpage":0}
             */

            private int id;
            private String title;
            private String image;
            private String hash;
            private String uri;
            private String request_id;
            private int server_type;
            private int resource_id;
            private int index;
            private int cm_mark;
            private int creative_id;
            private int src_id;
            private boolean is_ad;
            private boolean is_ad_loc;
            private String ad_cb;
            private String click_url;
            private String client_ip;
            private ExtraBeanX extra;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getHash() {
                return hash;
            }

            public void setHash(String hash) {
                this.hash = hash;
            }

            public String getUri() {
                return uri;
            }

            public void setUri(String uri) {
                this.uri = uri;
            }

            public String getRequest_id() {
                return request_id;
            }

            public void setRequest_id(String request_id) {
                this.request_id = request_id;
            }

            public int getServer_type() {
                return server_type;
            }

            public void setServer_type(int server_type) {
                this.server_type = server_type;
            }

            public int getResource_id() {
                return resource_id;
            }

            public void setResource_id(int resource_id) {
                this.resource_id = resource_id;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public int getCm_mark() {
                return cm_mark;
            }

            public void setCm_mark(int cm_mark) {
                this.cm_mark = cm_mark;
            }

            public int getCreative_id() {
                return creative_id;
            }

            public void setCreative_id(int creative_id) {
                this.creative_id = creative_id;
            }

            public int getSrc_id() {
                return src_id;
            }

            public void setSrc_id(int src_id) {
                this.src_id = src_id;
            }

            public boolean isIs_ad() {
                return is_ad;
            }

            public void setIs_ad(boolean is_ad) {
                this.is_ad = is_ad;
            }

            public boolean isIs_ad_loc() {
                return is_ad_loc;
            }

            public void setIs_ad_loc(boolean is_ad_loc) {
                this.is_ad_loc = is_ad_loc;
            }

            public String getAd_cb() {
                return ad_cb;
            }

            public void setAd_cb(String ad_cb) {
                this.ad_cb = ad_cb;
            }

            public String getClick_url() {
                return click_url;
            }

            public void setClick_url(String click_url) {
                this.click_url = click_url;
            }

            public String getClient_ip() {
                return client_ip;
            }

            public void setClient_ip(String client_ip) {
                this.client_ip = client_ip;
            }

            public ExtraBeanX getExtra() {
                return extra;
            }

            public void setExtra(ExtraBeanX extra) {
                this.extra = extra;
            }

            public static class ExtraBeanX {
                /**
                 * use_ad_web_v2 : false
                 * show_urls : []
                 * click_urls : ["https://ad-bili-data.biligame.com/api/mobile/clickBili?ad_plan_id=19333&mid=__MID__&os=0&idfa=__IDFA__&buvid=__BUVID__&android_id=__ANDROIDID__&imei=__IMEI__&mac=__MAC__&duid=__DUID__&ip=218.88.21.50&request_id=1553654176012q172a23a61a97q698&ts=__TS__&ua=__UA__"]
                 * open_whitelist : ["dianping","kaola","vipshop","ctrip","bilibili","mqq","openapp.jdmobile","taobao","tbopen","tmall","weixin","alipays","sinaweibo","pinduoduo","SNKRS","booking"]
                 * card : {"card_type":0,"title":"","covers":[{"url":"https://i0.hdslb.com/bfs/sycp/creative_img/201903/10559e40a81dae32cd1fb936d292362e.jpg"}],"jump_url":"https://game.bilibili.com/unheard/h5?msource=1&source=afid_1014c4c04c9711e98da7fa6b6e0442b6","desc":"","button":{"type":1,"text":"","jump_url":"https://game.bilibili.com/unheard/h5?msource=1&source=afid_1014c4c04c9711e98da7fa6b6e0442b6","report_urls":[],"dlsuc_callup_url":""},"callup_url":"","long_desc":"","ad_tag":"","extra_desc":"","ad_tag_style":{"type":2,"text":"广告","text_color":"#999999FF","bg_border_color":"#999999FF"}}
                 * report_time : 2000
                 * sales_type : 31
                 * special_industry : false
                 * special_industry_tips :
                 * preload_landingpage : 0
                 */

                private boolean use_ad_web_v2;
                private CardBeanX card;
                private int report_time;
                private int sales_type;
                private boolean special_industry;
                private String special_industry_tips;
                private int preload_landingpage;
                private List<?> show_urls;
                private List<String> click_urls;
                private List<String> open_whitelist;

                public boolean isUse_ad_web_v2() {
                    return use_ad_web_v2;
                }

                public void setUse_ad_web_v2(boolean use_ad_web_v2) {
                    this.use_ad_web_v2 = use_ad_web_v2;
                }

                public CardBeanX getCard() {
                    return card;
                }

                public void setCard(CardBeanX card) {
                    this.card = card;
                }

                public int getReport_time() {
                    return report_time;
                }

                public void setReport_time(int report_time) {
                    this.report_time = report_time;
                }

                public int getSales_type() {
                    return sales_type;
                }

                public void setSales_type(int sales_type) {
                    this.sales_type = sales_type;
                }

                public boolean isSpecial_industry() {
                    return special_industry;
                }

                public void setSpecial_industry(boolean special_industry) {
                    this.special_industry = special_industry;
                }

                public String getSpecial_industry_tips() {
                    return special_industry_tips;
                }

                public void setSpecial_industry_tips(String special_industry_tips) {
                    this.special_industry_tips = special_industry_tips;
                }

                public int getPreload_landingpage() {
                    return preload_landingpage;
                }

                public void setPreload_landingpage(int preload_landingpage) {
                    this.preload_landingpage = preload_landingpage;
                }

                public List<?> getShow_urls() {
                    return show_urls;
                }

                public void setShow_urls(List<?> show_urls) {
                    this.show_urls = show_urls;
                }

                public List<String> getClick_urls() {
                    return click_urls;
                }

                public void setClick_urls(List<String> click_urls) {
                    this.click_urls = click_urls;
                }

                public List<String> getOpen_whitelist() {
                    return open_whitelist;
                }

                public void setOpen_whitelist(List<String> open_whitelist) {
                    this.open_whitelist = open_whitelist;
                }

                public static class CardBeanX {
                    /**
                     * card_type : 0
                     * title :
                     * covers : [{"url":"https://i0.hdslb.com/bfs/sycp/creative_img/201903/10559e40a81dae32cd1fb936d292362e.jpg"}]
                     * jump_url : https://game.bilibili.com/unheard/h5?msource=1&source=afid_1014c4c04c9711e98da7fa6b6e0442b6
                     * desc :
                     * button : {"type":1,"text":"","jump_url":"https://game.bilibili.com/unheard/h5?msource=1&source=afid_1014c4c04c9711e98da7fa6b6e0442b6","report_urls":[],"dlsuc_callup_url":""}
                     * callup_url :
                     * long_desc :
                     * ad_tag :
                     * extra_desc :
                     * ad_tag_style : {"type":2,"text":"广告","text_color":"#999999FF","bg_border_color":"#999999FF"}
                     */

                    private int card_type;
                    private String title;
                    private String jump_url;
                    private String desc;
                    private ButtonBean button;
                    private String callup_url;
                    private String long_desc;
                    private String ad_tag;
                    private String extra_desc;
                    private AdTagStyleBeanX ad_tag_style;
                    private List<CoversBeanX> covers;

                    public int getCard_type() {
                        return card_type;
                    }

                    public void setCard_type(int card_type) {
                        this.card_type = card_type;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public String getJump_url() {
                        return jump_url;
                    }

                    public void setJump_url(String jump_url) {
                        this.jump_url = jump_url;
                    }

                    public String getDesc() {
                        return desc;
                    }

                    public void setDesc(String desc) {
                        this.desc = desc;
                    }

                    public ButtonBean getButton() {
                        return button;
                    }

                    public void setButton(ButtonBean button) {
                        this.button = button;
                    }

                    public String getCallup_url() {
                        return callup_url;
                    }

                    public void setCallup_url(String callup_url) {
                        this.callup_url = callup_url;
                    }

                    public String getLong_desc() {
                        return long_desc;
                    }

                    public void setLong_desc(String long_desc) {
                        this.long_desc = long_desc;
                    }

                    public String getAd_tag() {
                        return ad_tag;
                    }

                    public void setAd_tag(String ad_tag) {
                        this.ad_tag = ad_tag;
                    }

                    public String getExtra_desc() {
                        return extra_desc;
                    }

                    public void setExtra_desc(String extra_desc) {
                        this.extra_desc = extra_desc;
                    }

                    public AdTagStyleBeanX getAd_tag_style() {
                        return ad_tag_style;
                    }

                    public void setAd_tag_style(AdTagStyleBeanX ad_tag_style) {
                        this.ad_tag_style = ad_tag_style;
                    }

                    public List<CoversBeanX> getCovers() {
                        return covers;
                    }

                    public void setCovers(List<CoversBeanX> covers) {
                        this.covers = covers;
                    }

                    public static class ButtonBean {
                        /**
                         * type : 1
                         * text :
                         * jump_url : https://game.bilibili.com/unheard/h5?msource=1&source=afid_1014c4c04c9711e98da7fa6b6e0442b6
                         * report_urls : []
                         * dlsuc_callup_url :
                         */

                        private int type;
                        private String text;
                        private String jump_url;
                        private String dlsuc_callup_url;
                        private List<?> report_urls;

                        public int getType() {
                            return type;
                        }

                        public void setType(int type) {
                            this.type = type;
                        }

                        public String getText() {
                            return text;
                        }

                        public void setText(String text) {
                            this.text = text;
                        }

                        public String getJump_url() {
                            return jump_url;
                        }

                        public void setJump_url(String jump_url) {
                            this.jump_url = jump_url;
                        }

                        public String getDlsuc_callup_url() {
                            return dlsuc_callup_url;
                        }

                        public void setDlsuc_callup_url(String dlsuc_callup_url) {
                            this.dlsuc_callup_url = dlsuc_callup_url;
                        }

                        public List<?> getReport_urls() {
                            return report_urls;
                        }

                        public void setReport_urls(List<?> report_urls) {
                            this.report_urls = report_urls;
                        }
                    }

                    public static class AdTagStyleBeanX {
                        /**
                         * type : 2
                         * text : 广告
                         * text_color : #999999FF
                         * bg_border_color : #999999FF
                         */

                        private int type;
                        private String text;
                        private String text_color;
                        private String bg_border_color;

                        public int getType() {
                            return type;
                        }

                        public void setType(int type) {
                            this.type = type;
                        }

                        public String getText() {
                            return text;
                        }

                        public void setText(String text) {
                            this.text = text;
                        }

                        public String getText_color() {
                            return text_color;
                        }

                        public void setText_color(String text_color) {
                            this.text_color = text_color;
                        }

                        public String getBg_border_color() {
                            return bg_border_color;
                        }

                        public void setBg_border_color(String bg_border_color) {
                            this.bg_border_color = bg_border_color;
                        }
                    }

                    public static class CoversBeanX {
                        /**
                         * url : https://i0.hdslb.com/bfs/sycp/creative_img/201903/10559e40a81dae32cd1fb936d292362e.jpg
                         */

                        private String url;

                        public String getUrl() {
                            return url;
                        }

                        public void setUrl(String url) {
                            this.url = url;
                        }
                    }
                }
            }
        }

        public static class DislikeReasonsBean {
            /**
             * reason_id : 4
             * reason_name : UP主:赤井心Official
             */

            private int reason_id;
            private String reason_name;

            public int getReason_id() {
                return reason_id;
            }

            public void setReason_id(int reason_id) {
                this.reason_id = reason_id;
            }

            public String getReason_name() {
                return reason_name;
            }

            public void setReason_name(String reason_name) {
                this.reason_name = reason_name;
            }
        }
    }

}
