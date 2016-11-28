package com.huawangxin.aircraft;

import org.loon.framework.javase.game.GameDeploy;
import org.loon.framework.javase.game.action.sprite.Sprite;
import org.loon.framework.javase.game.core.graphics.LImage;
import org.loon.framework.javase.game.core.graphics.Screen;
import org.loon.framework.javase.game.core.graphics.device.LGraphics;
import org.loon.framework.javase.game.core.timer.LTimer;
import org.loon.framework.javase.game.core.timer.LTimerContext;

public class StartScreen extends Screen {
	private Sprite aircraft;
	private GameDeploy deploy;

	// ��ʱ�� 20����
	private LTimer timer = new LTimer(20);

	public StartScreen(GameDeploy deploy) {
		this.deploy = deploy;
	}

	@Override
	public void onLoaded() {
		super.onLoaded();
		//��ʼ������
		initBackGround();
		//��ʼ������
		initSprite();
	}

	@Override
	public void alter(LTimerContext timerCtx) {
		/**
		 * ÿ��20�����ƶ�һ�ηɻ�
		 */
		if (timer.action(timerCtx)) {
			moveSprite();
		}
	}
	/**
	 * ��ʼ�����汳��
	 */
	private void initBackGround(){
		// LImage��װͼƬ��Ϣ
		// ����ͼƬ
		LImage background = new LImage("assets/bg_start_480_720.png");
		// ���ñ�������Screen
		setBackground(background);
	}
	/**
	 * ��ʼ������
	 */
	private void initSprite(){
		// ��������
		aircraft = new Sprite("assets/start_aircraft.png");

		// ������볡��
		add(aircraft);

		aircraft.setLocation(0, 400);
	}
	/**
	 * �ƶ�����
	 */
	private void moveSprite(){
		aircraft.move_right(5);
		if (aircraft.getX() > getWidth()) {
			timer.stop();
			System.out.println("�л���������");
			deploy.setScreen(new GameScreen(deploy));
		}
	}
	@Override
	public void draw(LGraphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTouchDown(LTouch e) {
	}

	@Override
	public void onTouchUp(LTouch e) {

	}

	@Override
	public void onTouchMove(LTouch e) {
	}

}
