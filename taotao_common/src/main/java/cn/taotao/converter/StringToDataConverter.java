package cn.taotao.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈自定义时间转换器〉
 *
 * @author Administrator
 * @create 2018/12/14
 * @since 1.0.0
 */
public class StringToDataConverter implements Converter<String, Date> {

    private String[] dataFormats;//时间格式字符数组
    @Override
    public Date convert(String dataStr) {
        Date date=null;
        for (String dataFormat : dataFormats) {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat(dataFormat);
            try {
                date=simpleDateFormat.parse(dataStr);
                return date;
            }catch (ParseException e){
                continue;
            }
        }
        throw  new  RuntimeException("400,时间转换异常");
    }

    public void setDataFormats(String[] dataFormats) {
        this.dataFormats = dataFormats;
    }
}
