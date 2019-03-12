package cn.taotao.utils;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 江宝明
 * @create 2019/1/9
 * @since 1.0.0
 */
public class PictureResult {
    private int error;
    private String url;
    private String message;

    private PictureResult(int error, String url, String message) {
        this.error = error;
        this.url = url;
        this.message = message;
    }

    //成功时调用的方法
    public static PictureResult ok(String url) {
        return new PictureResult(0, url, null);
    }

    //失败时调用的方法
    public static PictureResult error(String message) {
        return new PictureResult(1, null, message);
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;

    }
}