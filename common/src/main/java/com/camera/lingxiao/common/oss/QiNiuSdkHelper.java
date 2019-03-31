package com.camera.lingxiao.common.oss;

import android.content.Context;

import com.camera.lingxiao.common.R;
import com.camera.lingxiao.common.app.ContentValue;
import com.camera.lingxiao.common.utills.LogUtils;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

public class QiNiuSdkHelper {

    private static UploadManager uploadManager;
    private static QiNiuSdkHelper helper;
    public static QiNiuSdkHelper getInstance(){
        if (helper == null){
            helper = new QiNiuSdkHelper();
        }
        return helper;
    }

    private QiNiuSdkHelper(){
        init();
    }
    private static void init(){
        Configuration config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
                //.recorder(recorder)           // recorder分片上传时，已上传片记录器。默认null
                //.recorder(recorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        uploadManager = new UploadManager(config);
    }

    /**
     * @param token  //从服务端sdk获取
     * @param picName //指定图片名字
     */
    public QiNiuSdkHelper upload(String path, final String picName, String token, final Context context){

        uploadManager.put(path, picName, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if(info.isOK()) {
                            if (listener != null){
                                listener.onSuccess(ContentValue.INSTANCE.getQINIU_BASE_URL() +key);
                            }
                            //上传成功后将key值上传到自己的服务器
                            Auth.create(context
                                    .getResources()
                                    .getString(R.string.AccessKey), context
                                    .getResources()
                                    .getString(R.string.SecretKey))
                                    .uploadToken(key);
                            LogUtils.i("Upload Success"+"picName: "+picName+"  key："+key);
                        } else {
                            if (listener != null){
                                listener.onFaild("图片上传失败");
                            }
                            LogUtils.i("Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        LogUtils.i( key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, null);

        return helper;
        //上传进度
        /*uploadManager.put(data, key, token,handler,
                new UploadOptions(null, null, false,
                        new UpProgressHandler(){
                            public void progress(String key, double percent){
                                Log.i("qiniu", key + ": " + percent);
                            }
                        }, null));*/

    }

    private uploadListener listener;
    public void setUploadListener(uploadListener listener){
        this.listener = listener;
    }
    public interface uploadListener{
        void onSuccess(String url);
        void onFaild(String msg);
    }
}
