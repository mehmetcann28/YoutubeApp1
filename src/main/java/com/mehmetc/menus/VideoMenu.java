package com.mehmetc.menus;

import com.mehmetc.controller.VideoController;
import com.mehmetc.entity.User;
import com.mehmetc.entity.Video;
import com.mehmetc.utility.ConsoleTextUtils;

import java.util.List;

public class VideoMenu {
	private final VideoController videoController;
	
	public VideoMenu(VideoController videoController) {
		this.videoController = videoController;
	}
	
	public void displayVideos(List<Video> videos) {
		if (videos.isEmpty()) {
			ConsoleTextUtils.printErrorMessage("Gösterilecek video bulunamadı.");
			return;
		}
		
		for (Video video : videos) {
			videoController.displayVideoDetails(video);
		}
	}
}