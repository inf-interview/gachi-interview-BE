from flask import Flask, request, jsonify
import requests
from moviepy.editor import VideoFileClip
import speech_recognition as sr
from pydub import AudioSegment
import math
import os

app = Flask(__name__)


@app.route('/process_video', methods=['POST'])
def process_video_from_url():
    content = request.json
    video_url = content['video_url']
    if not video_url:
        return jsonify({"error": "No video URL provided"}), 400

    video_path = download_video(video_url)
    if not video_path:
        return jsonify({"error": "Failed to download video"}), 500

    audio_path = video_path.rsplit('.', 1)[0] + '.wav'
    result_text = extract_audio_from_video(video_path, audio_path)
    remove_file(video_path, audio_path)
    return jsonify({"result": result_text}), 200


def download_video(url, local_dir='downloads'):
    local_filename = url.split('/')[-1]
    local_path = os.path.join(local_dir, local_filename)
    os.makedirs(local_dir, exist_ok=True)

    try:
        with requests.get(url, stream=True) as r:
            r.raise_for_status()
            with open(local_path, 'wb') as f:
                for chunk in r.iter_content(chunk_size=8192):
                    f.write(chunk)
        print(f"Video downloaded to: {local_path}")
        return local_path
    except requests.RequestException as e:
        print(f"Error downloading the video: {e}")
        return None


def extract_audio_from_video(video_file_path, audio_file_path):
    try:
        video = VideoFileClip(video_file_path)
        video.audio.write_audiofile(audio_file_path, codec='pcm_s16le')
        video.close()
        os.unlink(video_file_path)  # 원본 비디오 파일 삭제
    except Exception as e:
        print("Video processing error:", e)
        return "Error processing video"
    return process_audio_chunks(audio_file_path)


def process_audio_chunks(audio_file_path):
    chunk_count = split_audio(audio_file_path)
    audio_name = os.path.basename(audio_file_path).rsplit('.', 1)[0]
    recognizer = sr.Recognizer()
    text_results = ["", chunk_count]
    for i in range(chunk_count):
        chunk_file_path = f'{audio_name}_chunk{i}.wav'
        with sr.AudioFile(chunk_file_path) as source:
            audio_data = recognizer.record(source)
            try:
                text = recognizer.recognize_azure(audio_data, language='ko-KR', key="422eed492c8e403b81c1380ac82d2359",
                                                  location="koreacentral")[0]
                text_results.append(text)
            except sr.UnknownValueError:
                text_results.append("[인식 불가]")
            except sr.RequestError as e:
                text_results.append(f"[요청 오류: {e}]")
    return " ".join(text_results)


def split_audio(audio_path, chunk_length_ms=30000):
    audio = AudioSegment.from_file(audio_path)
    audio_name = os.path.basename(audio_path).rsplit('.', 1)[0]
    chunk_count = math.ceil(len(audio) / chunk_length_ms)
    for i in range(chunk_count):
        start_ms = i * chunk_length_ms
        end_ms = start_ms + chunk_length_ms
        chunk = audio[start_ms:end_ms]
        chunk_file_path = f'{audio_name}_chunk{i}.wav'
        chunk.export(chunk_file_path, format="wav")
    return chunk_count


def remove_file(video_path, audio_path):
    audio = AudioSegment.from_file(audio_path)
    chunk_count = math.ceil(len(audio) / 30000)
    audio_name = os.path.basename(audio_path).rsplit('.', 1)[0]
    for i in range(chunk_count):
        os.remove(f'{audio_name}_chunk{i}.wav')
    os.remove(audio_path)
    os.remove(video_path)


if __name__ == '__main__':
    app.run(debug=True)