package com.huawangxin.aircraft;

import org.loon.framework.javase.game.GameDeploy;
import org.loon.framework.javase.game.GameScene;

public class Main {
	public static void main(String[] args) {
		// GameScene��װһ����������Ϸ
		GameScene gs = new GameScene("�ɻ���ս", 480, 720);
		// ���ݣ����𳡾����л�
		GameDeploy deploy = gs.getDeploy();

		// ����ˢ��Ƶ��
		deploy.setFPS(100);
		// ���س���
		deploy.setScreen(new StartScreen(deploy));
		
		// ��ʼ��Ϸ��ѭ��
		deploy.mainLoop();
		// ��ʾ��Ϸ����
		gs.showFrame();
	}
}
