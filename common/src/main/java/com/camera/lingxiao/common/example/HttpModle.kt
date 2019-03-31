package com.camera.lingxiao.common.example

class HttpModle {

    /*var news : ArrayList<NewsData>? = null
    class NewsData{

    }*/
    var _id: String? = null
    var createdAt: String? = null
    var desc: String? = null
    var publishedAt: String? = null
    var source: String? = null
    var type: String? = null
    var url: String? = null
    var isUsed: Boolean = false
    var who: String? = null
    var images: List<String>? = null
    override fun toString(): String {
        return "HttpModle(_id=$_id, createdAt=$createdAt, desc=$desc, publishedAt=$publishedAt, source=$source, type=$type, url=$url, isUsed=$isUsed, who=$who, images=$images)"
    }


}
