//package com.winston.utils;
//
//import it.sauronsoftware.jave.Encoder;
//import it.sauronsoftware.jave.EncoderException;
//import it.sauronsoftware.jave.MultimediaInfo;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.bytedeco.javacv.FFmpegFrameGrabber;
//import org.bytedeco.javacv.Frame;
//import org.bytedeco.javacv.FrameGrabber;
//import org.bytedeco.javacv.Java2DFrameConverter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
///**
// * @author Winston
// * @title: VideoUtil
// * @projectName hq-pms
// * @description: 处理视频
// * @date 2019/4/2316:48
// */
//@Slf4j
//public class VideoUtil {
//
//    @Value("${file.path}")
//    private String SAVE_DB_PATH;
//    /**
//     * 获取视频时长(时分秒)
//     *
//     * @param ms
//     * @return
//     */
//    public static String ReadVideoTime(Long ms) {
//        int ss = 1000;
//        int mi = ss * 60;
//        int hh = mi * 60;
//        int dd = hh * 24;
//
//        long day = ms / dd;
//        long hour = (ms - day * dd) / hh;
//        long minute = (ms - day * dd - hour * hh) / mi;
//        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
//        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;
//
//        String strDay = day < 10 ? "0" + day : "" + day; //天
//        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
//        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
//        String strSecond = second < 10 ? "0" + second : "" + second;//秒
//        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
//        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
//        if (strHour.equals("00")) {
//            return strMinute + ":" + strSecond;
//        } else {
//            return strHour + ":" + strMinute + ":" + strSecond;
//        }
//    }
//
//    /**
//     * 获取视频时长(毫秒)
//     *
//     * @param file
//     * @return
//     */
//    public static Long ReadVideoTimeMs(File file){
//
//        // 获取视频时长
//        Encoder encoder = new Encoder();
//        Long length = 0l;
//
//        MultimediaInfo m = null;
//        try {
//            m = encoder.getInfo(file);
//            Long ls = m.getDuration();
//            length = ls;
//        } catch (EncoderException e) {
//            e.printStackTrace();
//        }
//        return length;
//    }
//
//    /**
//     * 截取视频第 limit 帧的图片
//     *
//     * @param filePath 视频路径
//     * @return String 文件名
//     * @throws FrameGrabber.Exception
//     */
//    public static String getVideothumbnailURL(String savePath, String filePath, int limit) {
//        try {
//            String picPath = null;
//            FFmpegFrameGrabber ff = null;
//            File outPut = null;
//            String fileName = null;
//
//            ff = new FFmpegFrameGrabber(filePath);
//            ff.start();
//            //获取视频总帧数
//            int ffLength = ff.getLengthInFrames();
//            Frame frame;
//            int flag = 0;
//            if (limit > ffLength) {
//                return null;
//            }
//            while (flag < ffLength) {
//                frame = ff.grabImage();
//                // 对视频的第limit帧进行处理
//                if (frame != null && flag == limit) {
//                    fileName = StringUtil.getRandomString(9) + flag + ".jpg";
//                    picPath = savePath + fileName;
//                    outPut = new File(picPath);
//                    ImageIO.write(getExecuteFrame(frame), "jpg", outPut);
//                }
//                flag++;
//            }
//            ff.stop();
//            return fileName;
//        } catch (FrameGrabber.Exception e) {
//            log.info("创建视频帧失败：{}", e.getMessage());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private static BufferedImage getExecuteFrame(Frame frame) {
//        Java2DFrameConverter converter = new Java2DFrameConverter();
//        BufferedImage bufferedImage = converter.getBufferedImage(frame);
//        return bufferedImage;
//    }
//
//    /**
//     * 删除
//     *
//     * @param files
//     */
//    private static void deleteFile(File... files) {
//        for (File file : files) {
//            if (file.exists()) {
//                file.delete();
//            }
//        }
//    }
//}
