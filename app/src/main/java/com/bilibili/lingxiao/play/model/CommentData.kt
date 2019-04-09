package com.bilibili.lingxiao.play.model

import com.chad.library.adapter.base.entity.MultiItemEntity

data class CommentData (
    val config: Config,
    val hots: List<Reply>,
    val mode: Int,
    val notice: Notice,
    val page: Page,
    val replies: List<Reply>?,
    val support_mode: List<Int>,
    val top: Any,
    val upper: Upper
) {

    data class Notice(
        val content: String,
        val id: Int,
        val link: String,
        val title: String
    )

    data class Hot(
        val action: Int,
        val assist: Int,
        val attr: Int,
        val content: Content,
        val count: Int,
        val ctime: Int,
        val dialog: Int,
        val dialog_str: String,
        val fansgrade: Int,
        val floor: Int,
        val folder: Folder,
        val like: Int,
        val member: Member,
        val mid: Int,
        val oid: Int,
        val parent: Int,
        val parent_str: String,
        val rcount: Int,
        val replies: Any,
        val root: Int,
        val root_str: String,
        val rpid: Int,
        val rpid_str: String,
        val state: Int,
        val type: Int,
        val up_action: UpAction
    ) {
        data class Member(
            val DisplayRank: String,
            val avatar: String,
            val fans_detail: Any,
            val following: Int,
            val level_info: LevelInfo,
            val mid: String,
            val nameplate: Nameplate,
            val official_verify: OfficialVerify,
            val pendant: Pendant,
            val rank: String,
            val sex: String,
            val sign: String,
            val uname: String,
            val vip: Vip
        ) {
            data class LevelInfo(
                val current_exp: Int,
                val current_level: Int,
                val current_min: Int,
                val next_exp: Int
            )

            data class Nameplate(
                val condition: String,
                val image: String,
                val image_small: String,
                val level: String,
                val name: String,
                val nid: Int
            )

            data class OfficialVerify(
                val desc: String,
                val type: Int
            )

            data class Vip(
                val accessStatus: Int,
                val dueRemark: String,
                val themeType: Int,
                val vipDueDate: Long,
                val vipStatus: Int,
                val vipStatusWarn: String,
                val vipType: Int
            )

            data class Pendant(
                val expire: Int,
                val image: String,
                val name: String,
                val pid: Int
            )
        }

        data class UpAction(
            val like: Boolean,
            val reply: Boolean
        )

        data class Content(
            val device: String,
            val members: List<Any>,
            val message: String,
            val plat: Int
        )

        data class Folder(
            val has_folded: Boolean,
            val is_folded: Boolean,
            val rule: String
        )
    }

    data class Upper(
        val mid: Int,
        val top: Any,
        val vote: Any
    )

    data class Page(
        val acount: Int,
        val count: Int,
        val num: Int,
        val size: Int
    )

    data class Reply(
        val action: Int,
        val assist: Int,
        val attr: Int,
        val content: Content,
        val count: Int,
        val ctime: Long,
        val dialog: Int,
        val dialog_str: String,
        val fansgrade: Int,
        val floor: Int,
        val folder: Folder,
        val like: Int,
        val member: Member,
        val mid: Int,
        val oid: Int,
        val parent: Int,
        val parent_str: String,
        val rcount: Int,
        val replies: Any?,
        val root: Int,
        val root_str: String,
        val rpid: Int,
        val rpid_str: String,
        val state: Int,
        val type: Int,
        val up_action: UpAction,
        val viewType:Int
    ) : MultiItemEntity{
        companion object {
            val REPLIE = 0
            val SEGMENT = 1
        }

        override fun getItemType(): Int {
            return viewType
        }

        data class Member(
            val DisplayRank: String,
            val avatar: String,
            val fans_detail: Any,
            val following: Int,
            val level_info: LevelInfo,
            val mid: String,
            val nameplate: Nameplate,
            val official_verify: OfficialVerify,
            val pendant: Pendant,
            val rank: String,
            val sex: String,
            val sign: String,
            val uname: String,
            val vip: Vip
        ) {
            data class LevelInfo(
                val current_exp: Int,
                val current_level: Int,
                val current_min: Int,
                val next_exp: Int
            )

            data class Nameplate(
                val condition: String,
                val image: String,
                val image_small: String,
                val level: String,
                val name: String,
                val nid: Int
            )

            data class OfficialVerify(
                val desc: String,
                val type: Int
            )

            data class Vip(
                val accessStatus: Int,
                val dueRemark: String,
                val themeType: Int,
                val vipDueDate: Long,
                val vipStatus: Int,
                val vipStatusWarn: String,
                val vipType: Int
            )

            data class Pendant(
                val expire: Int,
                val image: String,
                val name: String,
                val pid: Int
            )
        }

        data class UpAction(
            val like: Boolean,
            val reply: Boolean
        )

        data class Content(
            val device: String,
            val members: List<Any>,
            val message: String,
            val plat: Int
        )

        data class Folder(
            val has_folded: Boolean,
            val is_folded: Boolean,
            val rule: String
        )
    }

    data class Config(
        val show_up_flag: Boolean,
        val showadmin: Int,
        val showentry: Int,
        val showfloor: Int,
        val showtopic: Int
    )
}