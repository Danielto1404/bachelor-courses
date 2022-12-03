import glob
import os
import shutil

import cv2
from PIL import Image


def convert_mp4_to_jpgs(path, output='output'):
    video_capture = cv2.VideoCapture(path)
    still_reading, image = video_capture.read()
    frame_count = 0
    if os.path.exists(output):
        # remove previous GIF frame files
        shutil.rmtree(output)
    try:
        os.mkdir(output)
    except IOError:
        return

    while still_reading:
        cv2.imwrite(f"{output}/{frame_count:05d}.jpg", image)

        # read next image
        still_reading, image = video_capture.read()
        frame_count += 1


def make_gif(gif_path, frame_folder="output"):
    images = glob.glob(f"{frame_folder}/*.jpg")
    images.sort()
    frames = [Image.open(image) for image in images]
    frame_one = frames[0]
    frame_one.save(gif_path, format="GIF", append_images=frames, save_all=True, duration=50, loop=0)
