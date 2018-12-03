package cn.colorfuline.elderlylauncher.https.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import cn.colorfuline.elderlylauncher.config.Config;
import cn.colorfuline.elderlylauncher.https.RetrofitUtil;
import cn.colorfuline.elderlylauncher.utils.Encryptor;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

/**
 * Created by CQDXP on 2016/5/23.
 */
public class MRequestBodyConverter<T> implements Converter<T, RequestBody> {

    private final Charset UTF_8 = Charset.forName("UTF-8");

    private Gson gson = null;
    private TypeAdapter<T> adapter = null;

    MRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        JsonWriter jsonWriter = gson.newJsonWriter(writer);
        adapter.write(jsonWriter, value);
        jsonWriter.close();
        return RequestBody.create(RetrofitUtil.CONTENT_TYPE, Encryptor.encrypt(buffer.readByteArray(), Config.AES_KEY));
    }
}
