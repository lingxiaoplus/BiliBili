package com.bilibili.lingxiao.play.model

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.annotations.SerializedName

data class CommentData(
    @SerializedName("assist")
    val assist: Int,
    @SerializedName("blacklist")
    val blacklist: Int,
    @SerializedName("config")
    val config: Config,
    @SerializedName("cursor")
    val cursor: Cursor,
    @SerializedName("folder")
    val folder: Folder,
    @SerializedName("hots")
    val hots: List<Reply>?,
    @SerializedName("notice")
    val notice: Any,
    @SerializedName("replies")
    val replies: List<Reply>?,
    @SerializedName("top")
    val top: Top,
    @SerializedName("upper")
    val upper: Upper,
    @SerializedName("vote")
    val vote: Int
) {
    data class Config(
        @SerializedName("read_only")
        val readOnly: Boolean,
        @SerializedName("show_del_log")
        val showDelLog: Boolean,
        @SerializedName("show_up_flag")
        val showUpFlag: Boolean,
        @SerializedName("showadmin")
        val showadmin: Int,
        @SerializedName("showentry")
        val showentry: Int,
        @SerializedName("showfloor")
        val showfloor: Int,
        @SerializedName("showtopic")
        val showtopic: Int
    )

    data class Cursor(
        @SerializedName("all_count")
        val allCount: Int,
        @SerializedName("is_begin")
        val isBegin: Boolean,
        @SerializedName("is_end")
        val isEnd: Boolean,
        @SerializedName("mode")
        val mode: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("next")
        val next: Int,
        @SerializedName("prev")
        val prev: Int,
        @SerializedName("support_mode")
        val supportMode: List<Int>
    )

    data class Reply(
        @SerializedName("action")
        val action: Int,
        @SerializedName("assist")
        val assist: Int,
        @SerializedName("attr")
        val attr: Int,
        @SerializedName("content")
        val content: Content,
        @SerializedName("count")
        val count: Int,
        @SerializedName("ctime")
        val ctime: Long,
        @SerializedName("dialog")
        val dialog: Int,
        @SerializedName("fansgrade")
        val fansgrade: Int,
        @SerializedName("floor")
        val floor: Int,
        @SerializedName("folder")
        val folder: Folder,
        @SerializedName("like")
        val like: Int,
        @SerializedName("member")
        val member: Member,
        @SerializedName("mid")
        val mid: Int,
        @SerializedName("oid")
        val oid: Int,
        @SerializedName("parent")
        val parent: Int,
        @SerializedName("parent_str")
        val parentStr: String,
        @SerializedName("rcount")
        val rcount: Int,
        @SerializedName("replies")
        val replies: List<Reply>?,
        @SerializedName("root")
        val root: Int,
        @SerializedName("root_str")
        val rootStr: String,
        @SerializedName("rpid")
        val rpid: Int,
        @SerializedName("rpid_str")
        val rpidStr: String,
        @SerializedName("state")
        val state: Int,
        @SerializedName("type")
        val type: Int,
        @SerializedName("up_action")
        val upAction: UpAction,
        val viewType:Int
    ) : MultiItemEntity {
        companion object {
            val REPLIE = 0
            val SEGMENT = 1
        }

        override fun getItemType(): Int {
            return viewType
        }
        data class Content(
            @SerializedName("device")
            val device: String,
            @SerializedName("members")
            val members: List<Any>,
            @SerializedName("message")
            val message: String,
            @SerializedName("plat")
            val plat: Int
        )

        data class Member(
            @SerializedName("DisplayRank")
            val displayRank: String,
            @SerializedName("avatar")
            val avatar: String,
            @SerializedName("fans_detail")
            val fansDetail: Any,
            @SerializedName("following")
            val following: Int,
            @SerializedName("level_info")
            val levelInfo: LevelInfo,
            @SerializedName("mid")
            val mid: String,
            @SerializedName("nameplate")
            val nameplate: Nameplate,
            @SerializedName("official_verify")
            val officialVerify: OfficialVerify,
            @SerializedName("pendant")
            val pendant: Pendant,
            @SerializedName("rank")
            val rank: String,
            @SerializedName("sex")
            val sex: String,
            @SerializedName("sign")
            val sign: String,
            @SerializedName("uname")
            val uname: String,
            @SerializedName("vip")
            val vip: Vip
        ) {
            data class Nameplate(
                @SerializedName("condition")
                val condition: String,
                @SerializedName("image")
                val image: String,
                @SerializedName("image_small")
                val imageSmall: String,
                @SerializedName("level")
                val level: String,
                @SerializedName("name")
                val name: String,
                @SerializedName("nid")
                val nid: Int
            )

            data class OfficialVerify(
                @SerializedName("desc")
                val desc: String,
                @SerializedName("type")
                val type: Int
            )

            data class Pendant(
                @SerializedName("expire")
                val expire: Int,
                @SerializedName("image")
                val image: String,
                @SerializedName("name")
                val name: String,
                @SerializedName("pid")
                val pid: Int
            )

            data class LevelInfo(
                @SerializedName("current_exp")
                val currentExp: Int,
                @SerializedName("current_level")
                val currentLevel: Int,
                @SerializedName("current_min")
                val currentMin: Int,
                @SerializedName("next_exp")
                val nextExp: Int
            )

            data class Vip(
                @SerializedName("accessStatus")
                val accessStatus: Int,
                @SerializedName("dueRemark")
                val dueRemark: String,
                @SerializedName("themeType")
                val themeType: Int,
                @SerializedName("vipDueDate")
                val vipDueDate: Long,
                @SerializedName("vipStatus")
                val vipStatus: Int,
                @SerializedName("vipStatusWarn")
                val vipStatusWarn: String,
                @SerializedName("vipType")
                val vipType: Int
            )
        }

        data class Folder(
            @SerializedName("has_folded")
            val hasFolded: Boolean,
            @SerializedName("is_folded")
            val isFolded: Boolean,
            @SerializedName("rule")
            val rule: String
        )

        data class UpAction(
            @SerializedName("like")
            val like: Boolean,
            @SerializedName("reply")
            val reply: Boolean
        )
    }

    data class Hot(
        @SerializedName("action")
        val action: Int,
        @SerializedName("assist")
        val assist: Int,
        @SerializedName("attr")
        val attr: Int,
        @SerializedName("content")
        val content: Content,
        @SerializedName("count")
        val count: Int,
        @SerializedName("ctime")
        val ctime: Int,
        @SerializedName("dialog")
        val dialog: Int,
        @SerializedName("fansgrade")
        val fansgrade: Int,
        @SerializedName("floor")
        val floor: Int,
        @SerializedName("folder")
        val folder: Folder,
        @SerializedName("like")
        val like: Int,
        @SerializedName("member")
        val member: Member,
        @SerializedName("mid")
        val mid: Int,
        @SerializedName("oid")
        val oid: Int,
        @SerializedName("parent")
        val parent: Int,
        @SerializedName("parent_str")
        val parentStr: String,
        @SerializedName("rcount")
        val rcount: Int,
        @SerializedName("replies")
        val replies: List<Reply>,
        @SerializedName("root")
        val root: Int,
        @SerializedName("root_str")
        val rootStr: String,
        @SerializedName("rpid")
        val rpid: Int,
        @SerializedName("rpid_str")
        val rpidStr: String,
        @SerializedName("state")
        val state: Int,
        @SerializedName("type")
        val type: Int,
        @SerializedName("up_action")
        val upAction: UpAction
    ) {
        data class Reply(
            @SerializedName("action")
            val action: Int,
            @SerializedName("assist")
            val assist: Int,
            @SerializedName("attr")
            val attr: Int,
            @SerializedName("content")
            val content: Content,
            @SerializedName("count")
            val count: Int,
            @SerializedName("ctime")
            val ctime: Int,
            @SerializedName("dialog")
            val dialog: Int,
            @SerializedName("fansgrade")
            val fansgrade: Int,
            @SerializedName("floor")
            val floor: Int,
            @SerializedName("folder")
            val folder: Folder,
            @SerializedName("like")
            val like: Int,
            @SerializedName("member")
            val member: Member,
            @SerializedName("mid")
            val mid: Int,
            @SerializedName("oid")
            val oid: Int,
            @SerializedName("parent")
            val parent: Int,
            @SerializedName("parent_str")
            val parentStr: String,
            @SerializedName("rcount")
            val rcount: Int,
            @SerializedName("replies")
            val replies: List<Any>,
            @SerializedName("root")
            val root: Int,
            @SerializedName("root_str")
            val rootStr: String,
            @SerializedName("rpid")
            val rpid: Int,
            @SerializedName("rpid_str")
            val rpidStr: String,
            @SerializedName("state")
            val state: Int,
            @SerializedName("type")
            val type: Int,
            @SerializedName("up_action")
            val upAction: UpAction
        ) {
            data class Member(
                @SerializedName("DisplayRank")
                val displayRank: String,
                @SerializedName("avatar")
                val avatar: String,
                @SerializedName("fans_detail")
                val fansDetail: Any,
                @SerializedName("following")
                val following: Int,
                @SerializedName("level_info")
                val levelInfo: LevelInfo,
                @SerializedName("mid")
                val mid: String,
                @SerializedName("nameplate")
                val nameplate: Nameplate,
                @SerializedName("official_verify")
                val officialVerify: OfficialVerify,
                @SerializedName("pendant")
                val pendant: Pendant,
                @SerializedName("rank")
                val rank: String,
                @SerializedName("sex")
                val sex: String,
                @SerializedName("sign")
                val sign: String,
                @SerializedName("uname")
                val uname: String,
                @SerializedName("vip")
                val vip: Vip
            ) {
                data class Nameplate(
                    @SerializedName("condition")
                    val condition: String,
                    @SerializedName("image")
                    val image: String,
                    @SerializedName("image_small")
                    val imageSmall: String,
                    @SerializedName("level")
                    val level: String,
                    @SerializedName("name")
                    val name: String,
                    @SerializedName("nid")
                    val nid: Int
                )

                data class OfficialVerify(
                    @SerializedName("desc")
                    val desc: String,
                    @SerializedName("type")
                    val type: Int
                )

                data class Pendant(
                    @SerializedName("expire")
                    val expire: Int,
                    @SerializedName("image")
                    val image: String,
                    @SerializedName("name")
                    val name: String,
                    @SerializedName("pid")
                    val pid: Int
                )

                data class LevelInfo(
                    @SerializedName("current_exp")
                    val currentExp: Int,
                    @SerializedName("current_level")
                    val currentLevel: Int,
                    @SerializedName("current_min")
                    val currentMin: Int,
                    @SerializedName("next_exp")
                    val nextExp: Int
                )

                data class Vip(
                    @SerializedName("accessStatus")
                    val accessStatus: Int,
                    @SerializedName("dueRemark")
                    val dueRemark: String,
                    @SerializedName("themeType")
                    val themeType: Int,
                    @SerializedName("vipDueDate")
                    val vipDueDate: Int,
                    @SerializedName("vipStatus")
                    val vipStatus: Int,
                    @SerializedName("vipStatusWarn")
                    val vipStatusWarn: String,
                    @SerializedName("vipType")
                    val vipType: Int
                )
            }

            data class Folder(
                @SerializedName("has_folded")
                val hasFolded: Boolean,
                @SerializedName("is_folded")
                val isFolded: Boolean,
                @SerializedName("rule")
                val rule: String
            )

            data class Content(
                @SerializedName("device")
                val device: String,
                @SerializedName("members")
                val members: List<Member>,
                @SerializedName("message")
                val message: String,
                @SerializedName("plat")
                val plat: Int
            ) {
                data class Member(
                    @SerializedName("DisplayRank")
                    val displayRank: String,
                    @SerializedName("avatar")
                    val avatar: String,
                    @SerializedName("level_info")
                    val levelInfo: LevelInfo,
                    @SerializedName("mid")
                    val mid: String,
                    @SerializedName("nameplate")
                    val nameplate: Nameplate,
                    @SerializedName("official_verify")
                    val officialVerify: OfficialVerify,
                    @SerializedName("pendant")
                    val pendant: Pendant,
                    @SerializedName("rank")
                    val rank: String,
                    @SerializedName("sex")
                    val sex: String,
                    @SerializedName("sign")
                    val sign: String,
                    @SerializedName("uname")
                    val uname: String,
                    @SerializedName("vip")
                    val vip: Vip
                ) {
                    data class Nameplate(
                        @SerializedName("condition")
                        val condition: String,
                        @SerializedName("image")
                        val image: String,
                        @SerializedName("image_small")
                        val imageSmall: String,
                        @SerializedName("level")
                        val level: String,
                        @SerializedName("name")
                        val name: String,
                        @SerializedName("nid")
                        val nid: Int
                    )

                    data class OfficialVerify(
                        @SerializedName("desc")
                        val desc: String,
                        @SerializedName("type")
                        val type: Int
                    )

                    data class Pendant(
                        @SerializedName("expire")
                        val expire: Int,
                        @SerializedName("image")
                        val image: String,
                        @SerializedName("name")
                        val name: String,
                        @SerializedName("pid")
                        val pid: Int
                    )

                    data class LevelInfo(
                        @SerializedName("current_exp")
                        val currentExp: Int,
                        @SerializedName("current_level")
                        val currentLevel: Int,
                        @SerializedName("current_min")
                        val currentMin: Int,
                        @SerializedName("next_exp")
                        val nextExp: Int
                    )

                    data class Vip(
                        @SerializedName("accessStatus")
                        val accessStatus: Int,
                        @SerializedName("dueRemark")
                        val dueRemark: String,
                        @SerializedName("themeType")
                        val themeType: Int,
                        @SerializedName("vipDueDate")
                        val vipDueDate: Long,
                        @SerializedName("vipStatus")
                        val vipStatus: Int,
                        @SerializedName("vipStatusWarn")
                        val vipStatusWarn: String,
                        @SerializedName("vipType")
                        val vipType: Int
                    )
                }
            }

            data class UpAction(
                @SerializedName("like")
                val like: Boolean,
                @SerializedName("reply")
                val reply: Boolean
            )
        }

        data class Content(
            @SerializedName("device")
            val device: String,
            @SerializedName("members")
            val members: List<Any>,
            @SerializedName("message")
            val message: String,
            @SerializedName("plat")
            val plat: Int
        )

        data class Member(
            @SerializedName("DisplayRank")
            val displayRank: String,
            @SerializedName("avatar")
            val avatar: String,
            @SerializedName("fans_detail")
            val fansDetail: Any,
            @SerializedName("following")
            val following: Int,
            @SerializedName("level_info")
            val levelInfo: LevelInfo,
            @SerializedName("mid")
            val mid: String,
            @SerializedName("nameplate")
            val nameplate: Nameplate,
            @SerializedName("official_verify")
            val officialVerify: OfficialVerify,
            @SerializedName("pendant")
            val pendant: Pendant,
            @SerializedName("rank")
            val rank: String,
            @SerializedName("sex")
            val sex: String,
            @SerializedName("sign")
            val sign: String,
            @SerializedName("uname")
            val uname: String,
            @SerializedName("vip")
            val vip: Vip
        ) {
            data class Nameplate(
                @SerializedName("condition")
                val condition: String,
                @SerializedName("image")
                val image: String,
                @SerializedName("image_small")
                val imageSmall: String,
                @SerializedName("level")
                val level: String,
                @SerializedName("name")
                val name: String,
                @SerializedName("nid")
                val nid: Int
            )

            data class OfficialVerify(
                @SerializedName("desc")
                val desc: String,
                @SerializedName("type")
                val type: Int
            )

            data class Pendant(
                @SerializedName("expire")
                val expire: Int,
                @SerializedName("image")
                val image: String,
                @SerializedName("name")
                val name: String,
                @SerializedName("pid")
                val pid: Int
            )

            data class LevelInfo(
                @SerializedName("current_exp")
                val currentExp: Int,
                @SerializedName("current_level")
                val currentLevel: Int,
                @SerializedName("current_min")
                val currentMin: Int,
                @SerializedName("next_exp")
                val nextExp: Int
            )

            data class Vip(
                @SerializedName("accessStatus")
                val accessStatus: Int,
                @SerializedName("dueRemark")
                val dueRemark: String,
                @SerializedName("themeType")
                val themeType: Int,
                @SerializedName("vipDueDate")
                val vipDueDate: Long,
                @SerializedName("vipStatus")
                val vipStatus: Int,
                @SerializedName("vipStatusWarn")
                val vipStatusWarn: String,
                @SerializedName("vipType")
                val vipType: Int
            )
        }

        data class Folder(
            @SerializedName("has_folded")
            val hasFolded: Boolean,
            @SerializedName("is_folded")
            val isFolded: Boolean,
            @SerializedName("rule")
            val rule: String
        )

        data class UpAction(
            @SerializedName("like")
            val like: Boolean,
            @SerializedName("reply")
            val reply: Boolean
        )
    }

    data class Folder(
        @SerializedName("has_folded")
        val hasFolded: Boolean,
        @SerializedName("is_folded")
        val isFolded: Boolean,
        @SerializedName("rule")
        val rule: String
    )

    data class Upper(
        @SerializedName("mid")
        val mid: Int
    )

    data class Top(
        @SerializedName("admin")
        val admin: Any,
        @SerializedName("upper")
        val upper: Upper,
        @SerializedName("vote")
        val vote: Any
    ) {
        data class Upper(
            @SerializedName("action")
            val action: Int,
            @SerializedName("assist")
            val assist: Int,
            @SerializedName("attr")
            val attr: Int,
            @SerializedName("content")
            val content: Content,
            @SerializedName("count")
            val count: Int,
            @SerializedName("ctime")
            val ctime: Int,
            @SerializedName("dialog")
            val dialog: Int,
            @SerializedName("fansgrade")
            val fansgrade: Int,
            @SerializedName("floor")
            val floor: Int,
            @SerializedName("folder")
            val folder: Folder,
            @SerializedName("like")
            val like: Int,
            @SerializedName("member")
            val member: Member,
            @SerializedName("mid")
            val mid: Int,
            @SerializedName("oid")
            val oid: Int,
            @SerializedName("parent")
            val parent: Int,
            @SerializedName("parent_str")
            val parentStr: String,
            @SerializedName("rcount")
            val rcount: Int,
            @SerializedName("replies")
            val replies: List<Reply>,
            @SerializedName("root")
            val root: Int,
            @SerializedName("root_str")
            val rootStr: String,
            @SerializedName("rpid")
            val rpid: Int,
            @SerializedName("rpid_str")
            val rpidStr: String,
            @SerializedName("state")
            val state: Int,
            @SerializedName("type")
            val type: Int,
            @SerializedName("up_action")
            val upAction: UpAction
        ) {
            data class Reply(
                @SerializedName("action")
                val action: Int,
                @SerializedName("assist")
                val assist: Int,
                @SerializedName("attr")
                val attr: Int,
                @SerializedName("content")
                val content: Content,
                @SerializedName("count")
                val count: Int,
                @SerializedName("ctime")
                val ctime: Int,
                @SerializedName("dialog")
                val dialog: Int,
                @SerializedName("fansgrade")
                val fansgrade: Int,
                @SerializedName("floor")
                val floor: Int,
                @SerializedName("folder")
                val folder: Folder,
                @SerializedName("like")
                val like: Int,
                @SerializedName("member")
                val member: Member,
                @SerializedName("mid")
                val mid: Int,
                @SerializedName("oid")
                val oid: Int,
                @SerializedName("parent")
                val parent: Int,
                @SerializedName("parent_str")
                val parentStr: String,
                @SerializedName("rcount")
                val rcount: Int,
                @SerializedName("replies")
                val replies: List<Any>,
                @SerializedName("root")
                val root: Int,
                @SerializedName("root_str")
                val rootStr: String,
                @SerializedName("rpid")
                val rpid: Int,
                @SerializedName("rpid_str")
                val rpidStr: String,
                @SerializedName("state")
                val state: Int,
                @SerializedName("type")
                val type: Int,
                @SerializedName("up_action")
                val upAction: UpAction
            ) {
                data class Member(
                    @SerializedName("DisplayRank")
                    val displayRank: String,
                    @SerializedName("avatar")
                    val avatar: String,
                    @SerializedName("fans_detail")
                    val fansDetail: Any,
                    @SerializedName("following")
                    val following: Int,
                    @SerializedName("level_info")
                    val levelInfo: LevelInfo,
                    @SerializedName("mid")
                    val mid: String,
                    @SerializedName("nameplate")
                    val nameplate: Nameplate,
                    @SerializedName("official_verify")
                    val officialVerify: OfficialVerify,
                    @SerializedName("pendant")
                    val pendant: Pendant,
                    @SerializedName("rank")
                    val rank: String,
                    @SerializedName("sex")
                    val sex: String,
                    @SerializedName("sign")
                    val sign: String,
                    @SerializedName("uname")
                    val uname: String,
                    @SerializedName("vip")
                    val vip: Vip
                ) {
                    data class Nameplate(
                        @SerializedName("condition")
                        val condition: String,
                        @SerializedName("image")
                        val image: String,
                        @SerializedName("image_small")
                        val imageSmall: String,
                        @SerializedName("level")
                        val level: String,
                        @SerializedName("name")
                        val name: String,
                        @SerializedName("nid")
                        val nid: Int
                    )

                    data class OfficialVerify(
                        @SerializedName("desc")
                        val desc: String,
                        @SerializedName("type")
                        val type: Int
                    )

                    data class Pendant(
                        @SerializedName("expire")
                        val expire: Int,
                        @SerializedName("image")
                        val image: String,
                        @SerializedName("name")
                        val name: String,
                        @SerializedName("pid")
                        val pid: Int
                    )

                    data class LevelInfo(
                        @SerializedName("current_exp")
                        val currentExp: Int,
                        @SerializedName("current_level")
                        val currentLevel: Int,
                        @SerializedName("current_min")
                        val currentMin: Int,
                        @SerializedName("next_exp")
                        val nextExp: Int
                    )

                    data class Vip(
                        @SerializedName("accessStatus")
                        val accessStatus: Int,
                        @SerializedName("dueRemark")
                        val dueRemark: String,
                        @SerializedName("themeType")
                        val themeType: Int,
                        @SerializedName("vipDueDate")
                        val vipDueDate: Int,
                        @SerializedName("vipStatus")
                        val vipStatus: Int,
                        @SerializedName("vipStatusWarn")
                        val vipStatusWarn: String,
                        @SerializedName("vipType")
                        val vipType: Int
                    )
                }

                data class Content(
                    @SerializedName("device")
                    val device: String,
                    @SerializedName("members")
                    val members: List<Any>,
                    @SerializedName("message")
                    val message: String,
                    @SerializedName("plat")
                    val plat: Int
                )

                data class Folder(
                    @SerializedName("has_folded")
                    val hasFolded: Boolean,
                    @SerializedName("is_folded")
                    val isFolded: Boolean,
                    @SerializedName("rule")
                    val rule: String
                )

                data class UpAction(
                    @SerializedName("like")
                    val like: Boolean,
                    @SerializedName("reply")
                    val reply: Boolean
                )
            }

            data class Content(
                @SerializedName("device")
                val device: String,
                @SerializedName("members")
                val members: List<Any>,
                @SerializedName("message")
                val message: String,
                @SerializedName("plat")
                val plat: Int
            )

            data class Member(
                @SerializedName("DisplayRank")
                val displayRank: String,
                @SerializedName("avatar")
                val avatar: String,
                @SerializedName("fans_detail")
                val fansDetail: Any,
                @SerializedName("following")
                val following: Int,
                @SerializedName("level_info")
                val levelInfo: LevelInfo,
                @SerializedName("mid")
                val mid: String,
                @SerializedName("nameplate")
                val nameplate: Nameplate,
                @SerializedName("official_verify")
                val officialVerify: OfficialVerify,
                @SerializedName("pendant")
                val pendant: Pendant,
                @SerializedName("rank")
                val rank: String,
                @SerializedName("sex")
                val sex: String,
                @SerializedName("sign")
                val sign: String,
                @SerializedName("uname")
                val uname: String,
                @SerializedName("vip")
                val vip: Vip
            ) {
                data class Nameplate(
                    @SerializedName("condition")
                    val condition: String,
                    @SerializedName("image")
                    val image: String,
                    @SerializedName("image_small")
                    val imageSmall: String,
                    @SerializedName("level")
                    val level: String,
                    @SerializedName("name")
                    val name: String,
                    @SerializedName("nid")
                    val nid: Int
                )

                data class OfficialVerify(
                    @SerializedName("desc")
                    val desc: String,
                    @SerializedName("type")
                    val type: Int
                )

                data class Pendant(
                    @SerializedName("expire")
                    val expire: Int,
                    @SerializedName("image")
                    val image: String,
                    @SerializedName("name")
                    val name: String,
                    @SerializedName("pid")
                    val pid: Int
                )

                data class LevelInfo(
                    @SerializedName("current_exp")
                    val currentExp: Int,
                    @SerializedName("current_level")
                    val currentLevel: Int,
                    @SerializedName("current_min")
                    val currentMin: Int,
                    @SerializedName("next_exp")
                    val nextExp: Int
                )

                data class Vip(
                    @SerializedName("accessStatus")
                    val accessStatus: Int,
                    @SerializedName("dueRemark")
                    val dueRemark: String,
                    @SerializedName("themeType")
                    val themeType: Int,
                    @SerializedName("vipDueDate")
                    val vipDueDate: Long,
                    @SerializedName("vipStatus")
                    val vipStatus: Int,
                    @SerializedName("vipStatusWarn")
                    val vipStatusWarn: String,
                    @SerializedName("vipType")
                    val vipType: Int
                )
            }

            data class Folder(
                @SerializedName("has_folded")
                val hasFolded: Boolean,
                @SerializedName("is_folded")
                val isFolded: Boolean,
                @SerializedName("rule")
                val rule: String
            )

            data class UpAction(
                @SerializedName("like")
                val like: Boolean,
                @SerializedName("reply")
                val reply: Boolean
            )
        }
    }
}