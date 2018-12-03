package cn.colorfuline.elderlylauncher.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.AndroidCharacter;
import android.text.TextUtils;


import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.colorfuline.base.rxbus.RxBus;
import cn.colorfuline.base.utils.LogUtil;
import cn.colorfuline.elderlylauncher.events.ChangeEnableSpeakEvent;
import cn.colorfuline.elderlylauncher.utils.SPUtil;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/1/16.
 */

public class ReadScreenService extends Service {
    final String LOG_TAG = ReadScreenService.class.getSimpleName();
    public static final boolean DEFAULT_ENABLE_SPEAK = false;
    private String mDirPath;
    private static final String SAMPLE_DIR_NAME = "baiduTTS";
    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_ch_speech_female.dat";
    private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_ch_speech_male.dat";
    private static final String TEXT_MODEL_NAME = "bd_etts_ch_text.dat";
    private static final String LICENSE_FILE_NAME = "temp_license";
    private static final String ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
    private static final String ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
    private static final String ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";

    private SpeechSynthesizer mSpeechSynthesizer;

    public static final String ACTION_SPEAK = "cn.colorfuline.elderlylauncher.ACTION_SPEAK";
    public static final String EXTRA_CONTENT = "speakContent";
    public static final String SP_ENABLE_SPEAK_KEY = "enableSpeak";
    private boolean enableSpeak = DEFAULT_ENABLE_SPEAK;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initialEnv();
        initialTts();
        enableSpeak = SPUtil.getBoolean(getApplicationContext(), SP_ENABLE_SPEAK_KEY);
        IntentFilter intentFilter = new IntentFilter(ACTION_SPEAK);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    /**
     *
     */
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_SPEAK.equals(action)) {
                if (enableSpeak) {
                    String content = intent.getStringExtra(EXTRA_CONTENT);
                    speak(content);
                }
            }
        }
    };

    /**
     *
     */
    Subscription subscription = RxBus.getDefault().ofType(ChangeEnableSpeakEvent.class)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<ChangeEnableSpeakEvent>() {
                @Override
                public void call(ChangeEnableSpeakEvent changeEnableSpeakEvent) {
                    enableSpeak = changeEnableSpeakEvent.isEnableSpeak();
                    SPUtil.putBoolean(getApplicationContext(), SP_ENABLE_SPEAK_KEY, enableSpeak);
                }
            });

    /**
     * 初始化
     */
    private void initialEnv() {
        if (mDirPath == null) {
            String sdcardPath = Environment.getExternalStorageDirectory().toString();
            mDirPath = sdcardPath + "/" + SAMPLE_DIR_NAME;
        }
        makeDir(mDirPath);
        copyFromAssetsToSdcard(false, SPEECH_FEMALE_MODEL_NAME, mDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, SPEECH_MALE_MODEL_NAME, mDirPath + "/" + SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, TEXT_MODEL_NAME, mDirPath + "/" + TEXT_MODEL_NAME);
        copyFromAssetsToSdcard(false, LICENSE_FILE_NAME, mDirPath + "/" + LICENSE_FILE_NAME);
    }

    /**
     * 创建文件夹
     *
     * @param dirPath
     */
    private void makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 将sample工程需要的资源文件拷贝到SD卡中使用（授权文件为临时授权文件，请注册正式授权）
     *
     * @param isCover 是否覆盖已存在的目标文件
     * @param source
     * @param dest
     */
    private void copyFromAssetsToSdcard(boolean isCover, String source, String dest) {
        File file = new File(dest);
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = getResources().getAssets().open(source);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initialTts() {
        this.mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        this.mSpeechSynthesizer.setContext(this);
        this.mSpeechSynthesizer.setSpeechSynthesizerListener(speechSynthesizerListener);
        // 文本模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mDirPath + "/"
                + TEXT_MODEL_NAME);
        // 声学模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mDirPath + "/"
                + SPEECH_FEMALE_MODEL_NAME);
        // 本地授权文件路径,如未设置将使用默认路径.设置临时授权文件路径，LICENCE_FILE_NAME请替换成临时授权文件的实际路径，仅在使用临时license文件时需要进行设置，如果在[应用管理]中开通了正式离线授权，不需要设置该参数，建议将该行代码删除（离线引擎）
        // 如果合成结果出现临时授权文件将要到期的提示，说明使用了临时授权文件，请删除临时授权即可。
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, mDirPath + "/"
                + LICENSE_FILE_NAME);
        // 请替换为语音开发者平台上注册应用得到的App ID (离线授权)
        this.mSpeechSynthesizer.setAppId("9208902"/*这里只是为了让Demo运行使用的APPID,请替换成自己的id。*/);
        // 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
        this.mSpeechSynthesizer.setApiKey("DIGX5nscd8urwdydkpvvlkzu",
                "f7a894a6fa064c047ffa692b32103a5a"/*这里只是为了让Demo正常运行使用APIKey,请替换成自己的APIKey*/);
        // 发音人（在线引擎），可用参数为0,1,2,3。。。（服务器端会动态增加，各值含义参考文档，以文档说明为准。0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置Mix模式的合成策略
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 授权检测接口(只是通过AuthInfo进行检验授权是否成功。)
        // AuthInfo接口用于测试开发者是否成功申请了在线或者离线授权，如果测试授权成功了，可以删除AuthInfo部分的代码（该接口首次验证时比较耗时），不会影响正常使用（合成使用时SDK内部会自动验证授权）
        AuthInfo authInfo = this.mSpeechSynthesizer.auth(TtsMode.MIX);
        if (authInfo.isSuccess()) {
            LogUtil.i(LOG_TAG, "auth success");
        } else {
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            LogUtil.i(LOG_TAG, "auth failed errorMsg=" + errorMsg);
        }
        // 初始化tts
        mSpeechSynthesizer.initTts(TtsMode.MIX);
        // 加载离线英文资源（提供离线英文合成功能）
        int result =
                mSpeechSynthesizer.loadModel(mDirPath + "/" + TEXT_MODEL_NAME, mDirPath
                        + "/" + SPEECH_FEMALE_MODEL_NAME);
        LogUtil.i(LOG_TAG, "loadEnglishModel result=" + result);
    }

    /**
     * 播放语音
     *
     * @param content
     */
    private void speak(String content) {
        if (this.mSpeechSynthesizer != null) {
            //需要合成的文本text的长度不能超过1024个GBK字节。
            if (TextUtils.isEmpty(content)) {
                return;
            }
            int result = this.mSpeechSynthesizer.speak(content);
            if (result < 0) {
                LogUtil.i(LOG_TAG, "error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122");
            }
        }
    }

    /**
     *
     */
    private void stop() {
        if (this.mSpeechSynthesizer != null) {
            this.mSpeechSynthesizer.stop();
        }
    }


    /**
     * 监听
     */
    SpeechSynthesizerListener speechSynthesizerListener = new SpeechSynthesizerListener() {
        @Override
        public void onSynthesizeStart(String s) {

        }

        @Override
        public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {

        }

        @Override
        public void onSynthesizeFinish(String s) {

        }

        @Override
        public void onSpeechStart(String s) {

        }

        @Override
        public void onSpeechProgressChanged(String s, int i) {

        }

        @Override
        public void onSpeechFinish(String s) {

        }

        @Override
        public void onError(String s, SpeechError speechError) {

        }
    };

    @Override
    public void onDestroy() {
        if (mSpeechSynthesizer != null) {
            this.mSpeechSynthesizer.release();
        }
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        subscription.unsubscribe();
    }
}
