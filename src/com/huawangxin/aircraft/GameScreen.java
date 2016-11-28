package com.huawangxin.aircraft;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.loon.framework.javase.game.GameDeploy;
import org.loon.framework.javase.game.core.graphics.LImage;
import org.loon.framework.javase.game.core.graphics.Screen;
import org.loon.framework.javase.game.core.graphics.device.LGraphics;
import org.loon.framework.javase.game.core.timer.LTimer;
import org.loon.framework.javase.game.core.timer.LTimerContext;

public class GameScreen extends Screen {
	
	// ��ҷɻ����ƶ��ٶ�
	private int player_speed = 5;
	
	// ��Ұ������ĸ������
	private boolean to_up = false;
	private boolean to_down = false;
	private boolean to_left = false;
	private boolean to_right = false;
	
	// ��ҷɻ�
	private AircraftSprite player;

	// �л�����
	private List<AircraftSprite> foeList = new ArrayList<AircraftSprite>();

	// �ӵ�����
	private List<AircraftSprite> bulletList = new ArrayList<AircraftSprite>();

	// �л���ʱ�������ڿ��Ƶл����ֵ�Ƶ�ʣ�800���룩
	private LTimer foeTimer = new LTimer(800);

	// �ӵ���ʱ�������ڿ����ӵ����ֵ�Ƶ�ʣ�300���룩
	private LTimer bulletTimer = new LTimer(200);

	// ���Ƶл�������ӵ����е�Ƶ��
	private LTimer timer = new LTimer(20);

	// ���ٶ�ʱ��
	private LTimer destroyTimer = new LTimer(150);

	// ��ҷ���
	private int score = 0;

	private GameDeploy deploy;

	public GameScreen(GameDeploy deploy) {
		this.deploy = deploy;
	}

	@Override
	public void onLoaded() {
		super.onLoad();
		
		//  ��ʼ������
		initBackGround();
		
		//  ��ʼ����ҵķɻ�
		initPlayer();
		
	}

	/**
	 *  ��ʼ������
	 */
	private void initBackGround(){
		// ���ر���
		LImage background = new LImage("assets/bg_480_720.png");
		this.setBackground(background);
	}
	/**
	 *  ��ʼ����ҵķɻ�
	 */
	private void initPlayer(){
		// ������ҷɻ�
		player = new AircraftSprite("assets/player_aircraft.png",
				"assets/player_aircraft_destroy.png", 110, 120);
		add(player);
		player.setLocation(200, 500);
	}
	
	
	
	@Override
	public void alter(LTimerContext timerCtx) {
		/**
		 * ÿ֡ˢ��ʱ���ȿ���ҵķɻ��Ƿ��ʼ����ϣ���ʼ���òſ�ʼ��Ϸ���߼�
		 */
		if(player == null){
			return;
		}
		if (timer.action(timerCtx)) {
			// �ƶ���ҵķɻ�
			movePlayer();
			
			// �ƶ��л�
			moveEnemy();
			
			// �ƶ��ӵ�
			moveBullet();

			// ����ӵ��Ƿ�򵽵л�
			checkHitEnemy();

			// ɾ������ɾ����ǵ��ӵ�
			removeBullet();

			// �жϵ����Ƿ�ײ���ɻ�
			checkHitPlayer();
		}

		// �����л�
		if (foeTimer.action(timerCtx)) {
			createEnemy();
		}
		
		// �����ӵ�,ÿ�δ�������
		if (bulletTimer.action(timerCtx)) {
			createBullet();
		}
		
		// ��ʾ���ٶ���
		if (destroyTimer.action(timerCtx)) {
			playDestoryEnemy();			
			playDestoryPlayer();
		}

	}

	
	/**
	 * �ƶ���ҵķɻ�
	 */
	private void movePlayer(){
		if(player != null){
			if (to_up&&player.getY() > 0) {
				player.move_up(player_speed);
			}
			if (to_down&&player.getY() < (getHeight() - player.getHeight())) {
				player.move_down(player_speed);
			}
			if (to_right&&player.getX() < (getWidth() - player.getWidth())) {
				player.move_right(player_speed);
			}
			if (to_left&&player.getX() > 0) {
				player.move_left(player_speed);
			}
		}
	}
	
	/**
	 *  �ƶ��л�
	 */
	private void moveEnemy(){
		for (int i = foeList.size() - 1; i >= 0; i--) {
			foeList.get(i).move_down(4);
			if (foeList.get(i).getY() > getHeight()) {
				// �ӳ�����ɾ��
				remove(foeList.get(i));
				// �Ӽ�����ɾ��
				foeList.remove(i);
			}
		}
	}
	
	/**
	 *  �ƶ��ӵ�
	 */
	private void moveBullet(){
		for (int i = 0; i < bulletList.size(); i++) {
			bulletList.get(i).move_up(10);
			if (bulletList.get(i).getY() < 0) {
				bulletList.get(i).setDestroy(true);
			}
		}
	}
	
	/**
	 *  ����ӵ��Ƿ��л��ел�
	 */
	private void checkHitEnemy(){
		for (AircraftSprite foe : foeList) {
			for (AircraftSprite bullet : bulletList) {
				boolean isCollision = bullet.checkCollision(foe);
				if (isCollision) {
					// ���л����ӵ�����ɾ�����
					foe.setDestroy(true);
					foe.setShowDestroyAnimation(true);
					bullet.setDestroy(true);
					score += 100;
				}
			}
		}
	}
	
	/**
	 *   ɾ���ӵ�
	 */
	private void removeBullet(){
		for (int i = bulletList.size() - 1; i >= 0; i--) {
			if (bulletList.get(i).isDestroy()) {
				// �ӳ�����ɾ��
				remove(bulletList.get(i));
				// �Ӽ�����ɾ��
				bulletList.remove(i);
			}

		}
	}
	
	/**
	 * �����л�
	 * @param timerCtx
	 */
	private void createEnemy(){
		// ����һ������
		System.out.println("����һ������");
		// �ƶ�����ʾͼƬ�����ٵĶ���ͼƬ
		AircraftSprite foeAircraft = new AircraftSprite(
				"assets/foe_aircraft.png",
				"assets/foe_aircraft_destroy.png", 69, 94);
		foeAircraft.setLocation(Math.random()
				* (getWidth() - foeAircraft.getWidth()), -94);
		// ���뼯��
		foeList.add(foeAircraft);
		// ���볡��
		add(foeAircraft);
	}
	
	/**
	 * �����ӵ�
	 * @param timerCtx
	 */
	private void createBullet(){
		AircraftSprite leftBullet = new AircraftSprite(
				"assets/bullet.png");
		AircraftSprite rightBullet = new AircraftSprite(
				"assets/bullet.png");
		leftBullet.setLocation(player.getX() + 23, player.getY() + 25);
		rightBullet.setLocation(player.getX() + 80, player.getY() + 25);
		add(leftBullet);
		add(rightBullet);
		bulletList.add(leftBullet);
		bulletList.add(rightBullet);
	}
	
	/**
	 *   �жϵ����Ƿ����������
	 */
	private void checkHitPlayer(){
		for (AircraftSprite foe : foeList) {
			boolean isCollision = foe.checkCollision(player);
			if (isCollision) {
				player.setDestroy(true);
				player.setShowDestroyAnimation(true);
				break;
			}

		}
	}
	
	/**
	 *   ��ʾ���ٵл�����
	 */
	private void playDestoryEnemy(){
		for (int i = foeList.size() - 1; i >= 0; i--) {
			AircraftSprite foe = foeList.get(i);
			if (foe.isDestroy() && foe.isShowDestroyAnimation()) {
				boolean isRemove = foe.showDestroyFrame();
				if (isRemove) {
					remove(foe);
					foeList.remove(i);
				}
			}
		}
	}
	
	/**
	 *   ��ʾ������ҷɻ��Ķ���
	 */
	private void playDestoryPlayer(){
		if (player != null && player.isDestroy()
				&& player.isShowDestroyAnimation()) {
			boolean b = player.showDestroyFrame();
			if (b) {
				timer.stop();
				foeTimer.stop();
				bulletTimer.stop();
				remove(player);
				player = null;
				deploy.setScreen(new EndScreen(deploy, score));
				return;
			}
		}
	}
	
	@Override
	public void onKeyUp(LKey e) {
		// ��ֹplayer��û�м���ʱ���û�����
		if (player != null) {
			// ��ȡ�����ļ�ֵ
			int keyCode = e.getKeyCode();
			switch (keyCode) {
			case KeyEvent.VK_UP:
				to_up = false;
				break;
			case KeyEvent.VK_DOWN:
				to_down = false;
				break;
			case KeyEvent.VK_RIGHT:
				to_right = false;
				break;
			case KeyEvent.VK_LEFT:
				to_left = false;
			}
		}
	}
	@Override
	public void onKeyDown(LKey e) {
		super.onKeyDown(e);
		// ��ֹplayer��û�м���ʱ���û�����
		if (player != null) {
			// ��ȡ�����ļ�ֵ
			int keyCode = e.getKeyCode();
			switch (keyCode) {
			case KeyEvent.VK_UP:
				to_up = true;
				to_down = false;
				break;
			case KeyEvent.VK_DOWN:
				to_down = true;
				to_up = false;
				break;
			case KeyEvent.VK_RIGHT:
				to_right = true;
				to_left = false;
				break;
			case KeyEvent.VK_LEFT:
				to_left = true;
				to_right = false;
			}
		}
	}
	
	@Override
	public void draw(LGraphics lg) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void onTouchMove(LTouch e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTouchUp(LTouch e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTouchDown(LTouch e) {
		// TODO Auto-generated method stubF
	}

}
