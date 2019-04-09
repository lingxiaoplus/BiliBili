package com.bilibili.lingxiao.home.live

import com.chad.library.adapter.base.entity.MultiItemEntity

class LiveData {
    var banner: List<BannerBean>? = null
    var entranceIcons: List<EntranceIconsBean>? = null
    var partitions: List<PartitionsBean>? = null
    var recommend_data: RecommendDataBean? = null

    class RecommendDataBean {

        var partition: PartitionBean? = null
        var lives: List<LivesBean>? = null
        var banner_data: List<BannerDataBean>? = null

        class PartitionBean {
            /**
             * id : 0
             * name : 推荐主播
             * area : hot
             * sub_icon : {"src":"http://static.hdslb.com/live-static/images/mobile/android/small/xxhdpi/-1.png?20171116172700","height":"63","width":"63"}
             * count : 2718
             */

            var id: Int = 0
            var name: String? = null
            var area: String? = null
            var sub_icon: SubIconBean? = null
            var count: Int = 0

            class SubIconBean {
                /**
                 * src : http://static.hdslb.com/live-static/images/mobile/android/small/xxhdpi/-1.png?20171116172700
                 * height : 63
                 * width : 63
                 */

                var src: String? = null
                var height: String? = null
                var width: String? = null
            }
        }

        class LivesBean {
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

            var owner: OwnerBean? = null
            var cover: CoverBean? = null
            var room_id: Int = 0
            var check_version: Int = 0
            var online: Int = 0
            var area: String? = null
            var area_id: Int = 0
            var title: String? = null
            var playurl: String? = null
            var accept_quality: String? = null
            var broadcast_type: Int = 0
            var is_tv: Int = 0
            var area_v2_id: Int = 0
            var area_v2_name: String? = null
            var area_v2_parent_id: Int = 0
            var area_v2_parent_name: String? = null

            class OwnerBean {
                /**
                 * face : http://i1.hdslb.com/bfs/face/8c49a758216f9bd14b0046afe48a3514f44126f0.jpg
                 * mid : 110631
                 * name : 宫本狗雨
                 */

                var face: String? = null
                var mid: Int = 0
                var name: String? = null
            }

            class CoverBean {
                /**
                 * src : http://i0.hdslb.com/bfs/live/63602c757dd6aaf2f498cb3d44b47fced6589a1e.jpg
                 * height : 180
                 * width : 320
                 */

                var src: String? = null
                var height: Int = 0
                var width: Int = 0
            }
        }

        class BannerDataBean {
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

            var owner: OwnerBeanX? = null
            var cover: CoverBeanX? = null
            var room_id: Int = 0
            var check_version: Int = 0
            var online: Int = 0
            var area: String? = null
            var area_id: Int = 0
            var title: String? = null
            var playurl: String? = null
            var accept_quality: String? = null
            var broadcast_type: Int = 0
            var is_tv: Int = 0
            var area_v2_id: Int = 0
            var area_v2_name: String? = null
            var area_v2_parent_id: Int = 0
            var area_v2_parent_name: String? = null

            class OwnerBeanX {
                /**
                 * face : http://i2.hdslb.com/bfs/face/5d35da6e93fbfb1a77ad6d1f1004b08413913f9a.jpg
                 * mid : 11153765
                 * name : 3号直播间
                 */

                var face: String? = null
                var mid: Int = 0
                var name: String? = null
            }

            class CoverBeanX {
                /**
                 * src : http://i0.hdslb.com/bfs/live/98c268f907705241a3e3face25ae365e3120406b.png
                 * height : 180
                 * width : 320
                 */

                var src: String? = null
                var height: Int = 0
                var width: Int = 0
            }
        }
    }

    class BannerBean {
        /**
         * id : 913
         * pic : http://i0.hdslb.com/bfs/vc/7564c8b074a4ece54c23e10660c520e300cae782.jpg
         * link : https://www.bilibili.com/blackboard/activity-mdG8HVNeU.html
         * title : 超新星学员计划
         * position : 4
         * sort_num : 1
         * img : http://i0.hdslb.com/bfs/vc/7564c8b074a4ece54c23e10660c520e300cae782.jpg
         */

        var id: String? = null
        var pic: String? = null
        var link: String? = null
        var title: String? = null
        var position: String? = null
        var sort_num: String? = null
        var img: String? = null
    }

    class EntranceIconsBean {
        /**
         * id : 9
         * name : 绘画专区
         * entrance_icon : {"src":"http://static.hdslb.com/live-static/images/mobile/android/big/xxhdpi/9_big.png?20171116172700","height":"132","width":"132"}
         */

        var id: Int = 0
        var name: String? = null
        var entrance_icon: EntranceIconBean? = null

        class EntranceIconBean {
            /**
             * src : http://static.hdslb.com/live-static/images/mobile/android/big/xxhdpi/9_big.png?20171116172700
             * height : 132
             * width : 132
             */

            var src: String? = null
            var height: String? = null
            var width: String? = null
        }
    }

    class PartitionsBean {
        /**
         * partition : {"id":9,"name":"绘画专区","area":"draw","sub_icon":{"src":"http://static.hdslb.com/live-static/images/mobile/android/small/xxhdpi/9.png?20171116172700","height":"63","width":"63"},"count":92}
         * lives : [{"owner":{"face":"http://i1.hdslb.com/bfs/face/6c80fa10147b4481688bcaee61ff052fde0b4bdc.jpg","mid":9871569,"name":"小逝lullaby"},"cover":{"src":"http://i0.hdslb.com/bfs/live/e02e3b4f71fa8c57793d70fbccb6edbd5883d14a.jpg","height":180,"width":320},"title":"封⭐面是我画的，不用问了\u2026","room_id":73088,"check_version":0,"online":487,"area":"绘画专区","area_id":9,"playurl":"http://ws.live-play.acgvideo.com/live-ws/150694/live_9871569_3091457.flv?wsSecret=4b788f2328d20d0c0b9437d71e2d4382&wsTime=1552361161&trid=63901c288ca44742ab2f55db64af616a&sig=no","accept_quality":"4","broadcast_type":0,"is_tv":0},{"owner":{"face":"http://i1.hdslb.com/bfs/face/8fdff55db668991462065f2507713939ff115bd3.jpg","mid":1668013,"name":"戴腕表的怒龙道"},"cover":{"src":"http://i0.hdslb.com/bfs/live/user_cover/5b6910d2bdedc73c15691ba3a89a91d208194016.jpg","height":180,"width":320},"title":"瞎播测试","room_id":1152924,"check_version":0,"online":10,"area":"绘画专区","area_id":9,"playurl":"http://ws.live-play.acgvideo.com/live-ws/102457/live_1668013_3398863.flv?wsSecret=72e9fb1cde73b74a0f71e63e9c04133a&wsTime=1552361161&trid=63901c288ca44742ab2f55db64af616a&sig=no","accept_quality":"4","broadcast_type":0,"is_tv":0},{"owner":{"face":"http://i1.hdslb.com/bfs/face/ce8af5ba39f59dcd898ed59dbc26e9d0021a3ce1.jpg","mid":35271215,"name":"PumpkinJakk-金鱼"},"cover":{"src":"http://i0.hdslb.com/bfs/live/bff83d0b5a86e4654fc6ac08c866aa667d22f1db.jpg","height":180,"width":320},"title":"【金鱼】玄羽恋歌","room_id":472538,"check_version":0,"online":176,"area":"绘画专区","area_id":9,"playurl":"http://ws.live-play.acgvideo.com/live-ws/422797/live_35271215_8431452.flv?wsSecret=a81c8b69bdfa4aed1d540c33dc0ff3d3&wsTime=1552361161&trid=63901c288ca44742ab2f55db64af616a&sig=no","accept_quality":"4","broadcast_type":0,"is_tv":0},{"owner":{"face":"http://i0.hdslb.com/bfs/face/a1818c874392a6f203d832f70a87fddc6bc6b1f7.jpg","mid":25972035,"name":"肉松馅的蛋烘糕"},"cover":{"src":"http://i0.hdslb.com/bfs/live/room_cover/db559be4044b11e2384907bb8402569eac10e8c3.jpg","height":180,"width":320},"title":"听歌画画\u2014战斗天使","room_id":4290727,"check_version":0,"online":68,"area":"绘画专区","area_id":9,"playurl":"http://ws.live-play.acgvideo.com/live-ws/165867/live_25972035_3513791.flv?wsSecret=16b19213250a04a234c1e815ff9ab24f&wsTime=1552361161&trid=63901c288ca44742ab2f55db64af616a&sig=no","accept_quality":"4","broadcast_type":0,"is_tv":0},{"owner":{"face":"http://i2.hdslb.com/bfs/face/1c3f961754cadf27dfc961b3b6692bea7e0bf269.jpg","mid":1972394,"name":"蒙塔基的钢蛋儿_"},"cover":{"src":"http://i0.hdslb.com/bfs/live/d28d9e54cd8e5a2fd6fa525a4dad323e3cccf720.jpg","height":180,"width":320},"title":"暴躁小画家","room_id":946340,"check_version":0,"online":52,"area":"绘画专区","area_id":9,"playurl":"http://ws.live-play.acgvideo.com/live-ws/700399/live_1972394_8085638.flv?wsSecret=cd9dfc564ddc77374897d4283a4dd17f&wsTime=1552361161&trid=63901c288ca44742ab2f55db64af616a&sig=no","accept_quality":"4","broadcast_type":0,"is_tv":0},{"owner":{"face":"http://i1.hdslb.com/bfs/face/c04066b823ee54bc9e33e0816c6427ada986c293.jpg","mid":534128,"name":"harrymiao"},"cover":{"src":"http://i0.hdslb.com/bfs/live/ce0e1b42ece824bc94100c2ee547d98613ba11b3.jpg","height":180,"width":320},"title":"harrymiao的直播间","room_id":936402,"check_version":0,"online":1294,"area":"绘画专区","area_id":9,"playurl":"http://ws.live-play.acgvideo.com/live-ws/126418/live_534128_3438840.flv?wsSecret=b4b95993ef9af3f16679246e141e5a4b&wsTime=1552361161&trid=63901c288ca44742ab2f55db64af616a&sig=no","accept_quality":"4","broadcast_type":0,"is_tv":0},{"owner":{"face":"http://i2.hdslb.com/bfs/face/7449c484755309bc89d6bd68fb5d73ff7f6316e2.jpg","mid":20002577,"name":"大河庄一世繁华"},"cover":{"src":"http://i0.hdslb.com/bfs/live/room_cover/edec2128180f074a95446dc4986c5e14274130ed.jpg","height":180,"width":320},"title":"南小鸟白丝--花嫁折纸","room_id":465495,"check_version":0,"online":2238,"area":"绘画专区","area_id":9,"playurl":"http://ws.live-play.acgvideo.com/live-ws/496273/live_20002577_3735661.flv?wsSecret=5aca8343597fa49f52f6f02727ea8944&wsTime=1552361161&trid=63901c288ca44742ab2f55db64af616a&sig=no","accept_quality":"4","broadcast_type":0,"is_tv":0},{"owner":{"face":"http://i0.hdslb.com/bfs/face/202a3aff95f7835e3992786ab61c325ba109030a.jpg","mid":26583489,"name":"云度动漫代咕咕"},"cover":{"src":"http://i0.hdslb.com/bfs/live/e6f273d1c54e8502b591c9aebce86e149ca5a805.jpg","height":180,"width":320},"title":"【昆明云度】画师之友电台","room_id":94946,"check_version":0,"online":7450,"area":"绘画专区","area_id":9,"playurl":"http://ws.live-play.acgvideo.com/live-ws/120126/live_26583489_8892733.flv?wsSecret=931e52a3554b73137a8be27cd981f720&wsTime=1552361161&trid=63901c288ca44742ab2f55db64af616a&sig=no","accept_quality":"4","broadcast_type":0,"is_tv":0},{"owner":{"face":"http://i0.hdslb.com/bfs/face/a2df382ad145f1f4418ea4e5123477656e4221a1.jpg","mid":233132995,"name":"大概是一颗葱油饼"},"cover":{"src":"http://i0.hdslb.com/bfs/live/room_cover/c06583ea7b61dd157e9d70f6d13a73cfec4e3337.jpg","height":180,"width":320},"title":"画累了，摸鱼","room_id":5923793,"check_version":0,"online":6,"area":"绘画专区","area_id":9,"playurl":"http://ws.live-play.acgvideo.com/live-ws/718773/live_233132995_8419718.flv?wsSecret=a7d1e7cf5d3e58350386e16edd51732d&wsTime=1552361161&trid=63901c288ca44742ab2f55db64af616a&sig=no","accept_quality":"4","broadcast_type":0,"is_tv":0},{"owner":{"face":"http://i1.hdslb.com/bfs/face/b507c4a5418eb91f6ada4795292594439f5c2864.jpg","mid":96760464,"name":"君_尹醉"},"cover":{"src":"http://i0.hdslb.com/bfs/live/user_cover/c06a52dd1f2c1fd019c7611fda828a5b21c16ada.jpg","height":180,"width":320},"title":"勤快如我，肝已爆","room_id":3965495,"check_version":0,"online":55,"area":"绘画专区","area_id":9,"playurl":"http://ws.live-play.acgvideo.com/live-ws/553886/live_96760464_7014326.flv?wsSecret=016aa9c8bc360d094c333cb0f4e0d4e6&wsTime=1552361161&trid=63901c288ca44742ab2f55db64af616a&sig=no","accept_quality":"4","broadcast_type":0,"is_tv":0}]
         */

        var partition: PartitionBeanX? = null
        var lives: List<LivesBeanX>? = null

        class PartitionBeanX {
            /**
             * id : 9
             * name : 绘画专区
             * area : draw
             * sub_icon : {"src":"http://static.hdslb.com/live-static/images/mobile/android/small/xxhdpi/9.png?20171116172700","height":"63","width":"63"}
             * count : 92
             */

            var id: Int = 0
            var name: String? = null
            var area: String? = null
            var sub_icon: SubIconBeanX? = null
            var count: Int = 0

            class SubIconBeanX {
                /**
                 * src : http://static.hdslb.com/live-static/images/mobile/android/small/xxhdpi/9.png?20171116172700
                 * height : 63
                 * width : 63
                 */

                var src: String? = null
                var height: String? = null
                var width: String? = null
            }
        }

        class LivesBeanX {
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

            var owner: OwnerBeanXX? = null
            var cover: CoverBeanXX? = null
            var title: String? = null
            var room_id: Int = 0
            var check_version: Int = 0
            var online: Int = 0
            var area: String? = null
            var area_id: Int = 0
            var playurl: String? = null
            var accept_quality: String? = null
            var broadcast_type: Int = 0
            var is_tv: Int = 0

            class OwnerBeanXX {
                /**
                 * face : http://i1.hdslb.com/bfs/face/6c80fa10147b4481688bcaee61ff052fde0b4bdc.jpg
                 * mid : 9871569
                 * name : 小逝lullaby
                 */

                var face: String? = null
                var mid: Int = 0
                var name: String? = null
            }

            class CoverBeanXX {
                /**
                 * src : http://i0.hdslb.com/bfs/live/e02e3b4f71fa8c57793d70fbccb6edbd5883d14a.jpg
                 * height : 180
                 * width : 320
                 */

                var src: String? = null
                var height: Int = 0
                var width: Int = 0
            }
        }
    }

}
