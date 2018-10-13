package com.bootiful.framework.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
 * uses
 *
 * "ffmpeg" via system path
 *
 * "file" unix command
 *
 * */
public class FfmpegUtil {

    protected static final Log LOG = LogFactory.getLog(FfmpegUtil.class);

    public static Response mixUp(String inputFilePath, String bipFilePath, String outputFilePath,
                                 int maxDuration, int bipSoundModSeconds, int fadeOutLastSeconds,
                                 int outputBitrate) {

//        ffmpeg
//        -loglevel error
//        -hide_banner
//        -y
//        -i demo.mp3
//        -i bip.mp3
//        -filter_complex
//        "[0]afade=t=out:st=25:d=5[a];[1]adelay=10000|10000[b];[1]adelay=20000|20000[c];
//        [a][b][c]amix=inputs=3:duration=longest:dropout_transition=30[out]"
//        -map "[out]" -map_metadata:s:a 0:s:a
//        -ab 196k output.mp3

        if (!StringUtils.hasText(inputFilePath)
                || !StringUtils.hasText(bipFilePath)
                || !StringUtils.hasText(outputFilePath)
                || bipSoundModSeconds == 0) {

            return new Response("Invalid parameter");

        }

        int duration = getDuration(inputFilePath);
        if (duration == 0 || duration > maxDuration) {

            return new Response("Duration is invalid > duration: " + duration);

        }

        int delay = bipSoundModSeconds;
        int fadeOutMS = fadeOutLastSeconds;
        int divider = (int) Math.floor(duration / delay);
        String adelays = "";
        String amixes = "";
        for (int i = 1; i < divider; i++) {
            int adelay = i * delay * 1000;
            adelays += "[1]adelay=" + adelay + "|" + adelay + "[a" + i + "];";
            amixes += "[a" + i + "]";
        }

        String[] command = new String[]{
                "ffmpeg ",
                " -loglevel error",
                " -hide_banner",
                " -y",
                " -i ", inputFilePath,
                " -i ", bipFilePath,
                " -filter_complex",
                " \"[0]afade=t=out:st=" + (duration - fadeOutMS) + ":d=" + fadeOutMS + "[a];",
                adelays,
                "[a]" + amixes + "amix=inputs=" + divider + ":duration=longest:dropout_transition=" + duration + "[out]\"",
                " -map \"[out]\"",
                " -map_metadata:s:a 0:s:a -ab " + outputBitrate + "k " + outputFilePath
        };

        return cmd(command);
    }

    public static int getDuration(String filePath) {

        // ffprobe -i <file> -show_entries format=duration -v quiet -of csv="p=0"

        String command = "ffprobe -i " + filePath + " -show_entries format=duration -v quiet -of csv=\"p=0\"";

        Response response = cmd(command);

        return response.isOk()
                ? (int) Math.round(Double.parseDouble(response.getOutput()))
                : 0;
    }

    public static String getMimeType(String filePath) {

        // file -b --mime-type demo.mp3

        // Files.probeContentType
        // useless if the file has a missing or wrong extension

        String command = "file -b --mime-type " + filePath;

        Response response = cmd(command);

        return response.isOk()
                ? response.getOutput().trim()
                : null;
    }

    public static boolean isAudio(String filePath) {

        String mimeType = getMimeType(filePath);
        if (!StringUtils.hasText(mimeType))
            return false;

        return mimeType.startsWith("audio");
    }

    public static class Response {

        private String output;

        private boolean ok;

        public Response() {
        }

        public Response(String output) {
            this.output = output;
        }

        public String getOutput() {
            return output;
        }

        public void setOutput(String output) {
            this.output = output;
        }

        public boolean isOk() {
            return ok;
        }

        public void setOk(boolean ok) {
            this.ok = ok;
        }
    }

    public static Response cmd(String command) {

        LOG.trace(command);

        Response response = new Response();

        try {
            Process proc = Runtime.getRuntime().exec(command);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

            proc.waitFor();

            String output = "";
            String error = "";

            String s = "";
            while ((s = stdInput.readLine()) != null) output += s;
            while ((s = stdError.readLine()) != null) error += s;

            response.setOk(proc.exitValue() == 0);
            response.setOutput(proc.exitValue() == 0 ? output : error);

        } catch (Exception e) {

            response.setOk(false);
            response.setOutput(e.getMessage());

        }

        return response;
    }

    public static Response cmd(String[] command) {

        return cmd(StringUtils.arrayToDelimitedString(command, ""));
    }

}
