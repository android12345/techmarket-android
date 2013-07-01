
package com.youdao.techmarket.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

/**
 * @author junjun
 * 录音管理类
 */
public class AudioRecorderManager {
	
	private static final int RECORDER_BPP = 16;
	private static final String AUDIO_RECORDER_FILE_EXT_WAV = ".wav";
	private static final String AUDIO_RECORDER_FOLDER = "YouDao"; //定义存放录音文的文件夹
	private static final String AUDIO_RECORDER_TEMP_FILE = "record_temp.raw";
	private static final int RECORDER_SAMPLERATE = 44100;
	private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_STEREO;  //设置声道为单声道
	private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
	private static final String TAG = "AudioRecorderManager";
	
	public static float recodeTime=0.0f;    //录音的时间
	public static int RECORD_NO = 0;  //不在录音
	public static int RECORD_ING = 1;   //正在录音
	public static int RECODE_ED = 2;   //完成录音
	public static int RECODE_STATE = 0;      //录音的状态
	
	private AudioRecord recorder = null;
	private int bufferSize = 0;
	private Thread recordingThread = null;  //录音线程
	private boolean isRecording = false;    //是否在录音的标记
	
	private File file ;                     //录音最终的wav文件
	
	public File getFile() {
		return file;
	}

	
	public void setFile(File file) {
		this.file = file;
	}

	public AudioRecorderManager() {
		bufferSize = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE,RECORDER_CHANNELS,RECORDER_AUDIO_ENCODING);
	}
	
	/**
	 * 取得 wAv音频文件路径
	 * @return
	 */
	public   String getFilename(){
		String filepath = Environment.getExternalStorageDirectory().getPath();
		File file = new File(filepath,AudioRecorderManager.AUDIO_RECORDER_FOLDER);
		
		if(!file.exists()){
			file.mkdirs();
		}
		
		return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + AudioRecorderManager.AUDIO_RECORDER_FILE_EXT_WAV);
	}
	/*
	 * 取得raw音频文件的路径
	 */
	public  String getTempFilename(){
		String filepath = Environment.getExternalStorageDirectory().getPath();
		File file = new File(filepath,AudioRecorderManager.AUDIO_RECORDER_FOLDER);
		
		if(!file.exists()){
			file.mkdirs();
		}
		
		File tempFile = new File(filepath,AudioRecorderManager.AUDIO_RECORDER_TEMP_FILE);
		
		if(tempFile.exists())
			tempFile.delete();
		
		return (file.getAbsolutePath() + "/" + AudioRecorderManager.AUDIO_RECORDER_TEMP_FILE);
	}

	/**
	 * 把raw文件变成wav音频
	 * @param inFilename
	 * @param outFilename
	 * @return
	 */
	public  File copyWaveFile(String inFilename,String outFilename,int bufferSize){
		FileInputStream in = null;
		FileOutputStream out = null;
		long totalAudioLen = 0;
		long totalDataLen = totalAudioLen + 36;
		long longSampleRate = AudioRecorderManager.RECORDER_SAMPLERATE;
		int channels = 2;
		long byteRate = AudioRecorderManager.RECORDER_BPP * AudioRecorderManager.RECORDER_SAMPLERATE * channels/8;
		
		byte[] data = new byte[bufferSize];
                
		try {
			in = new FileInputStream(inFilename);
			out = new FileOutputStream(outFilename);
			totalAudioLen = in.getChannel().size();
			totalDataLen = totalAudioLen + 36;
			
			Log.d("File size: " ,totalDataLen+"");
			
			AudioRecorderManager.WriteWaveFileHeader(out, totalAudioLen, totalDataLen,
					longSampleRate, channels, byteRate);
			
			while(in.read(data) != -1){
				out.write(data);
			}
			
			in.close();
			out.close();
			android.util.Log.d(TAG, "wav文件路径："+outFilename) ;
			return new File(outFilename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null ;
		} catch (IOException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	/**
	 * 开始录音，把录音文件写入到raw中
	 */
	public void startRecording(){
		recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
				AudioRecorderManager.RECORDER_SAMPLERATE, AudioRecorderManager.RECORDER_CHANNELS,AudioRecorderManager.RECORDER_AUDIO_ENCODING, bufferSize);
		
		recorder.startRecording();
		
		isRecording = true;
		
		recordingThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				writeAudioDataToFile();
			}
		},"AudioRecorder Thread");
		
		recordingThread.start();
	}
	//把音频文件写入文件中
	/**
	 * 这里将数据写入文件，但是并不能播放，因为AudioRecord获得的音频是原始的裸音频，
      * 如果需要播放就必须加入一些格式或者编码的头信息。但是这样的好处就是你可以对音频的 裸数据进行处理
	 */
	private void writeAudioDataToFile(){
		byte data[] = new byte[bufferSize];
		String filename = getTempFilename();
		FileOutputStream os = null;
		
		try {
			os = new FileOutputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int read = 0;
		
		if(null != os){
			while(isRecording){ //在录音
				read = recorder.read(data, 0, bufferSize);
				
				if(AudioRecord.ERROR_INVALID_OPERATION != read){
					try {
						os.write(data);  //把录入的音频写入文件输出流
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 写入头文件
	 * @param out
	 * @param totalAudioLen
	 * @param totalDataLen
	 * @param longSampleRate
	 * @param channels
	 * @param byteRate
	 * @throws IOException
	 */
	public static void WriteWaveFileHeader(
			FileOutputStream out, long totalAudioLen,
			long totalDataLen, long longSampleRate, int channels,
			long byteRate) throws IOException {
		
		byte[] header = new byte[44];
		
		header[0] = 'R';  // RIFF/WAVE header
		header[1] = 'I';
		header[2] = 'F';
		header[3] = 'F';
		header[4] = (byte) (totalDataLen & 0xff);
		header[5] = (byte) ((totalDataLen >> 8) & 0xff);
		header[6] = (byte) ((totalDataLen >> 16) & 0xff);
		header[7] = (byte) ((totalDataLen >> 24) & 0xff);
		header[8] = 'W';
		header[9] = 'A';
		header[10] = 'V';
		header[11] = 'E';
		header[12] = 'f';  // 'fmt ' chunk
		header[13] = 'm';
		header[14] = 't';
		header[15] = ' ';
		header[16] = 16;  // 4 bytes: size of 'fmt ' chunk
		header[17] = 0;
		header[18] = 0;
		header[19] = 0;
		header[20] = 1;  // format = 1
		header[21] = 0;
		header[22] = (byte) channels;
		header[23] = 0;
		header[24] = (byte) (longSampleRate & 0xff);
		header[25] = (byte) ((longSampleRate >> 8) & 0xff);
		header[26] = (byte) ((longSampleRate >> 16) & 0xff);
		header[27] = (byte) ((longSampleRate >> 24) & 0xff);
		header[28] = (byte) (byteRate & 0xff);
		header[29] = (byte) ((byteRate >> 8) & 0xff);
		header[30] = (byte) ((byteRate >> 16) & 0xff);
		header[31] = (byte) ((byteRate >> 24) & 0xff);
		header[32] = (byte) (2 * 16 / 8);  // block align
		header[33] = 0;
		header[34] = RECORDER_BPP;  // bits per sample
		header[35] = 0;
		header[36] = 'd';
		header[37] = 'a';
		header[38] = 't';
		header[39] = 'a';
		header[40] = (byte) (totalAudioLen & 0xff);
		header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
		header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
		header[43] = (byte) ((totalAudioLen >> 24) & 0xff);

		out.write(header, 0, 44);
	}

	
	public void stopRecording(){
		if(null != recorder){
			isRecording = false;
			recorder.stop();
			recorder.release(); //释放以后以前的对象也就不存在了
			recorder = null;
			recordingThread = null;
		}
		if(new File(getTempFilename()).isFile()){
			file = copyWaveFile(getTempFilename(),getFilename(),bufferSize);
			setFile(file) ;
			deleteTempFile();
		}
	}
	/**
	 * 删除掉raw音频文件，只保存转化后的wav文件                                                                                                                                               
	 */
	private void deleteTempFile() {
		File file = new File(getTempFilename());
		//Log.d(TAG, "@@@@@@@@@@@@@@@@@@"+file.isFile()) ;
			file.delete();
	}
}
