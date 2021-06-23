from __future__ import unicode_literals
import youtube_dl
from com.chaquo.python import Python
from os.path import dirname, join
def main(variable_name):
    file_dir = str(Python.getPlatform().getApplication().getFilesDir())
    print(file_dir)
    ydl_opts = {
            'outtmpl':file_dir+"/"+variable_name+'.mp3',
            'format': 'bestaudio/best',
            'postprocessors': [{
                'key': 'FFmpegExtractAudio',
                'preferredcodec': 'mp3',
                'preferredquality': '192',
            }],
        }
    with youtube_dl.YoutubeDL(ydl_opts) as ydl:
        try:
            filename = ydl.download(['https://www.youtube.com/watch?v='+variable_name])
        except:
            print("All izz Well")
    return True

